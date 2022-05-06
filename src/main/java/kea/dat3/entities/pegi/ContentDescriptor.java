package kea.dat3.entities.pegi;

import lombok.Getter;

@Getter
public enum ContentDescriptor {


    VIOLENCE("VIOLENCE"),
    BAD_LANGUAGE("BAD LANGUAGE"),
    FEAR("FEAR"),
    GAMBLING("GAMBLING"),
    SEX("SEX"),
    DRUGS("DRUGS"),
    DISCRIMINATION("DISCRIMINATION");

    private final String name;

    ContentDescriptor(String name) {
        this.name = name;
    }
}
