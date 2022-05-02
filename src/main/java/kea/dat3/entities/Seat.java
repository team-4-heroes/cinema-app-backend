package kea.dat3.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
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
