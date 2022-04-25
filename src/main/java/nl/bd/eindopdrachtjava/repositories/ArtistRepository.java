package nl.bd.eindopdrachtjava.repositories;

import nl.bd.eindopdrachtjava.models.entityModels.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist findArtistByArtistName(String ArtistName);
}
