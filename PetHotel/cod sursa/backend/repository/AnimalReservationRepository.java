package hotelanimale.is.repository;

import hotelanimale.is.model.Animal;
import hotelanimale.is.model.AnimalReservation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface AnimalReservationRepository extends JpaRepository<AnimalReservation, Integer> {
    @Query("SELECT ar.animal FROM AnimalReservation ar WHERE ar.reservation.id = ?1")
    List<Animal> findAnimalsByReservationId(int reservationId);

    @Transactional
    @Modifying
    @Query("DELETE FROM AnimalReservation ar WHERE ar.reservation.id = ?1")
    void deleteByReservation_Id(int reservationId);

    @Transactional
    @Modifying
    @Query("DELETE FROM AnimalReservation ar WHERE ar.animal.id = ?1")
    void deleteByAnimal_Id(int animalId);
}
