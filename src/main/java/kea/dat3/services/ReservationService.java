package kea.dat3.services;

import kea.dat3.dto.ReservationRequest;
import kea.dat3.dto.ReservationResponse;
import kea.dat3.entities.Reservation;
import kea.dat3.entities.ReservedSeat;
import kea.dat3.entities.Screening;
import kea.dat3.error.PersonNotFoundException;
import kea.dat3.error.ScreeningNotFoundException;
import kea.dat3.error.SeatOccupiedException;
import kea.dat3.repositories.PersonRepository;
import kea.dat3.repositories.ReservationRepository;
import kea.dat3.repositories.ReservedSeatRepository;
import kea.dat3.repositories.ScreeningRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Set;

import static java.lang.Long.valueOf;

@Service
public class ReservationService {
    ReservationRepository reservationRepository;
    ReservedSeatRepository reservedSeatRepository;
    ScreeningRepository screeningRepository;
    PersonRepository personRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservedSeatRepository reservedSeatRepository, ScreeningRepository screeningRepository, PersonRepository personRepository) {
        this.reservationRepository = reservationRepository;
        this.reservedSeatRepository = reservedSeatRepository;
        this.screeningRepository = screeningRepository;
        this.personRepository = personRepository;
    }

    public Set<ReservedSeat> getSeatsWithNoReservations(Long screeningId) {
        return reservedSeatRepository.getSeatsWithNoReservations(screeningId);
    }

    public Set<ReservedSeat> getReservedSeats(Long screeningId) {
        return reservedSeatRepository.getReservedSeats(screeningId);
    }

    public ReservationResponse createReservationAlternativeMethod(ReservationRequest body) {
        Reservation res = new Reservation(
                screeningRepository.findById(body.getScreeningId()).orElseThrow(()-> new ScreeningNotFoundException(body.getScreeningId())),
                new ArrayList<ReservedSeat>(),
                personRepository.findById(body.getPersonId()).get());
            for (Integer n : body.getDesiredSeats()) {
                ReservedSeat rs = reservedSeatRepository.findById(valueOf(n)).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
                if (rs.getReservation() !=  null) {
                throw new SeatOccupiedException(valueOf(n));
                }
            rs.setReservation(res);
            res.getReservedSeats().add(rs);
            }
        reservedSeatRepository.saveAll(res.getReservedSeats());
        reservationRepository.save(res);
        return new ReservationResponse(res);
    }
}
