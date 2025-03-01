package hotelanimale.is.repository;

import hotelanimale.is.model.Contact;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    @Query("SELECT c FROM Contact c WHERE c.user.id = ?1")
    Optional<List<Contact>> findByUserId(int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Contact c WHERE c.user.id = ?1")
    void deleteByUserId(int userId);
}
