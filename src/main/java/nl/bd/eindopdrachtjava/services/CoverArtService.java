package nl.bd.eindopdrachtjava.services;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.exceptions.InvalidFileException;
import nl.bd.eindopdrachtjava.exceptions.ResourceNotFoundException;
import nl.bd.eindopdrachtjava.models.entities.CoverArt;
import nl.bd.eindopdrachtjava.models.entities.Record;
import nl.bd.eindopdrachtjava.repositories.CoverArtRepository;
import nl.bd.eindopdrachtjava.repositories.RecordRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Service layer which handles all image up/downloading related logic.
 */
@Service
@AllArgsConstructor
public class CoverArtService {
    /**
     * List of accepted content types. Currently only one file type is accepted
     */
    private static final List<String> contentTypes = List.of("image/png");
    private final CoverArtRepository coverArtRepository;
    private final RecordRepository recordRepository;
    private final RecordService recordService;
    private final MessageService messageService;

    /**
     * Method saves an image to the database and returns the record object it belongs to if the file is a PNG.
     * Otherwise, the system throws an exception.
     */
    public Record storeCoverArt(MultipartFile multipartImage, Long recordId)
            throws MultipartException, IOException, ResourceNotFoundException {
        String fileContentType = multipartImage.getContentType();

        if (recordRepository.findById(recordId).isEmpty()) {
            throw new ResourceNotFoundException(messageService.recordIdNotFound(recordId));
        }
        return validateAndStore(multipartImage, recordId, fileContentType);
    }

    /**
     * Method retrieves cover art from database and returns it as a byte array. Tried to use custom query to find
     * cover art by record id, but method would return a record and not a cover art object.
     */
    public ByteArrayResource retrieveCoverArt(Long recordId) throws ResourceNotFoundException {
        if (recordRepository.findById(recordId).isPresent()) {
            CoverArt coverArt = coverArtRepository.findCoverArtByRecordId(recordId)
                    .orElseThrow(() -> new ResourceNotFoundException(messageService.coverArtNotFound()));
            return new ByteArrayResource(coverArt.getContent());
        } else {
            throw new ResourceNotFoundException(messageService.recordIdNotFound(recordId));
        }
    }

    /**
     * Deletes CoverArt entity from database with given Id.
     */
    public void deleteCoverArt(Long coverArtId) {
        if (coverArtRepository.findById(coverArtId).isEmpty()) {
            throw new ResourceNotFoundException(messageService.coverArtIdNotFound(coverArtId));
        }
        coverArtRepository.deleteById(coverArtId);
    }

    /**
     * Method to save validated image to the database, returns the newly created cover art object to assign the
     * database reference.
     */
    private CoverArt ValidatedImage(MultipartFile multipartImage, Long recordId) throws IOException {
        CoverArt coverArt = new CoverArt();
        coverArt.setContent(multipartImage.getBytes());
        coverArt.setRecord(recordRepository.findById(recordId).get());

        return coverArt;
    }

    /**
     * Validates and uploads an image to the database.
     */
    private Record validateAndStore(MultipartFile multipartImage, Long recordId, String fileContentType)
            throws IOException {
        if (contentTypes.contains(fileContentType)) {
            CoverArt coverArt = ValidatedImage(multipartImage, recordId);
            coverArtRepository.save(coverArt);
            return recordService.updateCoverArt(recordId, coverArt.getCoverArtId());
        } else {
            throw new InvalidFileException(messageService.invalidFileType());
        }
    }
}