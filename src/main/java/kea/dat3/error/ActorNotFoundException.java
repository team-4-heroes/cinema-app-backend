package kea.dat3.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ActorNotFoundException extends ResponseStatusException {

    private static final String message = "Actor with id '%s' not found";

    public ActorNotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }

    public ActorNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, String.format(message, id));
    }

    public ActorNotFoundException(Long id, Throwable cause) {
        super(HttpStatus.NOT_FOUND, String.format(message, id), cause);
    }
}