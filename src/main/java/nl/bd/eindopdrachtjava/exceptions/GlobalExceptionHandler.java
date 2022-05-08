package nl.bd.eindopdrachtjava.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class globaly handles all the exceptions using the @ControllerAdvice annotation.
 */
@ControllerAdvice
public class GlobalExceptionHandler
        extends ResponseEntityExceptionHandler {

    /**
     * Handles ResourceNotFoundException, returns the exception, http status and a message to ensure the origins of the
     * exception are properly communicated to the user.
     */
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<Object> handleResourceNotFoundConflict(ResourceNotFoundException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    /**
     * Handles ResourceAlreadyExistsException, returns the exception, http status and a message to ensure the origins
     * of the exception are properly communicated to the user.
     */
    @ExceptionHandler(value = {ResourceAlreadyExistsException.class})
    protected ResponseEntity<Object> handleResourceAlreadyExistsConflict
    (ResourceAlreadyExistsException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    /**
     * Exception thrown when an invalid file is uploaded.
     */
    @ExceptionHandler(value = {InvalidFileException.class})
    protected ResponseEntity<Object> handleInvalidFileConflict(InvalidFileException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }
}