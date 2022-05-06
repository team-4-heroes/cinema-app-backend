package kea.dat3.services;

import kea.dat3.dto.ReservationRequest;
import kea.dat3.dto.ReservationResponse;
import kea.dat3.entities.Reservation;
import kea.dat3.entities.ReservedSeat;
import kea.dat3.entities.Seat;
import kea.dat3.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ReservationService {
    ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationResponse createReservation(ReservationRequest body) {
        //check if seat is taken
        //get list of seasts from request body
        Reservation reservation = new Reservation(body);
        reservationRepository.save(reservation);

            //Pick each seat from list ->
            //s.checkIfAvailable; possible
            //check if seat is available in Screening HashSet<> -> if not in set, is reserved
            //create reservedSeat if not ->

            //Put each reservedSeat into a collective reservation

            //add to db


        //Put reservation i db

        return new ReservationResponse(reservation);
    }
}
