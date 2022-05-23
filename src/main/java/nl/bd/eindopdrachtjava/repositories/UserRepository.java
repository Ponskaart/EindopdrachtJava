package nl.bd.eindopdrachtjava.repositories;

import nl.bd.eindopdrachtjava.models.entities.User;
import nl.bd.eindopdrachtjava.models.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Custom queries to retrieve specific data from the database.
     */
    @Query("SELECT u " +
            "FROM User u" +
            " WHERE u.username = :username")
    Optional<User> findByUsername(
            @Param("username") String username);

    @Query("SELECT u " +
            "FROM User u" +
            " WHERE u.role = :role")
    Optional<User> findByUserRole(
            @Param("role") UserRole role);
}
