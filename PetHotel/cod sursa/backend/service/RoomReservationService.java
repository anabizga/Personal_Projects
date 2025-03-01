package hotelanimale.is.service;

import hotelanimale.is.model.Reservation;
import hotelanimale.is.model.RoomReservation;
import hotelanimale.is.repository.RoomReservationRepository;
import hotelanimale.is.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomReservationService {
    private final RoomReservationRepository roomReservationRepository;
    private final RoomService roomService;
    private final ReservationService reservationService;

    @Autowired
    public RoomReservationService(RoomReservationRepository roomReservationRepository,
                                  RoomService roomService,
                                  ReservationService reservationService) {
        this.roomReservationRepository = roomReservationRepository;
        this.roomService = roomService;
        this.reservationService = reservationService;
    }

    public void addRoomToReservation(int roomId, int reservationId) {
        Room room = roomService.findRoomById(roomId);
        Reservation reservation = reservationService.getReservationById(reservationId);

        if (room == null || reservation == null) {
            throw new IllegalArgumentException("Room or Reservation not found");
        }

        RoomReservation roomReservation = new RoomReservation(room, reservation);
        roomReservationRepository.save(roomReservation);
    }

    public List<RoomReservation> getAllRoomReservations() {
        return roomReservationRepository.findAll();
    }

    public void deleteByReservationId(int reservationId) {
        roomReservationRepository.deleteByReservation_Id(reservationId);
    }

    public void deleteByRoomId(int roomId) {
        roomReservationRepository.deleteByRoom_Id(roomId);
    }

    public List<Room> getRoomsByReservationId(int reservationId) {
        return roomReservationRepository.findRoomsByReservationId(reservationId);
    }
}
