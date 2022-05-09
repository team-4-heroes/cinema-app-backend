package kea.dat3.repositories;

import kea.dat3.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    List<Actor> findActorByFirstNameAndLastName(String firstName, String lastName);
}
