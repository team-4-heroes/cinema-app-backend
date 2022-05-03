package kea.dat3.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kea.dat3.entities.Genre;
import kea.dat3.entities.Price;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieResponse {

    private Integer id;
    private String title;
    private String description;
    private int releaseYear;
    private int lenght;
    private Price price;
    // private Set<Genre> genres; TODO: How do we handle Genre?

}
