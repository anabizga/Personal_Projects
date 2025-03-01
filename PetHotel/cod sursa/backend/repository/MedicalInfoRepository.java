package hotelanimale.is.repository;

import hotelanimale.is.model.MedicalInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalInfoRepository extends JpaRepository<MedicalInfo, Integer> {
    @Query("SELECT m FROM MedicalInfo m WHERE m.animal.id = ?1")
    Optional<List<MedicalInfo>> findByAnimalId(int animalId);

    @Transactional
    @Modifying
    @Query("DELETE FROM MedicalInfo m WHERE m.animal.id = ?1")
    void deleteByAnimalId(int animalId);
}
