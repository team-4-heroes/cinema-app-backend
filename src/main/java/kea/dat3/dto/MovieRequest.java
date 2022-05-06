package kea.dat3.dto;

import kea.dat3.entities.Genre;
import kea.dat3.entities.Screening;
import kea.dat3.entities.pegi.AgeLimit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest {

    private String title;

    private String description;

    @Size(max = 4)
    private int releaseYear;

    @Size(max = 3)
    private int length; // in minutes

    private double basePrice;

    private AgeLimit ageLimit;

    private Set<Screening> screenings;

    private Set<Genre> genres;
}
