package kea.dat3.api;

import kea.dat3.dto.MovieDetailResponse;
import kea.dat3.dto.MovieRequest;
import kea.dat3.dto.MovieResponse;
import kea.dat3.services.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@AllArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/movies")
public class MovieController {

    private MovieService movieService;

    @GetMapping
    public Set<MovieResponse> getMovies() {
        return movieService.getMovies();
    }

    @GetMapping("/{id}")
    public MovieDetailResponse getMovie(@PathVariable Long id) {
        return movieService.getMovie(id);
    }

    @GetMapping("/keyword")
    public Set<MovieDetailResponse> getMoviesByKeyword(@RequestParam String keyword) {
        return movieService.getMoviesByKeyword(keyword);
    }

    @GetMapping("/search-actor")
    public Set<MovieDetailResponse> getMoviesByActor(@PathVariable Long actorId) {
        return movieService.getMoviesByActor(actorId);
    }

    @PostMapping("/add-actor")
    public MovieDetailResponse addActorToMovie(@PathVariable Long movieId, @PathVariable Long actorId) {
        return movieService.addActorToMovie(movieId, actorId);
    }

    @PutMapping
    public MovieDetailResponse addMovie(@RequestBody MovieRequest body) {
        return movieService.addMovie(body);
    }

    @PatchMapping("/{id}")
    public MovieDetailResponse editMovie(@RequestBody MovieRequest body, @PathVariable Long id) {
        return movieService.editMovie(id, body);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }
}
