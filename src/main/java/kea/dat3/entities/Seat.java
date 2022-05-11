package kea.dat3.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @OneToOne
    ReservedSeat reservedSeat;

    String rowLetter;

    int number;

    SeatType seatType;

    public Seat(String row, int number) {
        this.rowLetter = row;
        this.number = number;
    }

    public Seat(String rowLetter, int number, SeatType seatType) {
        this.rowLetter = rowLetter;
        this.number = number;
        this.seatType = seatType;
    }

    //roomId
}
