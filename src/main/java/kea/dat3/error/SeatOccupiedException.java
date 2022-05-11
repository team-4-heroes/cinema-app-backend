package kea.dat3.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SeatOccupiedException extends ResponseStatusException {

    private static final String message = "Seat with id '%s' occupied";

    public SeatOccupiedException(Long id) {
        super(HttpStatus.CONFLICT, String.format(message, id));
    }

}
