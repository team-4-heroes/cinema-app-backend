package kea.dat3.api;

import kea.dat3.entities.Reservation;
import kea.dat3.entities.ReservedSeat;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @PutMapping("/add-seat")
    Reservation addSeatToReservation(Reservation reservation, ReservedSeat seat) {
        Set<ReservedSeat> reservations = reservation.getReservedSeats();
        reservations.add(seat);
        return reservation;
    }
}
