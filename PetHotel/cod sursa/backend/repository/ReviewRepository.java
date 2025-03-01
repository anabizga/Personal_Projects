package hotelanimale.is.repository;
import hotelanimale.is.model.Review;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query("SELECT r FROM Review r WHERE r.client.id = ?1")
    Optional<List<Review>> findByClientId(int clientId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Review r WHERE r.client.id = ?1")
    void deleteByClientId(int clientId);
}
