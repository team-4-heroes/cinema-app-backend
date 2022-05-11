package kea.dat3.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RoomOccupiedException extends ResponseStatusException {

    private static final String message = "Room with id '%s' occupied";

    public RoomOccupiedException(Long id) {
        super(HttpStatus.CONFLICT, String.format(message, id));
    }

}
