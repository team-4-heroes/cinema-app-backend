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

    public Seat(String row, int number) {
        this.rowLetter = row;
        this.number = number;
    }

    //roomId
}
