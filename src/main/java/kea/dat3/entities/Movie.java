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
import javax.validation.constraints.Size;
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

    private String title;

    private String description;

    @Size(max = 4)
    private int releaseYear;

    @Size(max = 3)
    private int length; // in minutes

    private double basePrice;

    AgeLimit ageLimit;

    @JsonIgnore
    @OneToMany(mappedBy = "movie") //, fetch = FetchType.EAGER)
    Set<Screening> screenings = new HashSet<>();

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
        this.length = body.getLength();
        this.basePrice = body.getBasePrice();
        this.ageLimit = body.getAgeLimit();
        this.screenings = body.getScreenings();
        this.genres = body.getGenres();
    }

    public Movie(String title, String description, int releaseYear, int length, double basePrice) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.length = length;
        this.basePrice = basePrice;
    }
}
