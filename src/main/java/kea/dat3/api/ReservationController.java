package kea.dat3.api;

import kea.dat3.dto.ReservationRequest;
import kea.dat3.dto.ReservationResponse;
import kea.dat3.entities.Reservation;
import kea.dat3.entities.ReservedSeat;
import kea.dat3.services.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    ReservationService reservationService;

    public ReservationController (ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    //Create
    @PostMapping
    public ReservationResponse addReservation(@RequestBody ReservationRequest body) {
        return reservationService.createReservation(body);
    }

    //Read

    //Update

    //Delete

    @PutMapping("/{reservation.id}/add-seat")
    Reservation addSeatToReservation(@PathVariable Long id,  Reservation reservation, ReservedSeat seat) {
        id = reservation.getId();
        Set<ReservedSeat> reservations = reservation.getReservedSeats();
        reservations.add(seat);
        reservation.setReservedSeats(reservations);
        return reservation;
    }
}
