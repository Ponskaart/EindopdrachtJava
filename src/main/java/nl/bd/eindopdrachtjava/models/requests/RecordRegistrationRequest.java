package nl.bd.eindopdrachtjava.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.bd.eindopdrachtjava.models.entityModels.Artist;

@AllArgsConstructor
@Getter
public class RecordRegistrationRequest {

    private final String artistName;
    private final String title;
    private final String genre;
    private final String label;
    private final String color;
    private final int year;
    private final String country;
    private final boolean isShaped;
    private final boolean isPicturedisk;
}