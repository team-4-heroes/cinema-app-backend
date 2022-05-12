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

    }

    //Test make reservation with all seats available (expected = ok)
    @Test
    public void testMakeReservationExpectOk() {
        Room r = new Room("Bob", 10);
        Movie m = new Movie("Titel", "Beskrivelse", 1999, 120, 100);
        Screening s = new Screening(LocalDateTime.now(), r, m);
        System.out.println("TEST");
        System.out.println(s.getScreeningSeats().toString());

    }

    //TODO: Test make reservation with unavailable seats (expected = error)
    public void testMakeReservationExpectFail() {
    }

}
