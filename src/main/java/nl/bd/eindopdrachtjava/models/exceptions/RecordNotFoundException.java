package nl.bd.eindopdrachtjava.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {
    RecordNotFoundException(Long recordId){
        super("Record with id " + "'" + recordId + "' " + "was not found");
    }
}
