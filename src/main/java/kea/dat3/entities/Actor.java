package kea.dat3.entities;

import kea.dat3.dto.ActorRequest;
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
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private LocalDateTime birthDate;

    @ManyToMany(mappedBy = "actors")
    private Set<Movie> movies = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;

    Actor(String firstName, String lastName, LocalDateTime birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    Actor(ActorRequest body) {
        this.firstName = body.getFirstName();
        this.lastName = body.getLastName();
        this.birthDate = getBirthDate();
    }
}
