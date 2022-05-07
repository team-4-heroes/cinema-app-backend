package kea.dat3.services;

import kea.dat3.dto.MovieResponse;
import kea.dat3.entities.Movie;
import kea.dat3.entities.pegi.AgeLimit;
import kea.dat3.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MovieServiceInMemoryTest {

    MovieService service;

    @Autowired
    MovieRepository repository;

    Movie CUT_1 = new Movie(100L, "abc", "descr", 2000, 120, 125d, AgeLimit.PEGI_3, Collections.emptySet(), Collections.emptySet(), LocalDateTime.now(), LocalDateTime.now());
    Movie CUT_2 = new Movie(200L, "dfg", "bladescrbla", 2001, 120, 125d, AgeLimit.PEGI_7, Collections.emptySet(), Collections.emptySet(), LocalDateTime.now(), LocalDateTime.now());
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
        var keyword = "descr";

        Set<MovieResponse> mResponses = service.getMoviesByKeyword(keyword);
        assertEquals(2, mResponses.size());
        //1) go through results and check that description contains keyword
        //2) change test data so title contains keyword, update checks
        //3) change query to search both title and description

    }

    @Test
    void getMoviesByReleaseYear_movieIsFound() {
        var year = 2000;

        Set<MovieResponse> mResponses = service.getMoviesByReleaseYear(year);
        assertEquals(1, mResponses.size());
    }
}