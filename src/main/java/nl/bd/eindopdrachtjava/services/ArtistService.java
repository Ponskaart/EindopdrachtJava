package nl.bd.eindopdrachtjava.services;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.entities.Artist;
import nl.bd.eindopdrachtjava.models.entities.Record;
import nl.bd.eindopdrachtjava.repositories.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ArtistService {
    private ArtistRepository artistRepository;

    /**
     * Method retrieves all Artist entities from the database and returns them as a list.
     */
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }
    
}
