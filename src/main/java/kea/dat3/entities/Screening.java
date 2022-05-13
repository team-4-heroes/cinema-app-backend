package kea.dat3.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import kea.dat3.dto.ScreeningRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
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
    // "2015-03-25T12:00:00Z"
    //@JsonFormat(pattern = "dd-MM HH:mm",shape = JsonFormat.Shape.STRING)
    //@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ",shape = JsonFormat.Shape.STRING)
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

    public Screening(ScreeningRequest screeningReq) {
        this.room = screeningReq.getRoom();
        this.startTime = screeningReq.getStartTime();
        this.movie = screeningReq.getMovie();
        this.screeningSeats = buildScreeningSeats(screeningReq.getRoom().getSeats());
    }

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
}
