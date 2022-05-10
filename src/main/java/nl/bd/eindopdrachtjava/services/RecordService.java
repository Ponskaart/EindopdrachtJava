package nl.bd.eindopdrachtjava.services;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.exceptions.ResourceAlreadyExistsException;
import nl.bd.eindopdrachtjava.models.entities.Record;
import nl.bd.eindopdrachtjava.exceptions.ResourceNotFoundException;
import nl.bd.eindopdrachtjava.models.requests.RecordRegistrationRequest;
import nl.bd.eindopdrachtjava.repositories.ArtistRepository;
import nl.bd.eindopdrachtjava.repositories.CoverArtRepository;
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
    private final CoverArtRepository coverArtRepository;

    /**
     * Searches for record with specific Id.
     */
    public Record getRecordById(Long recordId) throws ResourceNotFoundException {
        return recordRepository.findById(recordId).orElseThrow(() ->
                new ResourceNotFoundException("Record with id " + recordId + " was not found" )) ;
    }

    /**
     * Method retrieves all Record entities from the database and returns them as a list.
     */
    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }

    /**
     * Returns all records of a specific artist
     */
    public List<Record> getRecordsByArtist(Long artistId)  throws ResourceNotFoundException {
        return recordRepository.findByArtistArtistId(artistId).orElseThrow(() ->
                new ResourceNotFoundException("Record with artist " + artistId + " were not found" ));
    }

    /**
     * Returns a record with a specific title
     */
    public Record getRecordByTitle(String title)  throws ResourceNotFoundException {
        return recordRepository.findRecordByTitle(title).orElseThrow(() ->
                new ResourceNotFoundException("Record with title " + title + " was not found" ));
    }

    /**
     * Returns a list of records with a specific genre
     */
    public List<Record> getRecordsByGenre(String genre)  throws ResourceNotFoundException {
        return recordRepository.findRecordByGenre(genre).orElseThrow(() ->
                new ResourceNotFoundException("Record with genre " + genre + " was not found" ));
    }

    /**
     * Method to register a new record using the builder design pattern to make it easier to see what is actually
     * happening. I'm also using a wrapper class when registering the record so that I don't have to pass this method 9
     * different variables. Method also checks if record is already registered, if so, it throws an exception so that
     * the database does not get double entries.
     */
    public Record registerRecord(RecordRegistrationRequest recordRegistrationRequest) {
        if (doesRecordExist(recordRegistrationRequest)){
            throw new ResourceAlreadyExistsException("Record with name: " + recordRegistrationRequest.getTitle() +
                    ", and with artist: " + recordRegistrationRequest.getArtistName() + ", is already registered.");
        } else {
            if(doesArtistExist(recordRegistrationRequest)){
                Record record = createRecord(recordRegistrationRequest);
                return recordRepository.save(record);
            } else {
                throw new ResourceNotFoundException("Artist with name: " + recordRegistrationRequest.getArtistName() +
                        ", does not exist");
            }
        }
    }

    /**
     * Updates a Record with new data, creates new record if record id does not exist.
     */
    public Record updateRecord(RecordRegistrationRequest recordRegistrationRequest,
                               Long recordId) throws ResourceNotFoundException{
        return recordRepository.findById(recordId).map(record -> updatedRecord(recordRegistrationRequest, record))
                .orElseThrow(() -> new ResourceNotFoundException("Record with id " + recordId + " was not found" ));
    }

    /**
     * Method adds cover art to the record object.
     */
    public Record updateCoverArt(Long recordId, Long coverArtId) {
        return recordRepository.findById(recordId).map(record -> updatedCoverArt(coverArtId, record))
                .orElseThrow(() -> new ResourceNotFoundException("Record with id " + recordId + " was not found" ));

    }

    /**
     * Deletes a record with a specific id
     */
    public void deleteRecord(Long recordId){
        recordRepository.deleteById(recordId);
    }

    /**
     * Creates record to use in the registerRecord Method.
     */
    private Record createRecord(RecordRegistrationRequest recordRegistrationRequest) {
        Record record = Record.builder()
                .artist(artistRepository.findByArtistName(recordRegistrationRequest.getArtistName()).get())
                .title(recordRegistrationRequest.getTitle())
                .genre(recordRegistrationRequest.getGenre())
                .label(recordRegistrationRequest.getLabel())
                .color(recordRegistrationRequest.getColor())
                .year(recordRegistrationRequest.getYear())
                .country(recordRegistrationRequest.getCountry())
                .isShaped(recordRegistrationRequest.isShaped())
                .isPicturedisk(recordRegistrationRequest.isPicturedisk())
                .price(recordRegistrationRequest.getPrice())
                .qtyInStock(recordRegistrationRequest.getQtyInStock())
                .build();
        return record;
    }

    /**
     * Returns boolean true if record already exists in database.
     */
    private boolean doesRecordExist(RecordRegistrationRequest recordRegistrationRequest) {
        return recordRepository.findRecordByTitle(recordRegistrationRequest.getTitle()).isPresent() &&
                recordRepository.findRecordByTitle(recordRegistrationRequest.getTitle()).get().getArtist()
                        .getArtistName().equals(recordRegistrationRequest.getArtistName());
    }

    /**
     * Returns boolean true if artist exists in database.
     */
    private boolean doesArtistExist(RecordRegistrationRequest recordRegistrationRequest) {
        return artistRepository.findByArtistName(recordRegistrationRequest.getArtistName()).isPresent();
    }

    /**
     * Returns updated record
     */
    private Record updatedRecord(RecordRegistrationRequest recordRegistrationRequest, Record record) {
        record.setArtist(artistRepository.findByArtistName(recordRegistrationRequest.getArtistName()).get());
        record.setTitle(recordRegistrationRequest.getTitle());
        record.setGenre(recordRegistrationRequest.getGenre());
        record.setLabel(recordRegistrationRequest.getLabel());
        record.setColor(recordRegistrationRequest.getColor());
        record.setYear(recordRegistrationRequest.getYear());
        record.setCountry(recordRegistrationRequest.getCountry());
        record.setShaped(recordRegistrationRequest.isShaped());
        record.setPicturedisk(recordRegistrationRequest.isPicturedisk());
        record.setPrice(recordRegistrationRequest.getPrice());
        record.setQtyInStock(recordRegistrationRequest.getQtyInStock());
        return recordRepository.save(record);
    }

    private Record updatedCoverArt(Long coverArtId, Record record) {
        record.setCoverArt(coverArtRepository.getById(coverArtId));
        return recordRepository.save(record);
    }
}