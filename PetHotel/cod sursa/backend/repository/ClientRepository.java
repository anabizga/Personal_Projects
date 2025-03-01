package hotelanimale.is.repository;
import hotelanimale.is.model.Client;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    @Query("SELECT c FROM Client c WHERE c.user.id = ?1")
    Optional<Client> findByUserId(int userId);

    @Query("SELECT c FROM Client c WHERE c.user.username = ?1")
    Optional<Client> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("DELETE FROM Client c WHERE c.user.id = ?1")
    void deleteByUserId(int userId);
}
