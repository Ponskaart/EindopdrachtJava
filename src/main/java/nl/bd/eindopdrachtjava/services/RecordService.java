package nl.bd.eindopdrachtjava.services;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.exceptions.ResourceAlreadyExistsException;
import nl.bd.eindopdrachtjava.exceptions.ResourceNotFoundException;
import nl.bd.eindopdrachtjava.models.entities.Record;
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
                new ResourceNotFoundException(
                        "Record with id " +
                                recordId +
                                " was not found"));
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
    public List<Record> getRecordsByArtist(Long artistId) throws ResourceNotFoundException {
        if ((recordRepository.findByArtistArtistId(artistId)).isEmpty()) {
            throw new ResourceNotFoundException(
                    "Record with artist " +
                            artistId +
                            " were not found");
        } else {
            return recordRepository.findByArtistArtistId(artistId);
        }
    }

    /**
     * Returns a record with a specific title
     */
    public Record getRecordByTitle(String title) throws ResourceNotFoundException {
        return recordRepository.findRecordByTitle(title).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Record with title " +
                                title +
                                " was not found"));
    }

    /**
     * Returns a list of records with a specific genre
     */
    public List<Record> getRecordsByGenre(String genre) throws ResourceNotFoundException {
        if ((recordRepository.findRecordByGenre(genre)).isEmpty()) {
            throw new ResourceNotFoundException(
                    "Record with genre " +
                            genre +
                            " was not found");
        } else {
            return recordRepository.findRecordByGenre(genre);
        }
    }

    /**
     * Method to register a new record using the builder design pattern to make it easier to see what is actually
     * happening. I'm also using a wrapper class when registering the record so that I don't have to pass this method 9
     * different variables. Method also checks if record is already registered, if so, it throws an exception so that
     * the database does not get double entries.
     */
    public Record registerRecord(RecordRegistrationRequest recordRegistrationRequest) {
        if (recordExists(recordRegistrationRequest)) {
            throw new ResourceAlreadyExistsException(
                    "Record with name: " +
                            recordRegistrationRequest.getTitle() +
                            ", and with artist: " +
                            recordRegistrationRequest.getArtistName() +
                            ", is already registered.");
        } else {
            if (artistExist(recordRegistrationRequest)) {
                Record record = createRecord(recordRegistrationRequest);
                return recordRepository.save(record);
            } else {
                throw new ResourceNotFoundException(
                        "Artist with name: " +
                                recordRegistrationRequest.getArtistName() +
                                ", does not exist");
            }
        }
    }

    /**
     * Updates a Record with new data.
     */
    public Record updateRecord(RecordRegistrationRequest recordRegistrationRequest,
                               Long recordId) throws ResourceNotFoundException {
        if (recordRegistrationRequest.getArtistName() != null & !artistExist(recordRegistrationRequest)) {
            throw new ResourceNotFoundException(
                    "Artist with name: " +
                            recordRegistrationRequest.getArtistName() +
                            " was not found.");
        }

        return recordRepository.findById(recordId).map(record -> updatedRecord(recordRegistrationRequest, record))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Record with id " +
                                recordId +
                                " was not found"));
    }

    /**
     * Method adds cover art to the record object.
     */
    public Record updateCoverArt(Long recordId, Long coverArtId) {
        return recordRepository.findById(recordId).map(record -> updatedCoverArt(coverArtId, record))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Record with id " +
                                recordId +
                                " was not found"));

    }

    /**
     * Deletes a record with a specific id
     */
    public void deleteRecord(Long recordId) throws ResourceNotFoundException {
        if (recordRepository.findById(recordId).isEmpty()) {
            throw new ResourceNotFoundException(
                    "Record with id " +
                            recordId +
                            ", was not found");
        }
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
                .price(recordRegistrationRequest.getPrice())
                .qtyInStock(recordRegistrationRequest.getQtyInStock())
                .build();
        return record;
    }

    /**
     * Returns boolean true if record already exists in database.
     */
    private boolean recordExists(RecordRegistrationRequest recordRegistrationRequest) {
        return recordRepository.findRecordByTitleAndArtistName(recordRegistrationRequest).isPresent();
    }

    /**
     * Returns boolean true if artist exists in database.
     */
    private boolean artistExist(RecordRegistrationRequest recordRegistrationRequest) {
        return artistRepository.findByArtistName(recordRegistrationRequest.getArtistName()).isPresent();
    }

    /**
     * Returns updated record. Very large method, but it should make sure that if the user does not specify a value
     * the old value does not get overridden with null or 0.
     */
    private Record updatedRecord(RecordRegistrationRequest recordRegistrationRequest, Record record)
            throws ResourceNotFoundException {
        if (recordRegistrationRequest.getArtistName() != null) {
            record.setArtist(artistRepository.findByArtistName(recordRegistrationRequest.getArtistName()).get());
        }

        if (recordRegistrationRequest.getTitle() != null) {
            record.setTitle(recordRegistrationRequest.getTitle());
        }

        if (recordRegistrationRequest.getGenre() != null) {
            record.setGenre(recordRegistrationRequest.getGenre());
        }

        if (recordRegistrationRequest.getLabel() != null) {
            record.setLabel(recordRegistrationRequest.getLabel());
        }

        if (recordRegistrationRequest.getColor() != null) {
            record.setColor(recordRegistrationRequest.getColor());
        }

        if (recordRegistrationRequest.getYear() != 0) {
            record.setYear(recordRegistrationRequest.getYear());
        }

        if (recordRegistrationRequest.getCountry() != null) {
            record.setCountry(recordRegistrationRequest.getCountry());
        }

        if (recordRegistrationRequest.getPrice() != 0.0) {
            record.setPrice(recordRegistrationRequest.getPrice());
        }

        if (recordRegistrationRequest.getQtyInStock() != 0) {
            record.setQtyInStock(recordRegistrationRequest.getQtyInStock());
        }
        return recordRepository.save(record);
    }

    private Record updatedCoverArt(Long coverArtId, Record record) {
        record.setCoverArt(coverArtRepository.getById(coverArtId));
        return recordRepository.save(record);
    }
}