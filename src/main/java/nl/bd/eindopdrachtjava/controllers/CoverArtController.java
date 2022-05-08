package nl.bd.eindopdrachtjava.controllers;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.services.CoverArtService;
import nl.bd.eindopdrachtjava.models.entities.Record;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@RequestMapping(path = "recordstore")
@RestController
public class CoverArtController {
    private CoverArtService coverArtService;

    /**
     * Uploads an image to the database and assigns it to a record.
     */
    @PostMapping("/uploadcoverart/{recordId}")
    public Record uploadCoverArt(@RequestBody MultipartFile multipartImage,
                                 @PathVariable Long recordId) throws IOException {
            return coverArtService.uploadCoverArt(multipartImage, recordId);
    }

    /**
     * Downloads an image from the database.
     */
    //TODO Does this download an image? Or does it only show an image? Maybe add proper download method.
    @GetMapping(value = "/viewcoverart/{recordId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ByteArrayResource downloadCoverArt(@PathVariable Long recordId) {
        return coverArtService.downloadCoverArt(recordId);
    }
}