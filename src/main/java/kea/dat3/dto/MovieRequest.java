package kea.dat3.dto;

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
    // private Set<AccessFactor> accessFactors
    private double price;
}