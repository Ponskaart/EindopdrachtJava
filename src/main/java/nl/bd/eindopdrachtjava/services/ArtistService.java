package nl.bd.eindopdrachtjava.services;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.exceptions.ResourceAlreadyExistsException;
import nl.bd.eindopdrachtjava.exceptions.ResourceNotFoundException;
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
        if (doesArtistExist(artistRegistrationRequest)){
            throw new ResourceAlreadyExistsException(
                    "Artist with name: " + artistRegistrationRequest.getArtistName() + ", and with year established: "
                            + artistRegistrationRequest.getEstablished() + ", is already registered.");
        } else {
            Artist artist = Artist.builder()
                    .artistName(artistRegistrationRequest.getArtistName())
                    .established(artistRegistrationRequest.getEstablished())
                    .build();
            return artistRepository.save(artist);
        }
    }

    /**
     * Returns boolean true if artist already exists in database.
     */
    private boolean doesArtistExist(ArtistRegistrationRequest artistRegistrationRequest) {
        return artistRepository.findByArtistName(artistRegistrationRequest.getArtistName()).isPresent() &&
                artistRepository.findByArtistName(artistRegistrationRequest.getArtistName()).get().getEstablished()
                        == artistRegistrationRequest.getEstablished();
    }

    /**
     * Method retrieves all Artist entities from the database and returns them as a list.
     */
    public List<Artist> getAllArtists() throws ResourceNotFoundException {
        return artistRepository.findAll();
    }

    /**
     * Method that retrieves al artists in a specific year.
     */
    public List<Artist> getArtistsByYearEstablished(int established) {
        return artistRepository.findArtistByEstablished(established).orElseThrow(() ->
                new ResourceNotFoundException("Artists with year of establishment: "
                        + established + ", were not found" ));
    }

    /**
     * Method searches repo for artist by Id.
     */
    public Artist getArtistByArtistId(Long artistId) throws ResourceNotFoundException{
        return artistRepository.findById(artistId).orElseThrow(() ->
                new ResourceNotFoundException("Artist with id: " + artistId + ", was not found" ));
    }

    /**
     * Method searches repo for artist by name.
     */
    public Artist getArtistByArtistName(String artistName){
        return artistRepository.findByArtistName(artistName).orElseThrow(() ->
                new ResourceNotFoundException("Artist with name: " + artistName + ", was not found" ));
    }

    /**
     * Deletes an Arist with a specific Id.
     */
    public void deleteArtist(Long artistId){
        artistRepository.deleteById(artistId);
    }
}
