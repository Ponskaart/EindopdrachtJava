package nl.bd.eindopdrachtjava.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class Artist {
    @Id
    @GeneratedValue(strategy = IDENTITY)
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