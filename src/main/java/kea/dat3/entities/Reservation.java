package kea.dat3.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "reservation")
    private Set<ReservedSeat> reservedSeats = new HashSet<>();

    //@ManyToOne
    //Screening screening;

    public Set<ReservedSeat> getReservedSeats() {
        return reservedSeats;
    }
}
