package nl.bd.eindopdrachtjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import nl.bd.eindopdrachtjava.models.entityModels.Record;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findRecordByArtist();
    Record findRecordByTitle();
}
