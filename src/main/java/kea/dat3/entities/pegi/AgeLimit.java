package kea.dat3.entities.pegi;

import lombok.Getter;

@Getter
public enum AgeLimit {

    PEGI_3("PEGI 3"),
    PEGI_7("PEGI 7"),
    PEGI_12("PEGI 12"),
    PEGI_16("PEGI 16"),
    PEGI_18("PEGI 18");

    private String name;

    AgeLimit(String name) {
        this.name = name;
    }
}
