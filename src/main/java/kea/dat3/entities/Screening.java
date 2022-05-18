package kea.dat3.entities;

import kea.dat3.dto.ScreeningRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "screening", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<ReservedSeat> screeningSeats = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;

    public Screening(ScreeningRequest screeningReq) {
        this.room = screeningReq.getRoom();
        this.startTime = screeningReq.getStartTime();
        this.movie = screeningReq.getMovie();
        this.screeningSeats = buildScreeningSeats(screeningReq.getRoom());
    }

    public Screening(LocalDateTime startTime, Room room, Movie movie) {
        this.startTime = startTime;
        this.room = room;
        this.movie = movie;
        //Get room's list of seats
        this.screeningSeats = buildScreeningSeats(room);
    }

    public List<ReservedSeat> buildScreeningSeats(Room room) {
        //Create seats for the screening
        List<ReservedSeat> screeningSeats = new ArrayList<>();
        for (Seat s : room.getSeats()) {
            ReservedSeat rs = new ReservedSeat(this, s);
            screeningSeats.add(rs);
        }
        return screeningSeats;
    }
}
