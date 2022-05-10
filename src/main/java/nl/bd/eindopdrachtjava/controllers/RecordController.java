package nl.bd.eindopdrachtjava.controllers;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.annotations.*;
import nl.bd.eindopdrachtjava.models.requests.RecordRegistrationRequest;
import nl.bd.eindopdrachtjava.services.RecordService;
import nl.bd.eindopdrachtjava.models.entities.Record;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class governs all the Record related endpoints.
 */
@RestController
@RequestMapping(path = "recordstore")
@AllArgsConstructor
public class RecordController {
    private final RecordService recordService;

    /**
     * This endpoint will be the default when loading up the API, hence there is no path specified. Endpoint shows all
     * records in the database.
     */
    @AllUserAuthorization
    @GetMapping()
    public List<Record> getAllRecords(){
        return recordService.getAllRecords();
    }

    /**
     * Shows all records of a specific artist.
     * */
    @AllUserAuthorization
    @GetMapping("/records/artist/{artistId}")
    public List<Record> getRecordsByArtist(@PathVariable Long artistId){
        return recordService.getRecordsByArtist(artistId);
    }

    /**
     * Shows record with specific Id.
     */
    @AllUserAuthorization
    @GetMapping("/record/{recordId}")
    public Record getRecordById(@PathVariable Long recordId) {
        return recordService.getRecordById(recordId);
    }

    /**
     * Shows record with specific title.
     */
    @AllUserAuthorization
    @GetMapping("/record/title/{title}")
    public Record getRecordByTitle(@PathVariable String title){
        return recordService.getRecordByTitle(title);
    }

    /**
     * Shows all records with a specific genre.
     * */
    @AllUserAuthorization
    @GetMapping("/record/genre/{genre}")
    public List<Record> getRecordsByGenre(@PathVariable String genre){
        return recordService.getRecordsByGenre(genre);
    }

    /**
     * Endpoint uses data provided by the user in the recordRegistrationRequest to fill a new Record entity and saves it
     * in the database.
     */
   @AdminAndEmployeeAuthorization
    @PostMapping("/record")
    public Record registerNewRecord(@RequestBody RecordRegistrationRequest recordRegistrationRequest){
        return recordService.registerRecord(recordRegistrationRequest);
    }

    /**
     * Updates an existing record, if recordId does not exist it publishes a new record.
     */
    @AdminAndEmployeeAuthorization
    @PutMapping("/record/{recordId}")
    public Record updateRecord(@PathVariable Long recordId,
                               @RequestBody RecordRegistrationRequest recordRegistrationRequest){
        return recordService.updateRecord(recordRegistrationRequest, recordId);
    }

    /**
     * Deletes record with specific id.
     */
    @AdminAuthorization
    @DeleteMapping("/record/{recordId}")
    public void deleteRecord(@PathVariable Long recordId){
        recordService.deleteRecord(recordId);
    }
}