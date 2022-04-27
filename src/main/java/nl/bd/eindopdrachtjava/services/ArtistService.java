package nl.bd.eindopdrachtjava.services;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.entities.Artist;
import nl.bd.eindopdrachtjava.models.requests.ArtistRegistrationRequest;
import nl.bd.eindopdrachtjava.repositories.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer with methods to facilitate CRUD operations
 */
@Service
@AllArgsConstructor
public class ArtistService {
    private ArtistRepository artistRepository;

    /**
     * Method to register a new artist using the builder design pattern to make it easier to see what is actually
     * happening.
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
     * Method searches repo for artist by Id.
     */
    public Artist getArtistByArtistId(Long artistId){
        return artistRepository.findById(artistId).get();
    }

    /**
     * Method searches repo for artist by name.
     */
    public Artist getArtistByArtistName(String artistName){
        return artistRepository.findByArtistName(artistName);
    }

    /**
     * Deletes an Arist with a specific Id.
     */
    public void deleteArtist(Long artistId){
        artistRepository.deleteById(artistId);
    }
}
