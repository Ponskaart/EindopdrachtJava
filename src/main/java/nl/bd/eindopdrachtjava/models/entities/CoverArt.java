package nl.bd.eindopdrachtjava.models.entities;

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
    private Long coverArtId;

    /**
     * The @Lob annotation signifies to the databse that it might receive a large object of bytes.
     */
    @Lob
    @Column
    private byte[] content;

    @Column
    private String title;

    /**
     * One to one relationship with a record entity, one record has one cover art.
     */
    @OneToOne
    @JoinColumn
            (name = "recordId",
                    referencedColumnName = "recordId")
    private Record record;
}
