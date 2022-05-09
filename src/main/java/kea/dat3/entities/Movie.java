package kea.dat3.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kea.dat3.dto.MovieRequest;
import kea.dat3.entities.pegi.AgeLimit;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    //@Size(min = 1900, max = 3000) TODO: How to define these values, instead of min and max digits?
    private int releaseYear;

    //@Size(min = 1, max = 900)
    private int lengthInMinutes;

    private double basePrice;

    private AgeLimit ageLimit;

    @JsonIgnore
    @ManyToMany(mappedBy = "movie")
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

    public Movie(String title, String description, int releaseYear, int length, double basePrice) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.lengthInMinutes = length;
        this.basePrice = basePrice;
    }
}
