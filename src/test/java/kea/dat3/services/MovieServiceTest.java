package kea.dat3.services;

import kea.dat3.dto.MovieResponse;
import kea.dat3.entities.Movie;
import kea.dat3.entities.pegi.AgeLimit;
import kea.dat3.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    MovieRepository repository;

    @Mock
    MovieService service;

    Movie CUT_1 = new Movie(100L, "abc", "descr", 2000, 120, 125d, AgeLimit.PEGI_3, Collections.emptySet(), Collections.emptySet(), LocalDateTime.now(), LocalDateTime.now());
    Movie CUT_2 = new Movie(200L, "dfg", "descr", 2000, 120, 125d, AgeLimit.PEGI_7, Collections.emptySet(), Collections.emptySet(), LocalDateTime.now(), LocalDateTime.now());

    @BeforeEach
    void setup() {
        service = new MovieService(repository);
    }

    @Test
    void getMovies() {
        Mockito.when(repository.findAll()).thenReturn(List.of(
            new Movie(),
            new Movie()
        ));
        Set<MovieResponse> mResponses = service.getMovies();
        assertEquals(2, mResponses.size());
    }

    @Test
    void deleteMovie_idExists() {
        var id = 100L;

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(CUT_1));
        service.deleteMovie(id);
        Mockito.verify(repository).delete(any(Movie.class));
    }

    @Test
    void deleteMovie_idDoesNotExist() {
        var id = 300L;

        Exception exception = assertThrows(Exception.class, () -> service.deleteMovie(id));
        assertEquals("404 NOT_FOUND \"Movie with id '300' not found\"", exception.getMessage());
    }
}