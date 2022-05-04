package kea.dat3.entities;

import lombok.Getter;

@Getter
public enum SeatType {

    BACK_ROW(1.3),
    FRONT_SECTION(1),
    MIDDLE_SECTION(1.3),
    COUPLE_SEAT(2.3);

    private double priceMultiplier;

    SeatType(double priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }
}
