package kea.dat3.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import kea.dat3.entities.Movie;
import kea.dat3.entities.Room;
import kea.dat3.entities.Screening;
import kea.dat3.repositories.*;
import kea.dat3.services.MovieService;
import kea.dat3.services.ReservationService;
import kea.dat3.services.ScreeningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ReservationControllerTest {
    @Autowired
    MockMvc mockMvc;

    //Repositories
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ScreeningRepository screeningRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    ReservedSeatRepository reservedSeatRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    SeatRepository seatRepository;

    //Services
    @Autowired
    ScreeningService screeningService;
    @Autowired
    ReservationService reservationService;
    @Autowired
    MovieService movieService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setupReservationControllerTest() {
        //Test reservedSeat
        movieRepository.deleteAll();
        roomRepository.deleteAll();
        Room r = new Room("Bob", 10);
        Movie m = new Movie("Titel", "Beskrivelse", 1999, 120, 100);
        Screening sc = new Screening(LocalDateTime.now(), r, m);
        movieRepository.save(m);
        roomRepository.save(r);

        System.out.println("Room.getSeats(): " + r.getSeats());
        System.out.println(sc.getScreeningSeats());
        screeningRepository.save(sc);

    }

    //Test make reservation with all seats available (expected = ok)

    public void testMakeReservationExpectOk() {
    }

    //TODO: Test make reservation with unavailable seats (expected = error)
    public void testMakeReservationExpectFail() {
    }

}
