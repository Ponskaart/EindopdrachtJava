package nl.bd.eindopdrachtjava.services;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.entities.Artist;
import nl.bd.eindopdrachtjava.models.requests.ArtistRegistrationRequest;
import nl.bd.eindopdrachtjava.repositories.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ArtistService {
    private ArtistRepository artistRepository;

    /**
     * Method to register a new record using the builder design pattern to make it easier to see what is actually
     * happening. I'm also using a wrapper class when registering the record so that I don't have to pass this method 9
     * different variables.
     */
    public Artist registerArtist(ArtistRegistrationRequest artistRegistrationRequest){
        Artist artist = Artist.builder()
                .artistName(artistRegistrationRequest.getArtistName())
                .established(artistRegistrationRequest.getEstablished())
                .build();
        return artistRepository.save(artist);
    }

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
