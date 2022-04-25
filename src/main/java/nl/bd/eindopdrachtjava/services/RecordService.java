package nl.bd.eindopdrachtjava.services;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.entityModels.Record;
import nl.bd.eindopdrachtjava.repositories.RecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * using @AllArgsConstructor allows us to easily instantiate the recordRepository.
 */
@Service
@AllArgsConstructor
public class RecordService {
    private RecordRepository recordRepository;

    /**
     *method retrieves all Record entities from the database and returns them as a list.
     */
    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }
    

}
