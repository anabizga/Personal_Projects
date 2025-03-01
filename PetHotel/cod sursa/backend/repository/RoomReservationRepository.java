package hotelanimale.is.repository;

import hotelanimale.is.model.Room;
import hotelanimale.is.model.RoomReservation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Integer> {
    @Query("SELECT rr.room FROM RoomReservation rr WHERE rr.reservation.id = ?1")
    List<Room> findRoomsByReservationId(int reservationId);

    @Transactional
    @Modifying
    @Query("DELETE FROM RoomReservation rr WHERE rr.reservation.id = ?1")
    void deleteByReservation_Id(int reservationId);

    @Transactional
    @Modifying
    @Query("DELETE FROM RoomReservation rr WHERE rr.room.id = ?1")
    void deleteByRoom_Id(int roomId);
}
