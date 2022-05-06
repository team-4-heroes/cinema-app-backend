package kea.dat3.entities;

import kea.dat3.dto.MovieRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Size(max = 4)
    private int releaseYear;

    @Size(max = 3)
    private int length; // in minutes

    // TODO: Map of AccessFactors here

    private double basePrice;

    /*@OneToMany(mappedBy = "genre")
    Set<MovieGenre> movieGenres;

    // TODO: Possibly add Set of actors to Movie. nice to have
     */

    public Movie(MovieRequest body) {
        this.title = body.getTitle();
        this.description = body.getDescription();
        this.releaseYear = getReleaseYear();
        this.length = body.getLength();
        this.basePrice = body.getBasePrice();
        // access factors
        // genres
    }
}
