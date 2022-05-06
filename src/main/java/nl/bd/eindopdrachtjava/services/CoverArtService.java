package nl.bd.eindopdrachtjava.services;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.entities.CoverArt;
import nl.bd.eindopdrachtjava.repositories.CoverArtRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CoverArtService {
    private CoverArtRepository coverArtRepository;

    public CoverArt uploadCoverArt(){
        Image dbImage = new Image();
        dbImage.setName(multipartImage.getName());
        dbImage.setContent(multipartImage.getBytes());

        return imageDbRepository.save(dbImage)
                .getId();
    }

}
