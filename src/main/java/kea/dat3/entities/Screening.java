package kea.dat3.entities;

import kea.dat3.dto.ScreeningRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime startTime;

    @ManyToOne
    private Movie movie;

    @OneToMany(mappedBy = "screening")
    Set<Reservation> reservation;

    @ManyToOne
    private Room room;

    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;

    public Screening(ScreeningRequest screeningReq) {
        this.room = screeningReq.getRoom();
        this.startTime = screeningReq.getStartTime();
        this.movie = screeningReq.getMovie();
    }

    public Screening(LocalDateTime startTime, Room room, Movie movie) {
        this.startTime = startTime;
        this.room = room;
        this.movie = movie;
    }
}
