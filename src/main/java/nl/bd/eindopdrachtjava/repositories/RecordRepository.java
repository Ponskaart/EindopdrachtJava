package nl.bd.eindopdrachtjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import nl.bd.eindopdrachtjava.models.entities.Record;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    /**
     * Lazy queries which should be replaced by proper ones to prevent things like sql injection. However,
     * these do work.
     */
//    TODO change queries to proper queries.
//    TODO fix finding artist by Id query.
    Optional<List<Record>> findByArtistArtistId(Long artistId);
    Optional<List<Record>> findRecordByGenre(String genre);
    Optional<Record> findRecordByTitle(String title);
}