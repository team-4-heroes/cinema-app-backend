package kea.dat3.services;

import kea.dat3.dto.MovieRequest;
import kea.dat3.dto.MovieResponse;
import kea.dat3.entities.Movie;
import kea.dat3.repositories.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

public class MovieService {
/*
    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

   public Set<MovieResponse> getMovies() {
        List<Movie> movies = movieRepository.findAll();
        Set<MovieResponse> mResponses = MovieResponse.getMoviesFromEntities(movies);
        return mResponses;

   }

    public Movie getMovie(Long id) {
        return movieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"List with id '"+id+"' not found"));
    }

    public MovieResponse addMovie(MovieRequest body) {
        Movie movie = new Movie(body);
        return new MovieResponse(movie);
    }

    public Movie editMovie(Long id, Movie movie) {
        movie.setId(id);
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.delete(movieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Movie with id '"+id+"' not found")));
        System.out.println("Movie with id '"+id+"' deleted");
    }*/
}
