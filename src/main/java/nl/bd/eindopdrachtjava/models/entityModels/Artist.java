package nl.bd.eindopdrachtjava.models.entityModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

}
