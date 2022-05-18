package kea.dat3.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kea.dat3.dto.RoomRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "room", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Seat> seats = new ArrayList<>();

    @Column(unique = true, length = 50, nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Screening> screenings = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;

    public Room(String name) {
        this.name = name;
    }

    public Room(String name, int seatsPerRow) {
        this.name = name;
        this.seatsPerRow = seatsPerRow;
        this.seats = buildSeats(this);
    }

    public Room(RoomRequest roomRequest) {
        this.name = roomRequest.getName();
    }

    public void addScreening(Screening screening){
        screenings.add(screening);
    }

    //Take number seatsPerRow
    private int seatsPerRow = 10;
    //Take char[] rowLetter
    private char rowLetters[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

    public static List<Seat> buildSeats(Room room) {
        //TODO Create seats for the room
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= room.getSeatsPerRow(); i++) {
            for (int j = 0; j < room.getRowLetters().length; j++) {
                Seat s = new Seat();
                s.setRowLetter(room.getRowLetters()[j]);
                s.setNumber(i);
                s.setRoom(room);
                seats.add(s);
            }
        }
        return seats;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
