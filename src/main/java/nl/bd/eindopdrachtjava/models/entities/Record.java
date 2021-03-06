package nl.bd.eindopdrachtjava.models.entities;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

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
public class Record {
    /**
     * Marking recordId with @Id annotation to generate a primary key for the database. The IDENTITY strategy in
     * the @GeneratedValue annotation makes sure that the id counter always starts at 1, even if the database holds
     * more tables.
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column
            (name = "record_Id")
    private Long recordId;

    /**
     * Though not needed, I mark the other variables with @Column to signify that they are a column in the database.
     * Should I at a later date want to change the specifications of a specific column, I can do it easily. For example
     * changing the column name, or making the contents non-nullable.
     */

    @ManyToOne
    @JoinColumn(
            name = "artist_Id",
            referencedColumnName = "artist_Id"
    )
    private Artist artist;

    @OneToOne(orphanRemoval = true,
            cascade = CascadeType.PERSIST,
            mappedBy = "record"
    )
    private CoverArt coverArt;

    @Column
    private String title;

    @Column
    private String genre;

    @Column
    private String label;

    @Column
    private String color;

    @Column
    private int year;

    @Column
    private String country;

    @Column
    private double price;

    @Column
    private int qtyInStock;
}