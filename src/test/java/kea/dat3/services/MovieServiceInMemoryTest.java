package kea.dat3.services;

import kea.dat3.dto.MovieResponse;
import kea.dat3.entities.Movie;
import kea.dat3.entities.MovieFactory;
import kea.dat3.entities.pegi.AgeLimit;
import kea.dat3.repositories.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
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

    @BeforeEach
    void setup() {
        service = new MovieService(repository);
    }

    @Test
    void getMoviesByKeyword() {
        var keyword = "thor";

        var CUT_1 = MovieFactory.create_titleAndDescriptionOnly("xxx", "123thorIsBadass456");
        var CUT_2= MovieFactory.create_titleAndDescriptionOnly("Thor", "xxx");
        var CUT_3= MovieFactory.create_titleAndDescriptionOnly("xxx", "xxx");

        repository.save(CUT_1);
        repository.save(CUT_2);
        repository.save(CUT_3);

        Set<MovieResponse> mResponses = service.getMoviesByKeyword(keyword);
        assertEquals(2, mResponses.size());
        assertTrue(mResponses.stream().allMatch(m -> verifyContainsKeyword(keyword, m)));
    }

    @Test
    void getMoviesByReleaseYear_movieIsFound() {
        var year = 2000;

        var CUT_1 = MovieFactory.create_releaseYearOnly(year);
        repository.save(CUT_1);

        Set<MovieResponse> mResponses = service.getMoviesByReleaseYear(year);
        assertEquals(1, mResponses.size());
    }

    @Test
    void getMoviesByReleaseYear_movieIsNotFound() {
        var yearNotSaved = 2000;
        var yearSaved = 2022;

        var CUT_1 = MovieFactory.create_releaseYearOnly(yearSaved);
        repository.save(CUT_1);

        assertTrue(service.getMoviesByReleaseYear(yearNotSaved).isEmpty());
    }

    @Test
    void addMovie_releaseYearOutOfBounds() {
        var lowYear = 10;
        var highYear = 1000000;
        var yearZero = 0;

        var CUT_1 = MovieFactory.create_releaseYearOnly(lowYear); // TODO: Shouldn't error already occur when Movie is created with incorrect value?
        var CUT_2 = MovieFactory.create_releaseYearOnly(highYear);
        var CUT_3 = MovieFactory.create_releaseYearOnly(yearZero);

        Assertions.assertThrows(ConstraintViolationException.class, () -> repository.save(CUT_1));
        Assertions.assertThrows(ConstraintViolationException.class, () -> repository.save(CUT_2));
        Assertions.assertThrows(ConstraintViolationException.class, () -> repository.save(CUT_3));
    }

    @Test
    void addMovie_lengthInMinutesOutOfBounds() {
        var highLength = 1000;
        var zeroLength = 0;
        var CUT_1 = MovieFactory.create_lengthOnly(highLength);
        var CUT_2 = MovieFactory.create_lengthOnly(zeroLength);

        Assertions.assertThrows(ConstraintViolationException.class, () -> repository.save(CUT_1));
        Assertions.assertThrows(ConstraintViolationException.class, () -> repository.save(CUT_2));
    }

    private boolean verifyContainsKeyword(String keyword, MovieResponse m) {
        return containsIgnoreCase(m.getDescription(), keyword) || containsIgnoreCase(m.getTitle(), keyword);
    }

    private boolean containsIgnoreCase(String s, String keyword) {
        return s.toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT));
    }
}