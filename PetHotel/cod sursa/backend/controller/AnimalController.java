package hotelanimale.is.controller;

import hotelanimale.is.service.AnimalService;
import hotelanimale.is.service.AnimalReservationService;
import hotelanimale.is.model.Animal;
import hotelanimale.is.model.Client;
import hotelanimale.is.service.ClientService;
import hotelanimale.is.service.MedicalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/animals")
public class AnimalController {
    private final AnimalService animalService;
    private final MedicalInfoService medicalInfoService;
    private final ClientService clientService;
    private final AnimalReservationService animalReservationService;

    @Autowired
    public AnimalController(AnimalService animalService, MedicalInfoService medicalInfoService, ClientService clientService, AnimalReservationService animalReservationService) {
        this.animalService = animalService;
        this.medicalInfoService = medicalInfoService;
        this.clientService = clientService;
        this.animalReservationService = animalReservationService;
    }

    @GetMapping(path = "/{animalId}")
    public ResponseEntity<?> getAnimalById(@PathVariable("animalId") int animalId) {
        try {
            Animal animal = animalService.findAnimalById(animalId);
            if (animal == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Animal not found"));
            }
            return ResponseEntity.ok(animal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to fetch animal details", "details", e.getMessage()));
        }
    }

    @GetMapping
    public List<Animal> getAllAnimals() {
        return animalService.getAllAnimals();
    }

    @PostMapping
    public ResponseEntity<?> addAnimal(@RequestBody Map<String, Object> payload) {
        try {
            String name = (String) payload.get("name");
            if (name == null || name.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Animal name is required."));
            }

            Optional<Object> ageObject = Optional.ofNullable(payload.get("age"));
            int age = ageObject.map(Object::toString)
                    .map(Integer::parseInt)
                    .orElse(0);

            if (age <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Animal age must be greater than 0."));
            }

            String gender = (String) payload.get("gender");
            if (gender == null || gender.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Animal gender is required."));
            }

            Optional<Object> clientIdObject = Optional.ofNullable(payload.get("client"))
                    .map(client -> ((Map<String, Object>) client).get("id"));
            int clientId = clientIdObject.map(Object::toString)
                    .map(Integer::parseInt)
                    .orElseThrow(() -> new IllegalArgumentException("Client ID is required."));

            Client client = clientService.getClientById(clientId);
            if (client == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Client not found."));
            }

            Animal animal = new Animal(name, age, gender, client);
            animalService.addAnimal(animal);

            return ResponseEntity.status(HttpStatus.CREATED).body(animal);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while adding the animal.", "details", e.getMessage()));
        }
    }

    @DeleteMapping(path = "{AnimalId}")
    public ResponseEntity<?> deleteAnimal(@PathVariable("AnimalId") int animalId) {
        System.out.println("Deleting animal with ID: " + animalId);

        if (animalService.findAnimalById(animalId) != null) {
            animalReservationService.deleteByAnimalId(animalId);
            medicalInfoService.deleteByAnimalId(animalId);
            animalService.deleteAnimal(animalId);
            System.out.println("Animal deleted successfully.");
            return ResponseEntity.ok(Map.of("message", "Animal deleted successfully"));
        }

        System.out.println("Animal not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Animal not found"));
    }


    @PutMapping(path = "{AnimalId}")
    public ResponseEntity<?> updateAnimal(
            @PathVariable("AnimalId") int animalId,
            @RequestBody Map<String, Object> payload) {
        String name = (String) payload.get("name");
        Object ageObject = payload.get("age");
        int age = 0;
        if (ageObject != null) {
            try {
                age = Integer.parseInt(ageObject.toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid age format. Age must be a numeric value.");
            }
        }

        String gender = (String) payload.get("gender");

        if (animalService.findAnimalById(animalId) != null) {
            animalService.updateAnimal(animalId, name, age, gender);
            return ResponseEntity.ok(Map.of("message", "Animal updated successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Animal not found"));
    }


    @GetMapping(path = "/byClientId")
    public List<Animal> getAnimalsByClientId(@RequestParam int clientId) {
        return animalService.findAnimalByClientId(clientId);
    }

    @DeleteMapping(path = "/byClientId")
    public ResponseEntity<?> deleteAnimalsByClientId(@RequestParam int clientId) {
        animalService.deleteAnimalByClientId(clientId);
        return ResponseEntity.ok(Map.of("message", "All animals for client deleted successfully"));
    }
}
