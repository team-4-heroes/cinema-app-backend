package kea.dat3.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kea.dat3.entities.Person;
import kea.dat3.entities.Reservation;
import kea.dat3.entities.ReservedSeat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationResponse {

    Person person;
    Set<ReservedSeat> reservedSeats;

    public ReservationResponse(Reservation reservation) {
        this.person = reservation.getCustomer();
        this.reservedSeats = reservation.getReservedSeats();
    }
}
