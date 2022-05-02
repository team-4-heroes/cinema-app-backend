package kea.dat3.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ReservedSeat {
    @Id
    long id;

    @ManyToOne
    private Reservation reservation;

    public Reservation getReservation() {
        return reservation;
    }
}
