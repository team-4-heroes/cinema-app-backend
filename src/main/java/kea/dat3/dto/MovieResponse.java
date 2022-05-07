package kea.dat3.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kea.dat3.entities.Genre;
import kea.dat3.entities.Movie;
import kea.dat3.entities.Screening;
import kea.dat3.entities.pegi.AgeLimit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
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
    private double price;
    private AgeLimit ageLimit;
    private Set<Screening> screenings;
    private Set<Genre> genres;
    private LocalDateTime created;
    private LocalDateTime updated;

    public MovieResponse(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.description = movie.getDescription();
        this.releaseYear = movie.getReleaseYear();
        this.length = movie.getLengthInMinutes();
        this.price = movie.getBasePrice();
        this.ageLimit = movie.getAgeLimit();
        this.screenings = movie.getScreenings();
        this.genres = movie.getGenres();
        this.created = movie.getCreated();
        this.updated = movie.getUpdated();
    }

    public static Set<MovieResponse> getMoviesFromEntities(List<Movie> movies) {
        return movies.stream().map(movie-> new MovieResponse(movie)).collect(Collectors.toSet());
    }
}

