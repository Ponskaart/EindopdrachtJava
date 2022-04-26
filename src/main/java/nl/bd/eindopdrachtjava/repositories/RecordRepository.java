package nl.bd.eindopdrachtjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import nl.bd.eindopdrachtjava.models.entities.Record;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    /**
     * Lazy queries which should be replaced by proper ones to prevent things like sql injection. However,
     * these do work.
     */
//    TODO change queries to proper queries.
    List<Record> findByArtistId(Long artistId);
    List<Record> findRecordByGenre(String genre);
    Record findRecordByTitle(String title);
}