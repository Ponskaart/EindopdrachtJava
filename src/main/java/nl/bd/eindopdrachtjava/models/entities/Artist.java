package nl.bd.eindopdrachtjava.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Lombok annotations reduce the amount of boilerplate code needed to run the application, here we make lombok generate
 * our getters, setters and constructors with the @AllArgsConstructor, @Getter and @Setter annotations. I also use
 * the @NoArgsConstructor annotation to generate a default constructor which requires no arguments. Using the @Builder
 * annotation I am able to easily create a builder design pattern.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
@Builder
public class Artist {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(
            name = "artist_Id",
            updatable = false
    )
    private Long artistId;

    @Column
    private String artistName;

    @Column
    private int established;

    /**
     * The @JsonIgnore annotation is used to prevent a recursive loop when the relationship between artist and record is
     * accessed in the api. CascadeType.ALL ensures no records of deleted artist remain in database.
     */
    @JsonIgnore
    @OneToMany(
            mappedBy = "artist",
            cascade = CascadeType.ALL
    )
    private Set<Record> records = new HashSet<>();

    public Artist(String artistName, int established) {
        this.artistName = artistName;
        this.established = established;
    }
}