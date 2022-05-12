package kea.dat3.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kea.dat3.dto.MovieRequest;
import kea.dat3.entities.pegi.AgeLimit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    private String description;

    @Min(value = 1900)
    @Max(value = 3000)
    private int releaseYear;

    @Min(value = 1)
    @Max(value = 900)
    private int lengthInMinutes;

    private double basePrice;

    private AgeLimit ageLimit;

    @ManyToMany()
    @JoinTable(name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Actor> actors = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "movie") //, fetch = FetchType.EAGER)
    private Set<Screening> screenings = new HashSet<>();

    @ManyToMany()
    @JoinTable(name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;

    public Movie(MovieRequest body) {
        this.title = body.getTitle();
        this.description = body.getDescription();
        this.releaseYear = getReleaseYear();
        this.lengthInMinutes = body.getLength();
        this.basePrice = body.getBasePrice();
        this.ageLimit = body.getAgeLimit();
        this.screenings = body.getScreenings();
        this.genres = body.getGenres();
    }

    public Movie(String title, String description, int releaseYear, int lengthInMinutes, double basePrice) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.lengthInMinutes = lengthInMinutes;
        this.basePrice = basePrice;
    }
}