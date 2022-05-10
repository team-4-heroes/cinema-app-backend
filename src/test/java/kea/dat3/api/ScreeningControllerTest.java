package kea.dat3.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import kea.dat3.dto.ScreeningRequest;
import kea.dat3.entities.Movie;
import kea.dat3.entities.Room;
import kea.dat3.entities.Screening;
import kea.dat3.repositories.MovieRepository;
import kea.dat3.repositories.RoomRepository;
import kea.dat3.repositories.ScreeningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class ScreeningControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ScreeningRepository screeningRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setupScreeningControllerTest() {
        screeningRepository.deleteAll();
    }

    @Test
    void testAddScreeningWhenRoomOccupied() throws Exception {
        LocalDateTime startTime = LocalDateTime.of(2022, 10, 1, 10, 0);
        Room room = roomRepository.save(new Room("testRoom"));
        Movie movie = movieRepository.save(new Movie("film titel", "beskrivelse", 2000, 100, 100));
        Screening screening = new Screening(startTime, room, movie);
        screeningRepository.save(screening);
        ScreeningRequest screeningRequest = new ScreeningRequest(startTime, room, movie);
        System.out.println(objectMapper.writeValueAsString(screeningRequest));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/screenings")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(objectMapper.writeValueAsString(screeningRequest)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Room occupied"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("409"));
        // Verify that only one screening actually ended in the database
        assertEquals(1, screeningRepository.count());
    }

    /*
        @Test
        void testAddPerson() throws Exception {
            PersonRequest personRequest = new PersonRequest("first", "last", 88888888, null, "testUser", "test@mail.dk", "s3cr3etpass");
            mockMvc.perform(MockMvcRequestBuilders.post("/api/persons/register")
                            .contentType("application/json")
                            .accept("application/json")
                            .content(objectMapper.writeValueAsString(personRequest)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
            // Verify that it actually ended in the database
            assertEquals(1, personRepository.count());
        }
    */
    @Test
    void testScreeningNotFound() throws Exception {
        // request a nonexistent person and verify HTTP Status and error response
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/screenings/555")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Unavailable screening"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("404"));
    }

}
