package kea.dat3.dto;

import kea.dat3.entities.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScreeningRequest {
    private LocalDateTime startTime;
    private Room room;
    // private Movie movie;
}