package kea.dat3.repositories;

import kea.dat3.entities.Actor;
import kea.dat3.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByTitleContainingOrDescriptionContainingAllIgnoreCase(String titleKeyword, String descriptionKeyword);

    List<Movie> findByReleaseYear(int releaseYear);

    List<Movie> findByActorsEquals(Actor actor);
}
