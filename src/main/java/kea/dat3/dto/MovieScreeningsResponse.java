package kea.dat3.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kea.dat3.entities.Movie;
import kea.dat3.entities.Screening;
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
public class MovieScreeningsResponse {

    private Long id;
    private String title;
    private String description;
    private int releaseYear;
    private int lengthInMinutes;
    private String posterUrl;
    private Set<String> rooms;
    private Set<LocalDateTime> startTimes;

    public MovieScreeningsResponse(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.description = movie.getDescription();
        this.releaseYear = movie.getReleaseYear();
        this.lengthInMinutes = movie.getLengthInMinutes();
        this.posterUrl = movie.getPosterUrl();
        // get rooms and startTimes for the screenings of this movie
        this.rooms = movie.getScreenings().stream().map(screening -> screening.getRoom().getName()).collect(Collectors.toSet());
        this.startTimes = movie.getScreenings().stream().map(Screening::getStartTime).collect(Collectors.toSet());
    }

    public static Set<MovieScreeningsResponse> getMoviesFromEntities(List<Movie> movies) {
        return movies.stream().map(movie -> new MovieScreeningsResponse(movie)).collect(Collectors.toSet());
    }
}