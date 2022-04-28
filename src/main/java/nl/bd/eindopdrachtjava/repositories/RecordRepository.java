package nl.bd.eindopdrachtjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import nl.bd.eindopdrachtjava.models.entities.Record;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    /**
     * Custom queries to retrieve specific data from the database.
     */
    @Query("SELECT u FROM Artist u WHERE u.artistId = :artistid")
    Optional<List<Record>> findByArtistArtistId(
            @Param("artistid") Long artistId);

    @Query("SELECT u FROM Record u WHERE u.genre = :genre")
    Optional<List<Record>> findRecordByGenre(
            @Param("genre") String genre);

    @Query("SELECT u FROM Record u WHERE u.title = :title")
    Optional<Record> findRecordByTitle(
            @Param("title") String title);
}