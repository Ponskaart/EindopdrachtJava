package nl.bd.eindopdrachtjava.controllers;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.annotations.*;
import nl.bd.eindopdrachtjava.services.CoverArtService;
import nl.bd.eindopdrachtjava.models.entities.Record;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Contains endpoints for all CoverArt related functions.
 */
@AllArgsConstructor
@RequestMapping(path = "recordstore/coverart")
@RestController
public class CoverArtController {
    private CoverArtService coverArtService;

    /**
     * Uploads an image to the database and assigns it to a record.
     */
    @AdminAndEmployeeAuthorization
    @PostMapping("/upload/{recordId}")
    public Record uploadCoverArt
    (@RequestBody MultipartFile multipartImage, @PathVariable Long recordId) throws IOException {
            return coverArtService.storeCoverArt(multipartImage, recordId);
    }

    /**
     * Downloads an image from the database. Had to use response entity, could not get it to work otherwise.
     */
    @AllUserAuthorization
    @GetMapping(value = "/download/{recordId}")
    public ResponseEntity<Resource> download(@PathVariable Long recordId) {
        ByteArrayResource resource = (coverArtService.retrieveCoverArt(recordId));
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("CoverArt.png")
                                .build().toString())
                .body(resource);
    }

    /**
     * Shows an image from the database in the browser.
     */
    @AllUserAuthorization
    @GetMapping(value = "/view/{recordId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ByteArrayResource downloadCoverArt(@PathVariable Long recordId) {
        return coverArtService.retrieveCoverArt(recordId);
    }

    /**
     * Method is the same as the uploadCoverArt PostMapping, however this is a PutMapping. Purely a formality.
     */
    @AdminAndEmployeeAuthorization
    @PutMapping("/{recordId}")
    public Record updateCoverArt
            (@RequestBody MultipartFile multipartImage, @PathVariable Long recordId) throws IOException {
        return coverArtService.storeCoverArt(multipartImage, recordId);
    }

    /**
     * Deletes CoverArt entity from database with given Id.
     */
    @AdminAuthorization
    @DeleteMapping("/{coverArtId}")
    public void deleteCoverArt(@PathVariable Long coverArtId){
        coverArtService.deleteCoverArt(coverArtId);
    }
}