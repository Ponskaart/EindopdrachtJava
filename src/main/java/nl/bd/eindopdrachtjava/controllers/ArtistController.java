package nl.bd.eindopdrachtjava.controllers;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.entities.Artist;
import nl.bd.eindopdrachtjava.repositories.ArtistRepository;
import nl.bd.eindopdrachtjava.services.ArtistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("recordstore")
@AllArgsConstructor
public class ArtistController {
    private final ArtistRepository artistRepository;
    private final ArtistService artistService;

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

//    TODO add method to search for artist by name.
//    TODO add method to search for artist by Id.
}