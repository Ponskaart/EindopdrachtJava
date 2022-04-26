package nl.bd.eindopdrachtjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import nl.bd.eindopdrachtjava.models.entities.Record;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    /**
     * Lazy queries which should be replaced by proper ones to prevent things like sql injection. However,
     * these do work.
     */
    List<Record> findRecordByArtist();
    List<Record> findRecordByGenre();
    Record findRecordByTitle();
}