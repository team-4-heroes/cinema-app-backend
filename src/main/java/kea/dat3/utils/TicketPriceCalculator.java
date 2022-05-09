package kea.dat3.utils;

import kea.dat3.entities.ReservedSeat;
import kea.dat3.entities.Screening;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class TicketPriceCalculator {

    public static double calculateTotalPrice(Screening screening, Set<ReservedSeat> seats) {
        var basePrice = screening.getMovie().getBasePrice();
        var seatTypes = seats.stream().map(s -> s.getSeat().getSeatType()).collect(Collectors.toList());


        return 0;
    }

}
