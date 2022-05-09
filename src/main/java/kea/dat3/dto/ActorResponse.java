package kea.dat3.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kea.dat3.entities.Actor;
import kea.dat3.entities.Movie;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActorResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private LocalDateTime birthDate;

    private Set<Movie> movies;

    private LocalDateTime created;

    private LocalDateTime updated;

    public ActorResponse(Actor actor) {
        this.id = actor.getId();
        this.firstName = actor.getFirstName();
        this.lastName = actor.getLastName();
        this.birthDate = actor.getBirthDate();
        this.movies = actor.getMovies();
        this.created = actor.getCreated();
        this.updated = actor.getUpdated();
    }

    public static Set<ActorResponse> getActorsFromEntities(List<Actor> actors) {
        return actors.stream().map(actor-> new ActorResponse(actor)).collect(Collectors.toSet());
    }
}
