package nl.bd.eindopdrachtjava.controllers;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.annotations.AdminAuthorization;
import nl.bd.eindopdrachtjava.models.annotations.CustomerAuthorization;
import nl.bd.eindopdrachtjava.models.entities.Artist;
import nl.bd.eindopdrachtjava.models.enums.UserRole;
import nl.bd.eindopdrachtjava.models.requests.ArtistRegistrationRequest;
import nl.bd.eindopdrachtjava.services.ArtistService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class governs the artist related endpoints.
 */
@RestController
@RequestMapping("recordstore")
@AllArgsConstructor
public class ArtistController {
    private final ArtistService artistService;

    /**
     * Endpoint creates a new artist object and saves it to te database.
     */
    @AdminAuthorization
    @PostMapping("/artist")
    public Artist registerArtist(@RequestBody ArtistRegistrationRequest artistRegistrationRequest){
        return artistService.registerArtist(artistRegistrationRequest);
    }

    /**
     * Endpoint shows all registered artists.
     */
    @CustomerAuthorization
    @GetMapping("/artists")
    public List<Artist> getAllArtists(){
        return artistService.getAllArtists();
    }

    /**
     * Endpoint shows all artists established in a specific year.
     */
    @GetMapping("/artists/{established}")
    public List<Artist> getArtistsByYearEstablished(@PathVariable int established){
        return artistService.getArtistsByYearEstablished(established);
    }

    /**
     * Endpoint shows an artist with a specific Id.
     */
    @GetMapping("/artist/{artistId}")
    public Artist getArtistByArtistId(@PathVariable Long artistId){
        return artistService.getArtistByArtistId(artistId);
    }

    /**
     * Endpoint to show an artist with a specific name.
     */
    @GetMapping("/artist/{artistName}")
    public Artist getArtistByArtistName(@PathVariable String artistName){
        return artistService.getArtistByArtistName(artistName);
    }

    /**
     * Endpoint deletes artist with a specific Id.
     */
    @DeleteMapping("/artist/{artistId}")
    public void deleteArtist(@PathVariable Long artistId){
        artistService.deleteArtist(artistId);
    }
}