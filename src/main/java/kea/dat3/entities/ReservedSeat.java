package kea.dat3.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ReservedSeat {
    @Id
    long id;

    @OneToOne(mappedBy = "reservedSeat")
    Seat seat;

    //@OneToOne
    //Screening screening;

    @ManyToOne
    Reservation reservation;

    public ReservedSeat(Seat seat, Reservation reservation) {
        this.reservation = reservation;
        //this.seat = seat;
    }
}
