package nl.bd.eindopdrachtjava.repositories;

import nl.bd.eindopdrachtjava.models.entities.CoverArt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoverArtRepository extends JpaRepository<CoverArt, Long> {
    /**
     * Custom queries to retrieve specific data from the database.
     */
    @Query("SELECT c " +
            "FROM CoverArt c" +
            " WHERE c.record.recordId = :recordId")
    Optional<CoverArt> findCoverArtByRecordId(
            @Param("recordId") Long recordId);
}