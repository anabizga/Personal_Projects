package hotelanimale.is.controller;

import hotelanimale.is.model.Room;
import hotelanimale.is.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "api/rooms")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from your React frontend
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<Room>> getRooms() {
        List<Room> rooms = roomService.getRooms();
        return ResponseEntity.ok(rooms);
    }

    @PostMapping
    public ResponseEntity<Room> registerNewRoom(@RequestBody Room room) {
        Room newRoom = roomService.addNewRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRoom);
    }

    @DeleteMapping(path = "{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable("roomId") int roomId) {
        roomService.deleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "{roomId}")
    public ResponseEntity<Room> updateRoom(
            @PathVariable("roomId") int roomId,
            @RequestBody Room updatedRoom) {
        Room room = roomService.updateRoom(roomId, updatedRoom.getRoomType(), updatedRoom.getPrice());
        return ResponseEntity.ok(room);
    }

    @GetMapping(path = "/byLocationId")
    public ResponseEntity<List<Room>> getRoomByLocationId(@RequestParam int locationId) {
        List<Room> rooms = roomService.findByLocationId(locationId);
        return ResponseEntity.ok(rooms);
    }

    @DeleteMapping(path = "/deleteByLocationId")
    public ResponseEntity<Void> deleteRoomByLocationId(@RequestParam int locationId) {
        roomService.deleteByLocationId(locationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableRooms(
            @RequestParam int locationId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {
        try {
            List<Room> availableRooms = roomService.getAvailableRooms(locationId, checkIn, checkOut);
            return ResponseEntity.ok(availableRooms);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Eroare la căutarea camerelor disponibile.");
        }
    }
}
