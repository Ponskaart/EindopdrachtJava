package nl.bd.eindopdrachtjava.services;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.entities.CoverArt;
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

    /**
     * Method saves an image to the database and returns its Id to the user.
     */
    public Long uploadCoverArt(MultipartFile multipartImage) throws IOException {
        CoverArt coverArt = new CoverArt();
        coverArt.setTitle(multipartImage.getName());
        coverArt.setContent(multipartImage.getBytes());

        return coverArtRepository.save(coverArt).getCoverArtId();
    }
}
