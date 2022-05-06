package kea.dat3.dto;

import kea.dat3.entities.Person;
import kea.dat3.entities.ReservedSeat;
import kea.dat3.entities.Seat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {
    Person customer;
    //Screening screening;
    Set<ReservedSeat> desiredSeats;
}
