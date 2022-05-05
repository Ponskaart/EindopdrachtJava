package nl.bd.eindopdrachtjava.repositories;

import nl.bd.eindopdrachtjava.models.entities.CoverArt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoverArtRepository extends JpaRepository<CoverArt, Long> {
}
