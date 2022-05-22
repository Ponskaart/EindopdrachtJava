package nl.bd.eindopdrachtjava.repositories;

import nl.bd.eindopdrachtjava.models.entities.Artist;
import nl.bd.eindopdrachtjava.models.requests.ArtistRegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    /**
     * Custom queries to retrieve specific data from the database.
     */
    @Query("SELECT a " +
            "FROM Artist a " +
            "WHERE a.artistName = :artistName")
    Optional<Artist> findByArtistName(
            @Param("artistName") String artistName);

    @Query("SELECT a " +
            "FROM Artist a " +
            "WHERE a.established = :established")
    List<Artist> findArtistByEstablished(
            @Param("established") int established);

    @Query("SELECT a " +
            "FROM Artist a " +
            "WHERE a.artistName = :#{#artistRegistrationRequest.artistName} " +
            "AND a.established = :#{#artistRegistrationRequest.established}")
    Optional<Artist> findArtistByArtistNameAndEstablished(
            @Param("artistRegistrationRequest") ArtistRegistrationRequest artistRegistrationRequest);
}