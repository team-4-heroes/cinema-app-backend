package kea.dat3.dto;

import kea.dat3.entities.Person;
import kea.dat3.entities.ReservedSeat;
import kea.dat3.entities.Screening;
import kea.dat3.entities.Seat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {
    Long personId;
    Long screeningId;
    List<Integer> desiredSeats;//TODO change to list of integers corresponding to reservedSeat.id
}
