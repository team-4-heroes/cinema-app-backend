package kea.dat3.services;

import kea.dat3.dto.ActorResponse;
import kea.dat3.dto.MovieDetailResponse;
import kea.dat3.dto.MovieResponse;
import kea.dat3.entities.Movie;
import kea.dat3.entities.builders.ActorBuilder;
import kea.dat3.entities.builders.MovieBuilder;
import kea.dat3.repositories.ActorRepository;
import kea.dat3.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    Movie m_1 = MovieBuilder.create("Galaxy Quest", "Really. It's not a Star Trek spoof.", 1999).build();
    Movie m_2 = MovieBuilder.create("Star Trek", "Unruly Kirk meets Cool Spock and sparks fly", 2009).build();
    Movie m_3 = MovieBuilder.create("Battlestar Galactica", "Humans struggle to survive in a galaxy infested with Cylons. Nr six in a red dress", 2004).build();

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
        var expected = "galaxy";
        Mockito.when(movieRepository.findByTitleContainingOrDescriptionContainingAllIgnoreCase(expected, expected)).thenReturn(List.of(m_1, m_3));
        Set<MovieDetailResponse> mResponses = service.getMoviesByKeyword(expected);
        assertEquals(2, mResponses.size());
    }

    @Test
    void getMoviesByReleaseYear_movieIsFound() {
        var expected = 2009;

        Mockito.when(movieRepository.findByReleaseYear(expected)).thenReturn(List.of(m_2));
        Set<MovieDetailResponse> mResponses = service.getMoviesByReleaseYear(expected);
        assertEquals(1, mResponses.size());
    }

    @Test
    void addActorToMovie() {
        var movieId = m_3.getId();
        var actorId = 444L;
        var actor = ActorBuilder.create()
                .addFirstName("Tricia")
                .addLastName("Helfer")
                .addBirthDate(LocalDate.now())
                .addId(actorId)
                .addCreated(LocalDateTime.now())
                .addUpdated(LocalDateTime.now())
                .build();

        Mockito.when(movieRepository.save(m_3)).thenReturn(m_3);
        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(m_3));
        Mockito.when(actorRepository.findById(actor.getId())).thenReturn(Optional.of(actor));

        var actualMovie = service.addActorToMovie(movieId, actor.getId());
        var actualActors = actualMovie.getActors();
        assertTrue(!actualActors.isEmpty());

        ActorResponse actualActor = null;
        for (ActorResponse aResponse : actualActors) {
            if (aResponse.getId() == actor.getId()
                    && aResponse.getFirstName().equals(actor.getFirstName())
                    && aResponse.getLastName().equals(actor.getLastName())) {
                actualActor = aResponse;
            }
        }
        assertNotNull(actualActor);
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