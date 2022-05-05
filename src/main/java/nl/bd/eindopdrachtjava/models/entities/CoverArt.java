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

    @Lob
    @Column
    private byte[] content;

    @Column
    private String title;

    @OneToOne
    @JoinColumn
            (name = "record_Id",
                    referencedColumnName = "record_Id")
    private Record record;
}
