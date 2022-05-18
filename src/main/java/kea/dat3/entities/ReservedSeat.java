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

    // @OneToOne(mappedBy = "reservedSeat")
    // Seat seat;

    @JsonIgnore
    @ManyToOne
    Screening screening;

    @ManyToOne
    Reservation reservation;

    char rowLetter;
    int number;

    private SeatType seatType;

    public ReservedSeat(Screening screening, int number, char rowLetter) {
        this.screening = screening;
        this.number = number;
        this.rowLetter = rowLetter;
        // this.seat = s;
    }

    public ReservedSeat(Screening screening, Reservation reservation, int number, char rowLetter) {
        this.screening = screening;
        this.reservation = reservation;
        this.number = number;
        this.rowLetter = rowLetter;
    }

    @Override
    public String toString() {
        return "ReservedSeat{" +
                "id=" + id +
                ", screening=" + screening +
                ", reservation=" + reservation +
                '}';
    }
}
