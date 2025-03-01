package hotelanimale.is.controller;

import hotelanimale.is.model.*;
import hotelanimale.is.model.ReservationRequest;
import hotelanimale.is.service.ReservationService;
import hotelanimale.is.service.AnimalReservationService;
import hotelanimale.is.service.ContactService;
import hotelanimale.is.service.MedicalInfoService;
import hotelanimale.is.model.Room;
import hotelanimale.is.service.RoomReservationService;
import hotelanimale.is.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final AnimalReservationService animalReservationService;
    private final RoomReservationService roomReservationService;
    private final ContactService contactService;
    private final UserService userService;
    private final MedicalInfoService medicalInfoService;

    public ReservationController(ReservationService reservationService, AnimalReservationService animalReservationService, RoomReservationService roomReservationService, ContactService contactService, UserService userService, MedicalInfoService medicalInfoService) {
        this.reservationService = reservationService;
        this.animalReservationService = animalReservationService;
        this.roomReservationService = roomReservationService;
        this.contactService = contactService;
        this.userService = userService;
        this.medicalInfoService = medicalInfoService;
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @PostMapping
    public void addReservation(@RequestBody Reservation reservation) {
        reservationService.addReservation(reservation);
    }

    @DeleteMapping(path = "{reservationId}")
    public void deleteReservation(@PathVariable("reservationId") int reservationId) {
        reservationService.deleteReservation(reservationId);
    }

    @PutMapping(path = "{reservationId}")
    public void updateReservation(@PathVariable("reservationId") int reservationId, @RequestParam(required = false) LocalDate checkIn, @RequestParam(required = false) LocalDate checkOut, @RequestParam(required = false) String state) {
        reservationService.updateReservation(reservationId, checkIn, checkOut, state);
    }

    @GetMapping(path = "/byClientId")
    public List<Reservation> getReservationByClientId(@RequestParam int clientId) {
        return reservationService.getReservationsByClientId(clientId);
    }

    @GetMapping(path = "/byUserId")
    public List<Reservation> getReservationByUserId(@RequestParam int userId) {
        return reservationService.getReservationsByUserId(userId);
    }

    @DeleteMapping(path = "/deleteByClientID")
    public void deleteReservationsByClientId(@RequestParam int clientId) {
        reservationService.deleteReservationByClientId(clientId);
    }

    @GetMapping(path = "/byLocationId")
    public List<Reservation> getReservationByLocationId(@RequestParam int locationId) {
        return reservationService.getReservationsByLocationId(locationId);
    }

    @DeleteMapping(path = "/deleteByLocationID")
    public void deleteReservationsByLocationId(@RequestParam int locationId) {
        reservationService.deleteReservationByLocationId(locationId);
    }

    @GetMapping("/details")
    public ResponseEntity<?> getReservationDetails(@RequestParam int reservationId) {
        Reservation reservation = reservationService.getReservationById(reservationId);
        if (reservation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found");
        }

        List<Animal> animals = animalReservationService.getAnimalsByReservationId(reservationId);
        List<Room> rooms = roomReservationService.getRoomsByReservationId(reservationId);

        Map<String, Object> details = Map.of(
                "id", reservation.getId(),
                "checkIn", reservation.getCheckIn(),
                "checkOut", reservation.getCheckOut(),
                "state", reservation.getState(),
                "location", reservation.getLocation(),
                "animals", animals,
                "rooms", rooms
        );

        return ResponseEntity.ok(details);
    }

    @GetMapping("/detailsForAdmin")
    public ResponseEntity<?> getReservationDetailsforAdmin(@RequestParam int reservationId) {
        Reservation reservation = reservationService.getReservationById(reservationId);
        if (reservation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found");
        }

        List<Map<String, Object>> animalsDetails = new ArrayList<>();
        List<Animal> animals = animalReservationService.getAnimalsByReservationId(reservationId);
        for (Animal animal : animals) {
            List<MedicalInfo> medicalInfos = medicalInfoService.getMedicalInfoByAnimalId(animal.getId());
            Map<String, Object> animalDetails = Map.of(
                    "id", animal.getId(),
                    "name", animal.getName(),
                    "age", animal.getAge(),
                    "gender", animal.getGender(),
                    "medicalInfos", medicalInfos
            );
            animalsDetails.add(animalDetails);
        }

        List<Room> rooms = roomReservationService.getRoomsByReservationId(reservationId);

        Client client = reservation.getClient();
        Map<String, Object> clientDetails = null;
        if (client != null) {
            List<Contact> contacts = contactService.getContactsByUserId(client.getUser().getId());
            clientDetails = Map.of(
                    "id", client.getId(),
                    "firstName", client.getFirstName(),
                    "lastName", client.getLastName(),
                    "dateOfBirth", client.getDateOfBirth(),
                    "contacts", contacts
            );
        }

        Map<String, Object> details = Map.of(
                "id", reservation.getId(),
                "checkIn", reservation.getCheckIn(),
                "checkOut", reservation.getCheckOut(),
                "state", reservation.getState(),
                "location", Map.of(
                        "id", reservation.getLocation().getId(),
                        "name", reservation.getLocation().getName()
                ),
                "client", clientDetails,
                "animals", animalsDetails,
                "rooms", rooms
        );

        return ResponseEntity.ok(details);
    }

    @PostMapping("/{reservationId}/checkin")
    public ResponseEntity<?> checkIn(@PathVariable int reservationId) {
        try {
            reservationService.checkIn(reservationId);
            return ResponseEntity.ok("Check-in realizat cu succes!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Eroare la realizarea check-in-ului.");
        }
    }

    @PostMapping("/{reservationId}/checkout")
    public ResponseEntity<?> checkOut(@PathVariable int reservationId) {
        try {
            reservationService.checkOut(reservationId);
            return ResponseEntity.ok("Check-out realizat cu succes!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Eroare la realizarea check-out-ului.");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createReservation(@RequestBody ReservationRequest reservationRequest) {
        try {
            Reservation reservation = reservationService.createReservation(reservationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Eroare la crearea rezervării.");
        }
    }

}
