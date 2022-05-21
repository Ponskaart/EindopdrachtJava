package nl.bd.eindopdrachtjava.controllers;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.annotations.*;
import nl.bd.eindopdrachtjava.models.entities.Artist;
import nl.bd.eindopdrachtjava.models.enums.UserRole;
import nl.bd.eindopdrachtjava.models.requests.ArtistRegistrationRequest;
import nl.bd.eindopdrachtjava.services.ArtistService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Class governs the artist related endpoints.
 */
@RestController
@RequestMapping("recordstore/artists")
@AllArgsConstructor
public class ArtistController {
    private final ArtistService artistService;

    /**
     * Endpoint creates a new artist object and saves it to te database.
     */
    @AdminAndEmployeeAuthorization
    @PostMapping("/register")
    public Artist registerArtist(@RequestBody ArtistRegistrationRequest artistRegistrationRequest){
        return artistService.registerArtist(artistRegistrationRequest);
    }

    /**
     * Endpoint shows all registered artists.
     */
    @AllUserAuthorization
    @GetMapping()
    public List<Artist> getAllArtists(){
        return artistService.getAllArtists();
    }

    /**
     * Endpoint shows all artists established in a specific year.
     */
    @AllUserAuthorization
    @GetMapping("/established/{established}")
    public List<Artist> getArtistsByYearEstablished(@PathVariable int established){
        return artistService.getArtistsByYearEstablished(established);
    }

    /**
     * Endpoint shows an artist with a specific Id.
     */
    @AllUserAuthorization
    @GetMapping("/{artistId}")
    public Artist getArtistByArtistId(@PathVariable Long artistId){
        return artistService.getArtistByArtistId(artistId);
    }

    /**
     * Endpoint to show an artist with a specific name.
     */
    @AllUserAuthorization
    @GetMapping("/name/{artistName}")
    public Artist getArtistByArtistName(@PathVariable String artistName){
        return artistService.getArtistByArtistName(artistName);
    }

    /**
     * Updates an artist object and overwrites the old entry in the database
     */
    @AdminAndEmployeeAuthorization
    @PutMapping("/{artistId}")
    public Artist updateArtist(@PathVariable Long artistId,
                               @RequestBody ArtistRegistrationRequest artistRegistrationRequest) {
        return artistService.updateArtist(artistRegistrationRequest, artistId);
    }

    /**
     * Endpoint deletes artist with a specific Id.
     */
    @AdminAuthorization
    @DeleteMapping("/{artistId}")
    public String deleteArtist(@PathVariable Long artistId){
        artistService.deleteArtist(artistId);
        return "Artist with id: " + artistId + " had been deleted";
    }
}