package kea.dat3.dto;

import kea.dat3.entities.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActorRequest {

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private Set<Movie> movies;
}
