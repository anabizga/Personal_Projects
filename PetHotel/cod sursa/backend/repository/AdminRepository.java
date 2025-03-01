package hotelanimale.is.repository;
import hotelanimale.is.model.Admin;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    @Query("SELECT a FROM Admin a WHERE a.user.id = ?1")
    Optional<Admin> findByUserId(int userId);

    @Query("SELECT a FROM Admin a WHERE a.user.username = ?1")
    Optional<Admin> findByUsername(String username);

    @Query("SELECT a FROM Admin a WHERE a.location.id = ?1")
    Optional<List<Admin>> findByLocationId(int locationId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Admin a WHERE a.user.id = ?1")
    void deleteByUserId(int userId);
}
