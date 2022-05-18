package kea.dat3.services;

import kea.dat3.dto.MovieDetailResponse;
import kea.dat3.dto.MovieResponse;
import kea.dat3.entities.Actor;
import kea.dat3.entities.Movie;
import kea.dat3.entities.MovieFactory;
import kea.dat3.entities.pegi.AgeLimit;
import kea.dat3.repositories.ActorRepository;
import kea.dat3.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    MovieRepository movieRepository;

    @Mock
    ActorRepository actorRepository;

    MovieService service;

    Movie m_1 = MovieFactory.create(100L, "Aeryn", "thisLadyIsHot", 2000, 120, 125d, AgeLimit.PEGI_18);
    Movie m_2 = MovieFactory.create(200L, "xxx", "aerynIsEpicc", 2001, 120, 125d, AgeLimit.PEGI_7);
    Movie m_3 = MovieFactory.create(300L, "Battlestar Galactica", "robot takeover", 2001, 120, 125d, AgeLimit.PEGI_7);

    @BeforeEach
    void setup() {
        service = new MovieService(movieRepository, actorRepository);
    }

    @Test
    void getMovies() {
        Mockito.when(movieRepository.findAll()).thenReturn(List.of(
                new Movie(),
                new Movie()
        ));
        Set<MovieResponse> mResponses = service.getMovies();
        assertEquals(2, mResponses.size());
    }

    @Test
    void getMoviesByKeyword() {
        var expected = "aeryn";
        Mockito.when(movieRepository.findByTitleContainingOrDescriptionContainingAllIgnoreCase(expected, expected)).thenReturn(List.of(m_1, m_2));
        Set<MovieDetailResponse> mResponses = service.getMoviesByKeyword(expected);
        assertEquals(2, mResponses.size());
    }

    @Test
    void getMoviesByReleaseYear_movieIsFound() {
        var expected = 2000;

        Mockito.when(movieRepository.findByReleaseYear(expected)).thenReturn(List.of(m_1));
        Set<MovieDetailResponse> mResponses = service.getMoviesByReleaseYear(expected);
        assertEquals(1, mResponses.size());
    }

    @Test
    void addActorToMovie() {
        var movieId = m_3.getId();
        var actorId = 444L;
        var actor = new Actor(actorId, "Tricia", "Helfer", LocalDateTime.now(), new HashSet<>(), LocalDateTime.now(), LocalDateTime.now());

        Mockito.when(movieRepository.save(m_3)).thenReturn(m_3);
        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(m_3));
        Mockito.when(actorRepository.findById(actor.getId())).thenReturn(Optional.of(actor));

        var actualMovie = service.addActorToMovie(movieId, actor.getId());

        assertTrue(!actualMovie.getActors().isEmpty());
        assertTrue(actualMovie.getActors().contains(actor));
    }

    @Test
    void deleteMovie_idExists() {
        var id = 100L;

        Mockito.when(movieRepository.findById(id)).thenReturn(Optional.of(m_1));
        service.deleteMovie(id);
        Mockito.verify(movieRepository).delete(any(Movie.class));
    }

    @Test
    void deleteMovie_idDoesNotExist() {
        var id = 300L;

        Exception exception = assertThrows(Exception.class, () -> service.deleteMovie(id));
        assertEquals("404 NOT_FOUND \"Movie with id '300' not found\"", exception.getMessage());
    }
}