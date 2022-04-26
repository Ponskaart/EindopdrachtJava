package nl.bd.eindopdrachtjava.services;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.entities.Artist;
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

    /**
     * Method that retrieves al artists in a specific year.
     */
    public List<Artist> getArtistsByYearEstablished(int established) {
        return artistRepository.findArtistByEstablished(established);
    }

    /**
     * Deletes an Arist with a specific id
     */
    public void deleteArtist(Long artistId){
        artistRepository.deleteById(artistId);
    }
}
