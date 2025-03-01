package hotelanimale.is.controller;

import hotelanimale.is.service.AnimalReservationService;
import hotelanimale.is.model.AnimalReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/animal-reservations")
public class AnimalReservationController {
    private final AnimalReservationService animalReservationService;

    @Autowired
    public AnimalReservationController(AnimalReservationService animalReservationService) {
        this.animalReservationService = animalReservationService;
    }

    @PostMapping
    public ResponseEntity<?> addAnimalToReservation(@RequestParam int animalId, @RequestParam int reservationId) {
        try {
            animalReservationService.addAnimalToReservation(animalId, reservationId);
            return ResponseEntity.ok("Animal added to reservation successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<AnimalReservation> getAllAnimalReservations() {
        return animalReservationService.getAllAnimalReservations();
    }

    @DeleteMapping("/byReservationId")
    public ResponseEntity<?> deleteByReservationId(@RequestParam int reservationId) {
        animalReservationService.deleteByReservationId(reservationId);
        return ResponseEntity.ok("Animal reservations deleted for reservation ID: " + reservationId);
    }

    @DeleteMapping("/byAnimalId")
    public ResponseEntity<?> deleteByAnimalId(@RequestParam int animalId) {
        animalReservationService.deleteByAnimalId(animalId);
        return ResponseEntity.ok("Animal reservations deleted for animal ID: " + animalId);
    }
}
