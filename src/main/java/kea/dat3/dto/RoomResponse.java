package kea.dat3.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kea.dat3.entities.Room;
import kea.dat3.entities.Screening;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RoomResponse {

    private long id;
    private String name;
    private Set<Screening> screenings;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss",shape = JsonFormat.Shape.STRING)
    private LocalDateTime created;
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss",shape = JsonFormat.Shape.STRING)
    private LocalDateTime updated;

    public RoomResponse(Room room, boolean includeScreenings) {
        this.id = room.getId();
        this.name = room.getName();
        if (includeScreenings) this.screenings = room.getScreenings();
    }
}
