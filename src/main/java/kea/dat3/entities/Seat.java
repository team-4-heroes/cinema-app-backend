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

   // @OneToOne
   // ReservedSeat reservedSeat;

    char rowLetter;

    int number;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    private SeatType seatType;

    public Seat(char row, int number, Room room) {
        this.room = room;
    }

    public Seat(char row, int number) {
        this.rowLetter = row;
        this.number = number;
    }

    public Seat(char rowLetter, int number, SeatType seatType, Room room) {
        this.rowLetter = rowLetter;
        this.number = number;
        this.seatType = seatType;
        this.room = room;
    }

}
