package kea.dat3.api;

import kea.dat3.dto.MovieRequest;
import kea.dat3.dto.MovieResponse;
import kea.dat3.entities.Movie;
import kea.dat3.services.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("api/movies")
public class MovieController {

    MovieService movieService;

    @GetMapping
    Set<MovieResponse> getMovies() {
        return movieService.getMovies();
    }

    @GetMapping("/{id}")
    MovieResponse getMovie(@PathVariable Long id) throws Exception {
        return movieService.getMovie(id);
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
