package kea.dat3.services;

import kea.dat3.dto.MovieRequest;
import kea.dat3.dto.MovieResponse;
import kea.dat3.entities.Actor;
import kea.dat3.entities.Movie;
import kea.dat3.repositories.ActorRepository;
import kea.dat3.repositories.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class MovieService {

    private MovieRepository movieRepository;

    private ActorRepository actorRepository;

    public Set<MovieResponse> getMovies() {
        return getMovieResponses(movieRepository.findAll());
    }

    public Set<MovieResponse> getMoviesByKeyword(String keyword) {
        List<Movie> movies = movieRepository.findByTitleContainingOrDescriptionContainingAllIgnoreCase(keyword, keyword);
        return getMovieResponses(movies);
    }

    public Set<MovieResponse> getMoviesByReleaseYear(int releaseYear) {
        List<Movie> movies = movieRepository.findByReleaseYear(releaseYear);
        return getMovieResponses(movies);
    }

    private Set<MovieResponse> getMovieResponses(List<Movie> movies) {
        return MovieResponse.getMoviesFromEntities(movies);
    }

    public MovieResponse getMovie(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List with id '" + id + "' not found"));
        return new MovieResponse(movie);
    }

    public MovieResponse addMovie(MovieRequest body) {
        Movie movie = new Movie(body);
        return new MovieResponse(movieRepository.save(movie));
    }

    public MovieResponse editMovie(Long id, MovieRequest body) {
        Movie movie = new Movie(body);
        movie.setId(id);
        return new MovieResponse(movieRepository.save(movie));
    }

    public void deleteMovie(Long id) {
        movieRepository.delete(movieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie with id '" + id + "' not found")));
        System.out.println("Movie with id '" + id + "' deleted");
    }

    public MovieResponse addActorToMovie(Long movieId, Long actorId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie with id '" + movieId + "' not found"));
        Actor actor = actorRepository.findById(actorId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor with id '" + actorId + "' not found"));
        movie.getActors().add(actor);
        return new MovieResponse(movieRepository.save(movie));
    }
}
