package kea.dat3.repositories;

import kea.dat3.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> getMovieByDescriptionContainingAndAndTitleContaining(String keyword);
}
