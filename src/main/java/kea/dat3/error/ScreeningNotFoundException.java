package kea.dat3.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ScreeningNotFoundException extends ResponseStatusException {

    private static final String message = "Screening with id '%s' not found";

    public ScreeningNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, String.format(message, id));
    }

}
