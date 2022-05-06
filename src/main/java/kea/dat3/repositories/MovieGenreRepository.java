package kea.dat3.repositories;

import kea.dat3.entities.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.stream.Collectors;

@Repository
public interface MovieGenreRepository extends JpaRepository<MovieGenre, Long> {

    default Map<Long, MovieGenre> findAllMap() {
        return findAll().stream().collect(Collectors.toMap(MovieGenre::getId, v -> v));
    }
}
