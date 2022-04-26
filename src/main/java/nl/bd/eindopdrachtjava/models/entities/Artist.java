package nl.bd.eindopdrachtjava.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

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
     * accessed in the api.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "artist")
    private Set<Record> records = new HashSet<>();
}