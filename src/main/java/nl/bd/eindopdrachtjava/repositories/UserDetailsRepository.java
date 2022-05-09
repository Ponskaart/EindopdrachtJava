package nl.bd.eindopdrachtjava.repositories;

import nl.bd.eindopdrachtjava.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<User, Long> {
    @Query( "SELECT u " +
            "FROM User u" +
            " WHERE u.username = :username")
    Optional<User> findByUsername(
            @Param("username") String username);
}
