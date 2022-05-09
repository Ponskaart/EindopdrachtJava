package nl.bd.eindopdrachtjava.repositories;

import nl.bd.eindopdrachtjava.models.entities.Artist;
import nl.bd.eindopdrachtjava.models.entities.CoverArt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoverArtRepository extends JpaRepository<CoverArt, Long> {
    /**
     * custom query does not work without the whitespace in from of "WHERE", don't know why.
     */
    @Query( "SELECT c " +
            "FROM CoverArt c" +
            " WHERE c.record.recordId = :recordId")
    Optional<CoverArt> findCoverArtByRecordId(
            @Param("recordId") Long recordId);
}