package kea.dat3.dto;

import kea.dat3.entities.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenreRequest {

private String name;

    private Set<Movie> movies = new HashSet<>();
}
