package kea.dat3.services;

import kea.dat3.dto.MovieDetailResponse;
import kea.dat3.entities.builders.MovieFactory;
import kea.dat3.repositories.ActorRepository;
import kea.dat3.repositories.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class MovieServiceInMemoryTest {

    MovieService service;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ActorRepository actorRepository;

    @BeforeEach
    void setup() {
        service = new MovieService(movieRepository, actorRepository);
    }

    @Test
    void getMoviesByKeyword() {
        var keyword = "thor";

        var m_1 = MovieFactory.create_titleAndDescriptionOnly("xxx", "123thorIsBadass456");
        var m_2 = MovieFactory.create_titleAndDescriptionOnly("Thor", "xxx");
        var m_3 = MovieFactory.create_titleAndDescriptionOnly("xxx", "xxx");

        movieRepository.save(m_1);
        movieRepository.save(m_2);
        movieRepository.save(m_3);

        Set<MovieDetailResponse> mResponses = service.getMoviesByKeyword(keyword);
        assertEquals(2, mResponses.size());
        assertTrue(mResponses.stream().allMatch(m -> verifyContainsKeyword(keyword, m)));
    }

    @Test
    void getMoviesByReleaseYear_movieIsFound() {
        var year = 2000;

        var CUT_1 = MovieFactory.create_releaseYearOnly(year);
        movieRepository.save(CUT_1);

        Set<MovieDetailResponse> mResponses = service.getMoviesByReleaseYear(year);
        assertEquals(1, mResponses.size());
    }

    @Test
    void getMoviesByReleaseYear_movieIsNotFound() {
        var yearNotSaved = 2000;
        var yearSaved = 2022;

        var CUT_1 = MovieFactory.create_releaseYearOnly(yearSaved);
        movieRepository.save(CUT_1);

        assertTrue(service.getMoviesByReleaseYear(yearNotSaved).isEmpty());
    }

    @Test
    void addMovie_releaseYearOutOfBounds() {
        var lowYear = 10;
        var highYear = 1000000;
        var yearZero = 0;

        var CUT_1 = MovieFactory.create_releaseYearOnly(lowYear);
        var CUT_2 = MovieFactory.create_releaseYearOnly(highYear);
        var CUT_3 = MovieFactory.create_releaseYearOnly(yearZero);

        Assertions.assertThrows(ConstraintViolationException.class, () -> movieRepository.save(CUT_1));
        Assertions.assertThrows(ConstraintViolationException.class, () -> movieRepository.save(CUT_2));
        Assertions.assertThrows(ConstraintViolationException.class, () -> movieRepository.save(CUT_3));
    }

    @Test
    void addMovie_lengthInMinutesOutOfBounds() {
        var highLength = 1000;
        var zeroLength = 0;
        var CUT_1 = MovieFactory.create_lengthOnly(highLength);
        var CUT_2 = MovieFactory.create_lengthOnly(zeroLength);

        Assertions.assertThrows(ConstraintViolationException.class, () -> movieRepository.save(CUT_1));
        Assertions.assertThrows(ConstraintViolationException.class, () -> movieRepository.save(CUT_2));
    }

    private boolean verifyContainsKeyword(String keyword, MovieDetailResponse m) {
        return containsIgnoreCase(m.getDescription(), keyword) || containsIgnoreCase(m.getTitle(), keyword);
    }

    private boolean containsIgnoreCase(String s, String keyword) {
        return s.toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT));
    }
}