package kea.dat3.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kea.dat3.entities.Actor;
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
public class MovieDetailResponse {

    private Long id;
    private String title;
    private String description;
    private int releaseYear;
    private int lengthInMinutes;
    private double price;
    private AgeLimit ageLimit;
    private Set<Actor> actors;// return ActorResponse, which must not contain Movie (to avoid infinity query)
    private Set<Screening> screenings;
    private Set<Genre> genres;
    private LocalDateTime created;
    private LocalDateTime updated;

    public MovieDetailResponse(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.description = movie.getDescription();
        this.releaseYear = movie.getReleaseYear();
        this.lengthInMinutes = movie.getLengthInMinutes();
        this.price = movie.getBasePrice();
        this.ageLimit = movie.getAgeLimit();
        //this.actors = movie.getActors();
        //this.screenings = movie.getScreenings();
        //this.genres = movie.getGenres();
        this.created = movie.getCreated();
        this.updated = movie.getUpdated();
    }

    public static Set<MovieDetailResponse> getMoviesFromEntities(List<Movie> movies) {
        return movies.stream().map(movie -> new MovieDetailResponse(movie)).collect(Collectors.toSet());
    }
}