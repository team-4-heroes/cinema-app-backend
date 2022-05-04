package kea.dat3.entities.movieAccessFactors;

import lombok.Getter;

@Getter
public enum AgeLimitDenmark implements AccessFactor {

    A("TILLADT FOR ALLE"),
    AGE_7("FRARÅDES BØRN UNDER 7 ÅR"),
    AGE_11("TILLADT FOR BØRN FRA 11 ÅR"),
    AGE_15("TILLADT FOR BØRN FRA 15 ÅR");

    private String description;

    AgeLimitDenmark(String description) {
        this.description = description;
    }
}
