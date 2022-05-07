package nl.bd.eindopdrachtjava.services;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.exceptions.ResourceNotFoundException;
import nl.bd.eindopdrachtjava.models.entities.CoverArt;
import nl.bd.eindopdrachtjava.models.entities.Record;
import nl.bd.eindopdrachtjava.repositories.CoverArtRepository;
import nl.bd.eindopdrachtjava.repositories.RecordRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

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
     * Method saves an image to the database and returns the record object it belongs to.
     */
    public Record uploadCoverArt(MultipartFile multipartImage, Long recordId) throws IOException {
        CoverArt coverArt = new CoverArt();
        coverArt.setContent(multipartImage.getBytes());
        coverArt.setRecord(recordRepository.findById(recordId).get());
        coverArtRepository.save(coverArt);
//TODO add exception handling if file is not a PNG or file already exists
        return recordService.updateCoverArt(recordId, coverArt.getCoverArtId());
    }

    /**
     * Method retrieves cover art from database and returns it as a byte array.
     */
    public ByteArrayResource downloadCoverArt(Long recordId) throws ResourceNotFoundException {
        Long tempCoverArtId = recordRepository.findById(recordId).get().getCoverArt().getCoverArtId();
        CoverArt tempCoverArt = coverArtRepository.findById(tempCoverArtId).orElseThrow(() ->
                new ResourceNotFoundException("Record with id " + recordId + " was not found" ));

        byte[] coverArt = tempCoverArt.getContent();

        return new ByteArrayResource(coverArt);
    }
}
