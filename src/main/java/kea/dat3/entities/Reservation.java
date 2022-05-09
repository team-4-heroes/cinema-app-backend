package kea.dat3.entities;

import kea.dat3.dto.ReservationRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    Screening screening;

    @OneToMany(mappedBy = "reservation")
    private Set<ReservedSeat> reservedSeats = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Person customer;

    // TODO: Add -> private double totalPrice = TicketPriceCalculator.calculateTotalPrice(screening, reservedSeats);

    public Reservation(Set<ReservedSeat> reservedSeats, Person customer) {
        this.reservedSeats = reservedSeats;
        this.customer = customer;
    }

    public Reservation(ReservationRequest body) {
        this.reservedSeats = body.getDesiredSeats();
        this.customer = body.getCustomer();
    }
}
