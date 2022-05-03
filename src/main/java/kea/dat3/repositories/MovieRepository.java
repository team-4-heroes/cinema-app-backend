package kea.dat3.repositories;

import kea.dat3.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    // TODO: Nice to have - search for movie by keyword (not a requirement)
    // Optional<Movie> getMovieByDescriptionContainingAndAndTitleContaining(String keyword);
}
