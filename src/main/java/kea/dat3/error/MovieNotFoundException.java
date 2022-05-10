package kea.dat3.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MovieNotFoundException extends ResponseStatusException {

    private static final String message = "Movie with id '%s' not found";

    public MovieNotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }

    public MovieNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, String.format(message, id));
    }

    public MovieNotFoundException(Long id, Throwable cause) {
        super(HttpStatus.NOT_FOUND, String.format(message, id), cause);
    }
}
