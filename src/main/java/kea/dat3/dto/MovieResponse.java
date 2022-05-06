package kea.dat3.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kea.dat3.entities.Movie;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieResponse {

    private Long id;
    private String title;
    private String description;
    private int releaseYear;
    private int length;
    // private Map<AccessFactor> accessFactors; // age limits and warnings such as "VIOLENCE"
    private double price;
    // private Set<Genre> genres;

    public MovieResponse(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.description = movie.getDescription();
        this.releaseYear = movie.getReleaseYear();
        this.length = movie.getLength();
        this.price = movie.getBasePrice();
    }

    public static Set<MovieResponse> getMoviesFromEntities(List<Movie> movies) {
        return movies.stream().map(movie-> new MovieResponse(movie)).collect(Collectors.toSet());
    }
}

