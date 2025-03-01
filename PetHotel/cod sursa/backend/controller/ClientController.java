package hotelanimale.is.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import hotelanimale.is.service.AnimalService;
import hotelanimale.is.service.ClientService;
import hotelanimale.is.model.Client;
import hotelanimale.is.service.ReservationService;
import hotelanimale.is.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/clients")
public class ClientController {
    private final ClientService clientService;
    private final AnimalService animalService;
    private final ReviewService reviewService;
    private final ReservationService reservationService;

    @Autowired
    public ClientController(ClientService clientService, AnimalService animalService, ReviewService reviewService, ReservationService reservationService) {
        this.clientService = clientService;
        this.animalService = animalService;
        this.reviewService = reviewService;
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @PostMapping
    public void addNewClient(@RequestBody Client client) {
        clientService.addClient(client);
    }

    @DeleteMapping(path = "{clientId}")
    public void deleteClient(@PathVariable("clientId") int clientId) {
        reservationService.deleteReservation(clientId);
        animalService.deleteAnimalByClientId(clientId);
        reviewService.deleteReviewsByClientId(clientId);
        clientService.deleteClient(clientId);
    }

    @PutMapping(path = "{clientId}")
    public void updateClient(@PathVariable("clientId") int clientId, @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName, @RequestParam(required = false) LocalDate dateOfBirth) {
        clientService.updateClient(clientId, firstName, lastName, dateOfBirth);
    }

    @GetMapping(path = "/byUserId")
    public Client getClientByUserId(@RequestParam int userId) {
        return clientService.getClientByUserId(userId);
    }

    @GetMapping(path = "/byUsername")
    public Client getClientByUsername(@RequestParam  String username) {
        return clientService.getClientByUsername(username);
    }

    @DeleteMapping(path = "/deleteClientByUserId")
    public void deleteClientByUserId(@RequestParam int userId) {
        Client client = clientService.getClientByUserId(userId);
        reservationService.deleteReservationByClientId(client.getId());
        animalService.deleteAnimalByClientId(client.getId());
        reviewService.deleteReviewsByClientId(client.getId());
        clientService.deleteClient(client.getId());
    }

    @PutMapping(path = "{clientId}/details")
    public ResponseEntity<?> updateClientDetails(
            @PathVariable("clientId") int clientId,
            @RequestBody Map<String, String> payload) {
        try {
            String firstName = payload.get("firstName");
            String lastName = payload.get("lastName");
            String dateOfBirthStr = payload.get("dateOfBirth");

            Client client = clientService.getClientById(clientId);
            if (client == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Client not found"));
            }

            if (firstName != null && !firstName.isEmpty()) {
                client.setFirstName(firstName);
            }
            if (lastName != null && !lastName.isEmpty()) {
                client.setLastName(lastName);
            }
            if (dateOfBirthStr != null && !dateOfBirthStr.isEmpty()) {
                try {
                    LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr);
                    client.setDateOfBirth(dateOfBirth);
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid date format"));
                }
            }

            clientService.updateClient(client.getId(), client.getFirstName(), client.getLastName(), client.getDateOfBirth());
            return ResponseEntity.ok(Map.of("message", "Client details updated successfully"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to update client details", "details", e.getMessage()));
        }
    }

}
