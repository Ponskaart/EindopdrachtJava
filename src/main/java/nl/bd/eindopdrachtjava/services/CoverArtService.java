package nl.bd.eindopdrachtjava.services;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.entities.CoverArt;
import nl.bd.eindopdrachtjava.models.entities.Record;
import nl.bd.eindopdrachtjava.repositories.CoverArtRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Service layer which handles all image up/downloading related logic.
 */
@Service
@AllArgsConstructor
public class CoverArtService {
    private CoverArtRepository coverArtRepository;
    private RecordService recordService;

    /**
     * Method saves an image to the database and returns its Id to the user.
     */
    public Record uploadCoverArt(MultipartFile multipartImage, Long recordId) throws IOException {
        CoverArt coverArt = new CoverArt();
        coverArt.setTitle(multipartImage.getName());
        coverArt.setContent(multipartImage.getBytes());
        coverArtRepository.save(coverArt);

        return recordService.updateCoverArt(recordId, coverArt.getCoverArtId());
    }
}
