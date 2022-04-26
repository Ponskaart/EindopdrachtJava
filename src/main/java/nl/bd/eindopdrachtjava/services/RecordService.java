package nl.bd.eindopdrachtjava.services;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.entities.Record;
import nl.bd.eindopdrachtjava.models.requests.RecordRegistrationRequest;
import nl.bd.eindopdrachtjava.repositories.ArtistRepository;
import nl.bd.eindopdrachtjava.repositories.RecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * using @AllArgsConstructor allows us to easily instantiate the recordRepository.
 */
@Service
@AllArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;
    private final ArtistRepository artistRepository;

    /**
     * Searches for record with specific Id.
     */
    public Record getRecordById(Long recordId){
        return recordRepository.findById(recordId).get();
    }
    /**
     * Method retrieves all Record entities from the database and returns them as a list.
     */
    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }

//    /**
//     * Returns all records of a specific artist
//     */
//    public List<Record> getRecordsByArtist(Long artistId){
//        return recordRepository.findByArtistId(artistId);
//    }

    /**
     * Returns a record with a specific title
     */
    public Record getRecordByTitle(String title){
        return recordRepository.findRecordByTitle(title);
    }

    /**
     * Returns a list of records with a specific genre
     */
    public List<Record> getRecordsByGenre(String genre){
        return recordRepository.findRecordByGenre(genre);
    }

    /**
     * Method to register a new record using the builder design pattern to make it easier to see what is actually
     * happening. I'm also using a wrapper class when registering the record so that I don't have to pass this method 9
     * different variables.
     */
    public Record registerRecord(RecordRegistrationRequest recordRegistrationRequest){
        Record record = Record.builder()
                .artist(artistRepository.findArtistByArtistName(recordRegistrationRequest.getArtistName()))
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

    /**
     * Updates a Record with new data, creates new record if record id does not exist.
     */
    public Record updateRecord(Record newRecord, RecordRegistrationRequest recordRegistrationRequest, Long recordId){
        return recordRepository.findById(recordId).map(record -> {
            record.setArtist(artistRepository.findArtistByArtistName(recordRegistrationRequest.getArtistName()));
            record.setTitle(recordRegistrationRequest.getTitle());
            record.setGenre(recordRegistrationRequest.getGenre());
            record.setLabel(recordRegistrationRequest.getLabel());
            record.setColor(recordRegistrationRequest.getColor());
            record.setYear(recordRegistrationRequest.getYear());
            record.setCountry(recordRegistrationRequest.getCountry());
            record.setShaped(recordRegistrationRequest.isShaped());
            record.setPicturedisk(recordRegistrationRequest.isPicturedisk());
            return recordRepository.save(record);
        }).orElseGet(() -> {
            newRecord.setRecordId(recordId);
            return recordRepository.save(newRecord);
        });
    }

    /**
     * Deletes a record with a specific id
     */
    public void deleteRecord(Long recordId){
        recordRepository.deleteById(recordId);
    }
}