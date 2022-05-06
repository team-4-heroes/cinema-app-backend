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


    public ReservationResponse createReservation(ReservationRequest body) {
        //check if seat is taken
        //get list of seasts from request body
        Reservation reservation = new Reservation(body);
        reservationRepository.save(reservation);

        Set<ReservedSeat> desiredSeats = body.getDesiredSeats();
        //Check if seat has reference to Screening
        Set<ReservedSeat> alreadyReserved = reservedSeatRepository.getReservedSeats(body.getScreening().getId());
        Set<ReservedSeat> notAvailable = new HashSet<>();
        alreadyReserved.stream().forEach(reservedSeat -> {
            if (desiredSeats.contains(reservedSeat)) {
                notAvailable.add(reservedSeat);
            }
        });

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
