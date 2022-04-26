package nl.bd.eindopdrachtjava.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Wrapper class to easily hand 2 variables to the registerArtist method.
 */
@AllArgsConstructor
@Getter
public class ArtistRegistrationRequest {
    private final String artistName;
    private final int established;
}
