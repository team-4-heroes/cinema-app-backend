package kea.dat3.entities;

import kea.dat3.entities.pegi.AgeLimit;
import java.time.LocalDateTime;
import java.util.HashSet;

public class MovieFactory {

    private static final String standardTitleOrDescription = "xxx";
    private static final int standardLength = 120;
    private static final int standardPrice = 150;
    private static final int standardReleaseYear = 150;

    public static Movie create(Long id, String title, String description, int releaseYear, int lengthInMinutes, double basePrice, AgeLimit ageLimit) {
        return new Movie(id, title, description, releaseYear, lengthInMinutes, basePrice, ageLimit, new HashSet<>(), new HashSet<>(), new HashSet<>(), LocalDateTime.now(), LocalDateTime.now());
    }

    public static Movie create_titleAndDescriptionOnly(String title, String description) {
        return new Movie(title, description, 2000, standardLength, standardPrice);
    }

    public static Movie create_releaseYearOnly(int releaseYear) {
        return new Movie(standardTitleOrDescription, standardTitleOrDescription, releaseYear, standardLength, standardPrice);
    }

    public static Movie create_lengthOnly(int length) {
        return new Movie(standardTitleOrDescription, standardTitleOrDescription, standardReleaseYear, length, standardPrice);
    }
}