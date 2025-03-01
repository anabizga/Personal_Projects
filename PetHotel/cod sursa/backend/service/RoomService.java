package hotelanimale.is.service;
import hotelanimale.is.model.Location;
import hotelanimale.is.model.Room;
import hotelanimale.is.repository.LocationRepository;
import hotelanimale.is.repository.RoomRepository;
import hotelanimale.is.repository.RoomReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final LocationRepository locationRepository;
    private final RoomReservationRepository roomReservationRepository;

    @Autowired
    public RoomService(RoomRepository RoomRepository, LocationRepository locationRepository, RoomReservationRepository roomReservationRepository) {
        this.roomRepository = RoomRepository;
        this.locationRepository = locationRepository;
        this.roomReservationRepository = roomReservationRepository;
    }

    public List<Room> getRooms(){
        return roomRepository.findAll();
    }

    public Room addNewRoom(Room room) {
        Location location = locationRepository.findById(room.getLocation().getId())
                .orElseThrow(() -> new IllegalStateException("Location with Id " + room.getLocation().getId() + "does not exist"));
        room.setLocation(location);
        roomRepository.save(room);
        return room;
    }

    public void deleteRoom(int RoomId) {
        boolean exists = roomRepository.existsById(RoomId);
        if(!exists){
            throw new IllegalArgumentException("This Room with id = " + RoomId + " does not exist");
        }
        roomReservationRepository.deleteByRoom_Id(RoomId);
        roomRepository.deleteById(RoomId);
    }

    @Transactional
    public Room updateRoom(int RoomId, String roomType, float price) {
        Optional<Room> r = roomRepository.findById(RoomId);
        if(r.isEmpty()){
            throw new IllegalArgumentException("This Room with id = " + RoomId + " does not exist");
        }
        Room room = r.get();
        if (roomType != null && !roomType.isEmpty() && !room.getRoomType().equals(roomType)) {
            room.setRoomType(roomType);
        }
        if (price != 0 && price != room.getPrice()) {
            room.setPrice(price);
        }
        return room;
    }

    public List<Room> findByLocationId(int locationId) {
        Optional<List<Room>> rooms = roomRepository.findByLocationId(locationId);
        if (rooms.isPresent()) {
            return rooms.get();
        } else{
            throw new IllegalArgumentException("There is no room at location = " + locationId);
        }
    }

    public void deleteByLocationId(int locationId) {
        roomRepository.deleteByLocationId(locationId);
    }

    public Room findRoomById(int roomId) {
        return roomRepository.findById(roomId).orElse(null);
    }

    public List<Room> getAvailableRooms(int locationId, LocalDate checkIn, LocalDate checkOut) {
        if (checkIn.isAfter(checkOut)) {
            throw new IllegalArgumentException("Data de check-in trebuie să fie înainte de check-out.");
        }
        return roomRepository.findAvailableRooms(locationId, checkIn, checkOut);
    }
}
