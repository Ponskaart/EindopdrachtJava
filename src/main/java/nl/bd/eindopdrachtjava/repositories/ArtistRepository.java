package nl.bd.eindopdrachtjava.repositories;

import nl.bd.eindopdrachtjava.models.entityModels.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    /**
     * This is a lazy way to create a custom query, but spring boot sorts out al the details from the method name.
     * Should change it to a proper query to prevent things such as sql injection.
     */
    Artist findArtistByArtistName(String ArtistName);
}
