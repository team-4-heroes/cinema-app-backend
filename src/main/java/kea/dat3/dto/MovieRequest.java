package kea.dat3.dto;

import kea.dat3.entities.Price;
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
    private int releaseYear;
    private int length;
    // private PegiRating pegiRating;
    private Price price;
}