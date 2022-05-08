package kea.dat3.services;

import kea.dat3.dto.MovieResponse;
import kea.dat3.entities.Movie;
import kea.dat3.entities.pegi.AgeLimit;
import kea.dat3.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MovieServiceInMemoryTest {

    MovieService service;

    @Autowired
    MovieRepository repository;

    Movie CUT_1 = new Movie(100L, "abc", "123thorIsBadass", 2000, 120, 125d, AgeLimit.PEGI_3, Collections.emptySet(), Collections.emptySet(), LocalDateTime.now(), LocalDateTime.now());
    Movie CUT_2 = new Movie(200L, "Thor", "bladescrbla", 2001, 120, 125d, AgeLimit.PEGI_7, Collections.emptySet(), Collections.emptySet(), LocalDateTime.now(), LocalDateTime.now());
    Movie CUT_3 = new Movie(300L, "hij", "bla", 2003, 120, 125d, AgeLimit.PEGI_7, Collections.emptySet(), Collections.emptySet(), LocalDateTime.now(), LocalDateTime.now());


    @BeforeEach
    void setup() {
        service = new MovieService(repository);
        repository.deleteAll();
        repository.save(CUT_1);
        repository.save(CUT_2);
        repository.save(CUT_3);
    }

    @Test
    void getMoviesByKeyword() {
        var keyword = "thor";

        Set<MovieResponse> mResponses = service.getMoviesByKeyword(keyword);
        assertEquals(2, mResponses.size());
        assertTrue(mResponses.stream().allMatch(m -> verifyContainsKeyword(keyword, m)));
    }

    private boolean verifyContainsKeyword(String keyword, MovieResponse m) {
        return containsIgnoreCase(m.getDescription(), keyword) || containsIgnoreCase(m.getTitle(), keyword);
    }

    private boolean containsIgnoreCase(String s, String keyword) {
        return s.toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT));
    }

    @Test
    void getMoviesByReleaseYear_movieIsFound() {
        var year = 2000;

        Set<MovieResponse> mResponses = service.getMoviesByReleaseYear(year);
        assertEquals(1, mResponses.size());
    }
}