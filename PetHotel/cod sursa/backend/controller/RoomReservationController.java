package hotelanimale.is.controller;

import hotelanimale.is.model.RoomReservation;
import hotelanimale.is.service.RoomReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/room-reservations")
public class RoomReservationController {
    private final RoomReservationService roomReservationService;

    @Autowired
    public RoomReservationController(RoomReservationService roomReservationService) {
        this.roomReservationService = roomReservationService;
    }

    @PostMapping
    public ResponseEntity<?> addRoomToReservation(@RequestParam int roomId, @RequestParam int reservationId) {
        try {
            roomReservationService.addRoomToReservation(roomId, reservationId);
            return ResponseEntity.ok("Room added to reservation successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<RoomReservation> getAllRoomReservations() {
        return roomReservationService.getAllRoomReservations();
    }

    @DeleteMapping("/byReservationId")
    public ResponseEntity<?> deleteByReservationId(@RequestParam int reservationId) {
        roomReservationService.deleteByReservationId(reservationId);
        return ResponseEntity.ok("Room reservations deleted for reservation ID: " + reservationId);
    }

    @DeleteMapping("/byRoomId")
    public ResponseEntity<?> deleteByRoomId(@RequestParam int roomId) {
        roomReservationService.deleteByRoomId(roomId);
        return ResponseEntity.ok("Room reservations deleted for room ID: " + roomId);
    }
}
