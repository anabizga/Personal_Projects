package hotelanimale.is.repository;

import hotelanimale.is.model.Room;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query("SELECT r FROM Room r WHERE r.location.id = ?1")
    Optional<List<Room>> findByLocationId(int locationId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Room r WHERE r.location.id = ?1")
    void deleteByLocationId(int locationId);

    @Query("""
        SELECT r FROM Room r
        WHERE r.location.id = :locationId AND r.id NOT IN (
            SELECT rr.room.id FROM RoomReservation rr
            WHERE rr.reservation.checkIn < :checkOut AND rr.reservation.checkOut > :checkIn
        )
    """)
    List<Room> findAvailableRooms(@Param("locationId") int locationId,
                                  @Param("checkIn") LocalDate checkIn,
                                  @Param("checkOut") LocalDate checkOut);
}
