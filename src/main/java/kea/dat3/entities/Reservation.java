package kea.dat3.entities;

import kea.dat3.dto.ReservationRequest;
import kea.dat3.repositories.PersonRepository;
import kea.dat3.utils.TicketPriceCalculator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private List<ReservedSeat> reservedSeats = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Person customer;

    private double totalPrice;

    public Reservation(Screening screening, List<ReservedSeat> reservedSeats, Person customer) {
        this.screening = screening;
        this.reservedSeats = reservedSeats;
        this.customer = customer;
        this.totalPrice = TicketPriceCalculator.calculateTotalPrice(screening, (Set<ReservedSeat>) reservedSeats);
    }

    public Reservation(List<ReservedSeat> reservedSeats, Person customer) {
        this.reservedSeats = reservedSeats;
        this.customer = customer;
    }

}
