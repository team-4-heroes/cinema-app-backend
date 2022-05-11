package kea.dat3.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kea.dat3.dto.RoomRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "room")
    private Set<Seat> seats = new HashSet<>();

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
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
    int seatsPerRow = 10;
    //Take char[] rowLetter
    char rowLetters[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

    public Set<Seat> buildSeats(Room room) {
        //TODO Create seats for the room
        Set<Seat> seats = new HashSet<>();
        for (int i = 1; i <= room.seatsPerRow; i++) {
            for (int j = 0; j < room.rowLetters.length; j++) {
                Seat s = new Seat(room.rowLetters[j], i, room);
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
