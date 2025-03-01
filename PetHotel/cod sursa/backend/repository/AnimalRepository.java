package hotelanimale.is.repository;
import hotelanimale.is.model.Animal;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer> {
    @Query("SELECT a FROM Animal a WHERE a.client.id = ?1")
    Optional<List<Animal>> findByClientId(int clientId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Animal a WHERE a.client.id = ?1")
    void deleteByClientId(int clientId);
}
