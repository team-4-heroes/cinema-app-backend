package kea.dat3.api;

import kea.dat3.dto.MovieRequest;
import kea.dat3.dto.MovieResponse;
import kea.dat3.entities.Movie;
import kea.dat3.services.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/movies")
public class MovieController {

    MovieService movieService;

    @GetMapping
    Set<MovieResponse> getMovies() {
        return movieService.getMovies();
    }

    @GetMapping("/{id}")
    MovieResponse getMovie(@PathVariable Long id) {
        return movieService.getMovie(id);
    }

    @GetMapping("/keyword")
    Set<MovieResponse> getMoviesByKeyword(@RequestParam String keyword) {
        return movieService.getMoviesByKeyword(keyword);
    }

    @PutMapping
    MovieResponse addMovie(@RequestBody MovieRequest body) {
        return movieService.addMovie(body);
    }

    @PatchMapping("/{id}")
    MovieResponse editMovie(@RequestBody MovieRequest body, @PathVariable Long id) {
        return movieService.editMovie(id, body);
    }

    @DeleteMapping("/{id}")
    void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }
}
