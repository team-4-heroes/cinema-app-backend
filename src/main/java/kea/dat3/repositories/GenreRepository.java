package kea.dat3.repositories;

import kea.dat3.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.stream.Collectors;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    default Map<Long, Genre> findAllMap() {
        return findAll().stream().collect(Collectors.toMap(Genre::getId, v -> v));
    }
}
