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

    @ManyToOne
    Screening screening;

    @ManyToOne
    Reservation reservation;

    public ReservedSeat(Seat seat) {
        this.seat = seat;
    }

    public ReservedSeat(Screening screening, Seat seat, Reservation reservation) {
        this.screening = screening;
        this.reservation = reservation;
        this.seat = seat;
    }

    @Override
    public String toString() {
        return "ReservedSeat{" +
                "id=" + id +
                ", seat=" + seat +
                ", screening=" + screening +
                ", reservation=" + reservation +
                '}';
    }
}
