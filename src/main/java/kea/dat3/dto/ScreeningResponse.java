package kea.dat3.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kea.dat3.entities.Movie;
import kea.dat3.entities.Room;
import kea.dat3.entities.Screening;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ScreeningResponse {

    private Movie movie;

    private String roomName;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss",shape = JsonFormat.Shape.STRING)
    private LocalDateTime startTime;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss",shape = JsonFormat.Shape.STRING)
    private LocalDateTime created;
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss",shape = JsonFormat.Shape.STRING)
    private LocalDateTime updated;

    public ScreeningResponse(Screening screening) {
        this.startTime = screening.getStartTime();
        this.roomName = screening.getRoom().getName();
        this.created = screening.getCreated();
        this.updated = screening.getUpdated();
        this.movie = screening.getMovie();
    }
}
