package kea.dat3.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import kea.dat3.entities.Reservation;
import kea.dat3.repositories.ReservationRepository;
import kea.dat3.services.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ReservationControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ReservationService reservationService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setupReservationControllerTest() {
        reservationRepository.deleteAll();
        Reservation res1 = new Reservation();
        Reservation res2 = new Reservation();
        Reservation res3 = new Reservation();

        reservationRepository.save(res1);
        reservationRepository.save(res2);
        reservationRepository.save(res3);

        System.out.println(reservationRepository);

    }


}
