package kea.dat3.entities;

import kea.dat3.entities.movieAccessFactors.AccessFactor;
import kea.dat3.entities.movieAccessFactors.AgeLimitPegi;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Map;
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

    // TODO: Map of AccessFactors here, do with PEGI for the sake of example

    private double basePrice;

    @OneToMany(mappedBy = "genre")
    Set<MovieGenre> movieGenres;
}
