package nl.bd.eindopdrachtjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import nl.bd.eindopdrachtjava.models.entityModels.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
