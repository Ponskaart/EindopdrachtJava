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
    private final ArtistRepository artistRepository;
    private final MessageService messageService;

    /**
     * Method to register a new artist using the builder design pattern to make it easier to see what is actually
     * happening.
     */
    public Artist registerArtist(ArtistRegistrationRequest artistRegistrationRequest) {
        if (artistExists(artistRegistrationRequest)) {
            throw new ResourceAlreadyExistsException(messageService.artistAlreadyExists(
                    artistRegistrationRequest.getArtistName(),
                    artistRegistrationRequest.getEstablished()));
        } else {
            Artist artist = createArtist(artistRegistrationRequest);
            return artistRepository.save(artist);
        }
    }

    /**
     * Method retrieves all Artist entities from the database and returns them as a list.
     */
    public List<Artist> getAllArtists() throws ResourceNotFoundException {
        if ((artistRepository.findAll().isEmpty())) {
            throw new ResourceNotFoundException(messageService.artistRepoEmpty());
        } else {
            return artistRepository.findAll();
        }
    }

    /**
     * Method that retrieves al artists in a specific year.
     */
    public List<Artist> getArtistsByYearEstablished(int established) throws ResourceNotFoundException {
        if ((artistRepository.findArtistByEstablished(established)).isEmpty()) {
            throw new ResourceNotFoundException(messageService.artistEstablishedNotFound(established));
        } else {
            return artistRepository.findArtistByEstablished(established);
        }
    }

    /**
     * Method searches repo for artist by Id.
     */
    public Artist getArtistByArtistId(Long artistId) throws ResourceNotFoundException {
        return artistRepository.findById(artistId).orElseThrow(() ->
                new ResourceNotFoundException(messageService.artistIdNotFound(artistId)));
    }

    /**
     * Method searches repo for artist by name.
     */
    public Artist getArtistByArtistName(String artistName) throws ResourceNotFoundException {
        return artistRepository.findByArtistName(artistName).orElseThrow(() ->
                new ResourceNotFoundException(messageService.artistNameNotFound(artistName)));
    }

    /**
     * Deletes an Arist with a specific Id.
     */
    public void deleteArtist(Long artistId) throws ResourceNotFoundException {
        if (artistRepository.findById(artistId).isEmpty()) {
            throw new ResourceNotFoundException(messageService.artistIdNotFound(artistId));
        }
        artistRepository.deleteById(artistId);
    }

    /**
     * Updates an Artist, throws exception if artistId is invalid.
     */
    public Artist updateArtist(ArtistRegistrationRequest artistRegistrationRequest, Long artistId)
            throws ResourceNotFoundException {
        return artistRepository.findById(artistId).map(artist -> updatedArtist(artistRegistrationRequest, artist))
                .orElseThrow(() -> new ResourceNotFoundException(messageService.artistIdNotFound(artistId)));
    }

    /**
     * Returns boolean true if artist already exists in database.
     */
    private boolean artistExists(ArtistRegistrationRequest artistRegistrationRequest) {
        return artistRepository.findArtistByArtistNameAndEstablished(artistRegistrationRequest).isPresent();
    }

    /**
     * Creates artist to use in registerArtist method.
     */
    private Artist createArtist(ArtistRegistrationRequest artistRegistrationRequest) {
        return Artist.builder()
                .artistName(artistRegistrationRequest.getArtistName())
                .established(artistRegistrationRequest.getEstablished())
                .build();
    }

    /**
     * Checks to see if fields need updating or not.
     */
    private Artist updatedArtist(ArtistRegistrationRequest artistRegistrationRequest, Artist artist) {
        if (artistRegistrationRequest.getArtistName() != null) {
            artist.setArtistName(artistRegistrationRequest.getArtistName());
        }

        if (artistRegistrationRequest.getEstablished() != 0) {
            artist.setEstablished(artistRegistrationRequest.getEstablished());
        }
        return artistRepository.save(artist);
    }
}