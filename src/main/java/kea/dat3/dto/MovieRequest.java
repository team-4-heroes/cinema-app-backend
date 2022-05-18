package kea.dat3.dto;

import kea.dat3.entities.Genre;
import kea.dat3.entities.Screening;
import kea.dat3.entities.pegi.AgeLimit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest {

    private String title;

    private String description;

    private int releaseYear;

    private int lengthInMinutes; // in minutes

    private double basePrice;

    private AgeLimit ageLimit;

    private Set<Screening> screenings;

    private Set<Genre> genres;
}