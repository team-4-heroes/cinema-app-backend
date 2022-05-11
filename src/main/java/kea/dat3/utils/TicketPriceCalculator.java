package kea.dat3.utils;

import kea.dat3.entities.ReservedSeat;
import kea.dat3.entities.Screening;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class TicketPriceCalculator {

    public static double calculateTotalPrice(Screening screening, Set<ReservedSeat> seats) {
        var baseMoviePrice = screening.getMovie().getBasePrice();
        var total = seats.stream().map(s -> s.getSeat().getSeatType().getPriceMultiplier() * baseMoviePrice).reduce(0d, Double::sum);

        return total;
    }
}