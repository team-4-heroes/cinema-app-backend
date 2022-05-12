package kea.dat3.services;

import kea.dat3.dto.MovieDetailResponse;
import kea.dat3.dto.MovieRequest;
import kea.dat3.dto.MovieResponse;
import kea.dat3.entities.Actor;
import kea.dat3.entities.Movie;
import kea.dat3.error.ActorNotFoundException;
import kea.dat3.error.MovieNotFoundException;
import kea.dat3.repositories.ActorRepository;
import kea.dat3.repositories.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class MovieService {

    private MovieRepository movieRepository;

    private ActorRepository actorRepository;

    public Set<MovieResponse> getMovies() {
        return MovieResponse.getMoviesFromEntities(movieRepository.findAll());
    }

    public Set<MovieDetailResponse> getMoviesByKeyword(String keyword) {
        List<Movie> movies = movieRepository.findByTitleContainingOrDescriptionContainingAllIgnoreCase(keyword, keyword);
        return getMovieResponses(movies);
    }

    public Set<MovieDetailResponse> getMoviesByReleaseYear(int releaseYear) {
        List<Movie> movies = movieRepository.findByReleaseYear(releaseYear);
        return getMovieResponses(movies);
    }

    public Set<MovieDetailResponse> getMoviesByActor(Long actorId) {
        Actor actor = actorRepository.findById(actorId).orElseThrow(() -> new ActorNotFoundException(actorId));
        List<Movie> movies = movieRepository.findByActorsEquals(actor);
        return MovieDetailResponse.getMoviesFromEntities(movies);
    }

    private Set<MovieDetailResponse> getMovieResponses(List<Movie> movies) {
        return MovieDetailResponse.getMoviesFromEntities(movies);
    }

    public MovieDetailResponse getMovie(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
        return new MovieDetailResponse(movie);
    }

    public MovieDetailResponse addMovie(MovieRequest body) {
        Movie movie = new Movie(body);
        return new MovieDetailResponse(movieRepository.save(movie));
    }

    public MovieDetailResponse editMovie(Long id, MovieRequest body) {
        Movie movie = new Movie(body);
        movie.setId(id);
        return new MovieDetailResponse(movieRepository.save(movie));
    }

    public void deleteMovie(Long id) {
        movieRepository.delete(movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id)));
    }

    public MovieDetailResponse addActorToMovie(Long movieId, Long actorId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException(movieId));
        Actor actor = actorRepository.findById(actorId).orElseThrow(() -> new ActorNotFoundException(actorId));
        movie.getActors().add(actor);
        return new MovieDetailResponse(movieRepository.save(movie));
    }
}