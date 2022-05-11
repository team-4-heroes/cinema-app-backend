package kea.dat3.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PersonNotFoundException extends ResponseStatusException {

    private static final String message = "Person with id/username '%s' not found";

    public PersonNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, String.format(message, id));
    }

    public PersonNotFoundException(String username) {
        super(HttpStatus.NOT_FOUND, String.format(message, username));
    }

}
