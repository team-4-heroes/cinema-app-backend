package kea.dat3.entities;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class Seat {
    @Id
    long id;

    String row;
    int number;

    public Seat(String row, int number) {
        this.row = row;
        this.number = number;
    }

    //roomId
}
