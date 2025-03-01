package hotelanimale.is.repository;

import hotelanimale.is.model.Reservation;
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
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    @Query("SELECT r FROM Reservation r WHERE r.client.id = ?1")
    Optional<List<Reservation>> findReservationByClientId(int clientId);

    @Query("SELECT r FROM Reservation r WHERE r.client.user.id = ?1")
    Optional<List<Reservation>> findReservationByUserId(int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Reservation r WHERE r.client.id = ?1")
    void deleteReservationByClientId(int clientId);

    @Query("SELECT r FROM Reservation r WHERE r.location.id = ?1")
    Optional<List<Reservation>> findReservationByLocationId(int locationId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Reservation r WHERE r.location.id = ?1")
    void deleteReservationByLocationId(int locationId);

    @Query("SELECT r FROM Reservation r WHERE r.state = :state AND r.checkIn < :currentDate")
    List<Reservation> findExpiredReservations(@Param("state") String state, @Param("currentDate") LocalDate currentDate);
}
