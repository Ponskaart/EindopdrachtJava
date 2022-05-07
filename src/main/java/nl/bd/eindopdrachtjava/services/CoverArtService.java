package nl.bd.eindopdrachtjava.services;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.entities.CoverArt;
import nl.bd.eindopdrachtjava.models.entities.Record;
import nl.bd.eindopdrachtjava.repositories.CoverArtRepository;
import nl.bd.eindopdrachtjava.repositories.RecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

        return recordService.updateCoverArt(recordId, coverArt.getCoverArtId());
    }
}
