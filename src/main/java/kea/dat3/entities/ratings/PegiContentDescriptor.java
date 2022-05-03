package kea.dat3.entities.ratings;

import lombok.Getter;

@Getter
public enum PegiContentDescriptor {


    VIOLENCE("VIOLENCE"),
    BAD_LANGUAGE("BAD LANGUAGE"),
    FEAR("FEAR"),
    GAMBLING("GAMBLING"),
    SEX("SEX"),
    DRUGS("DRUGS"),
    DISCRIMINATION("DISCRIMINATION"),
    IN_GAME_PURCHASES("IN GAME PURCHASES"); // Not relevant to movies, but is a part of the descriptor set

    private final String name;

    PegiContentDescriptor(String name) {
        this.name = name;
    }
}
