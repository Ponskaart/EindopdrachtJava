package nl.bd.eindopdrachtjava.services;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.entityModels.Record;
import nl.bd.eindopdrachtjava.models.requests.RecordRegistrationRequest;
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

    public Record registerRecord(RecordRegistrationRequest recordRegistrationRequest){
        Record record = Record.builder()
                .title(recordRegistrationRequest.getTitle())
                .genre(recordRegistrationRequest.getGenre())
                .label(recordRegistrationRequest.getLabel())
                .color(recordRegistrationRequest.getColor())
                .year(recordRegistrationRequest.getYear())
                .country(recordRegistrationRequest.getCountry())
                .isShaped(recordRegistrationRequest.isShaped())
                .isPicturedisk(recordRegistrationRequest.isPicturedisk())
                .build();
        return recordRepository.save(record);
    }

}
