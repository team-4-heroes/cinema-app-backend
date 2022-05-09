package kea.dat3.api;

import kea.dat3.dto.ReservationRequest;
import kea.dat3.dto.ReservationResponse;
import kea.dat3.entities.Reservation;
import kea.dat3.entities.ReservedSeat;
import kea.dat3.repositories.ReservedSeatRepository;
import kea.dat3.services.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    ReservationService reservationService;

    public ReservationController (ReservationService reservationService, ReservedSeatRepository reservedSeatRepository) {
        this.reservationService = reservationService;
    }

    //Create
    @PostMapping
    public ReservationResponse addReservation(@RequestBody ReservationRequest body) {
        return reservationService.createReservation(body);
    }

    //Read
    @GetMapping("/{screeningId}")
    public Set<ReservedSeat> seeAvailableSeats(@PathVariable Long screeningId) {
        return reservationService.getSeatsWithNoReservations(screeningId);
    }

    @GetMapping("/howManySeatsAvailableForScreening/{screeningId}")
    public int numberOfAvailableSeats(@PathVariable Long screeningId) {
        return reservationService.getSeatsWithNoReservations(screeningId).size();
    }

    //Update
    @PutMapping("/{reservation.id}/add-seat")
    Reservation addSeatToReservation(@PathVariable Long id,  Reservation reservation, ReservedSeat seat) {
        id = reservation.getId();
        Set<ReservedSeat> reservations = reservation.getReservedSeats();
        reservations.add(seat);
        reservation.setReservedSeats(reservations);
        return reservation;
    }

    //Delete


}
