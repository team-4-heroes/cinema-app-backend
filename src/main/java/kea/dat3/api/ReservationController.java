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

    public ReservationController (ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    //Create
    @PostMapping
    public ReservationResponse addReservation(@RequestBody ReservationRequest body) {
        return reservationService.createReservationAlternativeMethod(body);
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

    //Delete


}
