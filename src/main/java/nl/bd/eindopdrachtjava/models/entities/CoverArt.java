package nl.bd.eindopdrachtjava.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class CoverArt {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column
    private Long coverArtId;

    /**
     * The @Lob annotation signifies to the database that it might receive a large object of bytes.
     */
    @Lob
    @Column
    private byte[] content;

    /**
     * One to one relationship with a record entity, one record has one cover art.
     */
    @JsonIgnore
    @OneToOne
    @JoinColumn(
            name = "record_Id",
            referencedColumnName = "record_Id"
    )
    private Record record;
}