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

    char rowLetter;
    int number;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public Seat(char row, int number, Room room) {
        this.room = room;

    String rowLetter;

    int number;

    SeatType seatType;

    public Seat(String row, int number) {
        this.rowLetter = row;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", reservedSeat=" + reservedSeat +
                ", rowLetter=" + rowLetter +
                ", number=" + number +
                ", room=" + room +
                '}';
    }

    public Seat(String rowLetter, int number, SeatType seatType) {
        this.rowLetter = rowLetter;
        this.number = number;
        this.seatType = seatType;
    }

    //roomId
}
