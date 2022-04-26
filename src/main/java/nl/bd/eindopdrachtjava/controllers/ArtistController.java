package nl.bd.eindopdrachtjava.controllers;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.entities.Artist;
import nl.bd.eindopdrachtjava.models.requests.ArtistRegistrationRequest;
import nl.bd.eindopdrachtjava.repositories.ArtistRepository;
import nl.bd.eindopdrachtjava.services.ArtistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("recordstore")
@AllArgsConstructor
public class ArtistController {
    private final ArtistRepository artistRepository;
    private final ArtistService artistService;

    @PostMapping("artist")
    public Artist registerArtist(@RequestBody ArtistRegistrationRequest artistRegistrationRequest){
        return artistService.registerArtist(artistRegistrationRequest);
    }

    /**
     * Method shows all registered artists.
     */
    @GetMapping("/artists")
    public List<Artist> getAllArtists(){
        return artistService.getAllArtists();
    }

    /**
     * Method shows all artists established in a specific year.
     */
    @GetMapping("/artists/{established}")
    public List<Artist> getArtistsByYearEstablished(@PathVariable int established){
        return artistService.getArtistsByYearEstablished(established);
    }

    /**
     * Method shows an artist with a specific Id.
     */
    @GetMapping("/artist/{artistId}")
    public Artist getArtistByArtistId(@PathVariable Long artistId){
        return artistService.getArtistByArtistId(artistId);
    }

    @GetMapping("artist/{artistName}")
    public Artist getArtistByArtistName(@PathVariable String artistName){
        return artistService.getArtistByArtistName(artistName);
    }
}