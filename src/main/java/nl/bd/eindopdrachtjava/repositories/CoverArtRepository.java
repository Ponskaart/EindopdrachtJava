package nl.bd.eindopdrachtjava.repositories;

import nl.bd.eindopdrachtjava.models.entities.Artist;
import nl.bd.eindopdrachtjava.models.entities.CoverArt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CoverArtRepository extends JpaRepository<CoverArt, Long> {
    /**
     * custom query does not work without the whitespace in from of "WHERE", don't know why.
     */
    @Query( "SELECT r " +
            "FROM Record r" +
            " WHERE r.recordId = :recordId")
    Optional<CoverArt> findCoverArtByRecordId(
            @Param("recordId") Long recordId);
}
