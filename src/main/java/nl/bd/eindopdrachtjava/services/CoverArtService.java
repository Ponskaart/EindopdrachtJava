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
    private final CoverArtRepository coverArtRepository;
    private final RecordRepository recordRepository;
    private final RecordService recordService;

    /**
     * Method saves an image to the database and returns the record object it belongs to if the file is a PNG, JPEG or
     * GIFF. Otherwise, the system throws an exception.
     */
    public Record uploadCoverArt(MultipartFile multipartImage, Long recordId)
            throws MultipartException, IOException, ResourceNotFoundException {
        String fileContentType = multipartImage.getContentType();

        if (recordRepository.findById(recordId).isPresent()) {
            return validateAndUpload(multipartImage, recordId, fileContentType);
        } else {
            throw new ResourceNotFoundException("Record with id " + recordId + ", does not exist");
        }
    }

    /**
     * Method retrieves cover art from database and returns it as a byte array.
     */
    public ByteArrayResource downloadCoverArt(Long recordId) throws ResourceNotFoundException {
        Long tempCoverArtId = recordRepository.findById(recordId).get().getCoverArt().getCoverArtId();
        CoverArt coverArt = coverArtRepository.findById(tempCoverArtId).orElseThrow(() ->
                new ResourceNotFoundException("Record with id " + recordId + " was not found" ));

        return new ByteArrayResource(coverArt.getContent());
    }

    /**
     * List of accepted content types. Currently only one file type is accepted
     */
    private static final List<String> contentTypes = List.of("image/png");

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
    private Record validateAndUpload(MultipartFile multipartImage, Long recordId, String fileContentType) throws IOException {
        if (contentTypes.contains(fileContentType)) {
            CoverArt coverArt = ValidatedImage(multipartImage, recordId);
            coverArtRepository.save(coverArt);
            return recordService.updateCoverArt(recordId, coverArt.getCoverArtId());
        } else {
            throw new InvalidFileException("Only PNG files are accepted");
        }
    }
}