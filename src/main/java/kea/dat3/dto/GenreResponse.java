package kea.dat3.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kea.dat3.entities.Genre;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenreResponse {

    private Long id;

    private String name;

    private LocalDateTime created;

    private LocalDateTime updated;

    GenreResponse(Genre genre) {
        this.id = genre.getId();
        this.name = genre.getName();
        this.created = genre.getCreated();
        this.updated = genre.getUpdated();
    }

    public static Set<GenreResponse> getGenresFromEntities(List<Genre> genres) {
        return genres.stream().map(genre -> new GenreResponse(genre)).collect(Collectors.toSet());
    }

    public static Set<GenreResponse> getGenresFromEntities(Set<Genre> genres) {
        return genres.stream().map(genre -> new GenreResponse(genre)).collect(Collectors.toSet());
    }
}