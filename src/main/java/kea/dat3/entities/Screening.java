package kea.dat3.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private LocalDateTime startTime;

    @ManyToOne
    private Movie movie;

    @OneToMany(mappedBy = "screening")
    Set<Reservation> reservation;

    @ManyToOne
    private Room room;

    @OneToMany
    private Set<ReservedSeat> screeningSeats = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;

    public Screening(LocalDateTime startTime, Room room, Movie movie) {
        this.startTime = startTime;
        this.room = room;
        this.movie = movie;
        //Get room's list of seats
        this.screeningSeats = buildScreeningSeats(room.getSeats());
    }

    public Set<ReservedSeat> buildScreeningSeats(Set<Seat> seats) {
        //Create seats for the screening
        Set<ReservedSeat> screeningSeats = new HashSet<>();
        for (Seat s : seats) {
            ReservedSeat rs = new ReservedSeat(s);
            screeningSeats.add(rs);
        }
        return screeningSeats;
    }

    @Override
    public String toString() {
        return "Screening{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", movie=" + movie +
                ", room=" + room +
                ", updated=" + updated +
                '}';
    }
}
