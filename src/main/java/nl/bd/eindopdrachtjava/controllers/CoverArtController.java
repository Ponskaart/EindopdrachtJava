package nl.bd.eindopdrachtjava.controllers;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.repositories.CoverArtRepository;
import nl.bd.eindopdrachtjava.services.CoverArtService;
import nl.bd.eindopdrachtjava.models.entities.Record;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@RequestMapping(path = "recordstore")
@RestController
public class CoverArtController {
    private CoverArtRepository coverArtRepository;
    private CoverArtService coverArtService;

    @PostMapping("/uploadcoverart/{recordId}")
    public Record uploadCoverArt(@RequestParam MultipartFile multipartImage, @PathVariable Long recordId) throws IOException {
        return coverArtService.uploadCoverArt(multipartImage, recordId);
    }
}
