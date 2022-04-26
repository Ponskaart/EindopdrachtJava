package nl.bd.eindopdrachtjava.controllers;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.requests.RecordRegistrationRequest;
import nl.bd.eindopdrachtjava.repositories.RecordRepository;
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
    private final RecordRepository recordRepository;
    private final RecordService recordService;

    /**
     * This endpoint will be the default when loading up the API, hence there is no path specified. Endpoint shows all
     * records in the database.
     */
    @GetMapping()
    public List<Record> getAllRecords(){
        return recordService.getAllRecords();
    }

    /**
     * Endpoint uses data provided by the user in the recordRegistrationRequest to fill a new Record entiry and saves it
     * in the database.
     */
    @PostMapping("/record")
    public Record registerNewRecord(@RequestBody RecordRegistrationRequest recordRegistrationRequest){
        return recordService.registerRecord(recordRegistrationRequest);
    }

    /**
     * Updates an existing record, if recordId does not exist it publishes a new record.
     */
    @PutMapping("/record/{recordId}")
    public Record updateRecord(@PathVariable Long recordId, Record newRecord,
                               @RequestBody RecordRegistrationRequest recordRegistrationRequest){
        return recordService.updateRecord(newRecord, recordRegistrationRequest, recordId);
    }

    /**
     * Deletes record with specific id.
     */
    @DeleteMapping("/record/{recordId}")
    public void deleteRecord(@PathVariable Long recordId){
        recordService.deleteRecord(recordId);
    }

//    TODO add method to search for record by title.
//    TODO add method to search for record by Id.
//    TODO add method to show all records of a specific genre.
//    TODO add method to shows all records from a specific artist.
}