package kea.dat3.dto;

import kea.dat3.entities.pegi.AgeLimit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String posterUrl;
}