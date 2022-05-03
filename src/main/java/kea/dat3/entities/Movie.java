package kea.dat3.entities;

import kea.dat3.entities.ratings.PegiAgeRating;
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
    private Integer id;

    private String title;

    private String description;

    @Size(max = 4)
    private int releaseYear;

    @Size(max = 3)
    private int length; // in minutes

    private PegiAgeRating pegiAgeRating;

    @OneToOne
    @JoinColumn(name = "price_id", referencedColumnName = "id")
    private Price price;

    @OneToMany(mappedBy = "genre")
    Set<MovieGenre> movieGenres;
}
