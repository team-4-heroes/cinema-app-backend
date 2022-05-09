package kea.dat3.entities;

import kea.dat3.entities.pegi.AgeLimit;

import java.time.LocalDateTime;
import java.util.Collections;

public class MovieFactory {

    public static Movie create(Long id, String title, String description, int releaseYear, int lengthInMinutes, double basePrice, AgeLimit ageLimit) {
        return new Movie(id, title, description, releaseYear, lengthInMinutes, basePrice, ageLimit, Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), LocalDateTime.now(), LocalDateTime.now());
    }

    public static Movie create_releaseYearOnly(int releaseYear) {
        return new Movie("xxx", "xxx", releaseYear, 130, 120);
    }

    public static Movie create_lengthOnly(int length) {
        return new Movie("xxx", "xxx", 2000, length, 120);
    }
}

