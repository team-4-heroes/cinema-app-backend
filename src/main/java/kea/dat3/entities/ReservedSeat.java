package kea.dat3.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @OneToOne(mappedBy = "reservedSeat")
    Seat seat;

    @JsonIgnore
    @ManyToOne
    Screening screening;

    @ManyToOne
    Reservation reservation;

    public ReservedSeat(Screening screening, Seat seat) {
        this.screening = screening;
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
