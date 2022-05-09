package kea.dat3.services;

import kea.dat3.dto.ReservationRequest;
import kea.dat3.dto.ReservationResponse;
import kea.dat3.entities.Reservation;
import kea.dat3.entities.ReservedSeat;
import kea.dat3.entities.Seat;
import kea.dat3.repositories.ReservationRepository;
import kea.dat3.repositories.ReservedSeatRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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

    //TODO Metode til at finde sæder der ikke er ledige i et ReservationRequest
    public Set<ReservedSeat> returnUnavailableSeatsSet(ReservationRequest body) {
        Set<ReservedSeat> desiredSeats = body.getDesiredSeats();
        Set<ReservedSeat> alreadyReserved = reservedSeatRepository.getReservedSeats(body.getScreening().getId());
        Set<ReservedSeat> notAvailable = new HashSet<>();

        alreadyReserved.forEach(reservedSeat -> {
            if (desiredSeats.contains(reservedSeat)) {
                notAvailable.add(reservedSeat);
            }
        });
        return notAvailable;
    }

    public ReservationResponse createReservation(ReservationRequest body) {
        Reservation reservation = new Reservation(body);

        if (returnUnavailableSeatsSet(body).size() == 0) {
            reservationRepository.save(reservation);
        } else {
            //TODO: throw Error seatsUnavailable

        }

        return new ReservationResponse(reservation);
    }
}
