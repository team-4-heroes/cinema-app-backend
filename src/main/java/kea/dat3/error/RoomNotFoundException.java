package kea.dat3.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RoomNotFoundException extends ResponseStatusException {

    private static final String message = "Room with id '%s' not found";

    public RoomNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, String.format(message, id));
    }

}
