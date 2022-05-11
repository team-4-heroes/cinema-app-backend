package kea.dat3.services;

import kea.dat3.dto.ReservationRequest;
import kea.dat3.dto.ReservationResponse;
import kea.dat3.entities.Reservation;
import kea.dat3.entities.ReservedSeat;
import kea.dat3.error.SeatOccupiedException;
import kea.dat3.repositories.ReservationRepository;
import kea.dat3.repositories.ReservedSeatRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

import static java.lang.Long.valueOf;

@Service
public class ReservationService {
    ReservationRepository reservationRepository;
    ReservedSeatRepository reservedSeatRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservedSeatRepository reservedSeatRepository) {
        this.reservationRepository = reservationRepository;
        this.reservedSeatRepository = reservedSeatRepository;
    }

    public Set<ReservedSeat> getSeatsWithNoReservations(Long screeningId) {
        return reservedSeatRepository.getSeatsWithNoReservations(screeningId);
    }

    public Set<ReservedSeat> getReservedSeats(Long screeningId) {
        return reservedSeatRepository.getReservedSeats(screeningId);
    }

    public ReservationResponse createReservationAlternativeMethod(ReservationRequest body) {
        Reservation res = new Reservation();
            for (Integer n : body.getDesiredSeats()) {
                ReservedSeat rs = reservedSeatRepository.findById(valueOf(n)).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
                //Add rs to list ls if no reservation
                if (rs.getReservation() !=  null) {
                throw new SeatOccupiedException(valueOf(n));
                }
            res.getReservedSeats().add(rs);
        }
        //Check if list desiredSeats.size == ls.size
        reservationRepository.save(res);
        return new ReservationResponse(res);
    }
}
