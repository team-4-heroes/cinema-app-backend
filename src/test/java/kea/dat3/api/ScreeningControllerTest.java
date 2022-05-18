package kea.dat3.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import kea.dat3.dto.ScreeningRequest;
import kea.dat3.entities.Movie;
import kea.dat3.entities.Room;
import kea.dat3.entities.Screening;
import kea.dat3.entities.builders.MovieBuilder;
import kea.dat3.repositories.MovieRepository;
import kea.dat3.repositories.RoomRepository;
import kea.dat3.repositories.ScreeningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    Screening s;
    long nonExistentId = 999;

    @BeforeEach
    public void setupScreeningControllerTest() {
        screeningRepository.deleteAll();
        LocalDateTime startTime = LocalDateTime.of(2022, 10, 1, 10, 0);
        Room room = roomRepository.save(new Room("testRoom"));
        Movie movie = movieRepository.save(MovieBuilder.create("film titel", "beskrivelse", 2000)
                .addLengthInMinutes(200)
                .build());
        //new Movie("film titel", "beskrivelse", 2000, 100, 100));
        s = new Screening(startTime, room, movie);
        screeningRepository.save(s);
    }

    @ParameterizedTest
    @ValueSource(ints = {-130, 200}) // runs once with each value as the minutes parameter
    public void testAddScreening(int minutes) throws Exception {
        ScreeningRequest screeningReq = new ScreeningRequest(s.getStartTime().plusMinutes(minutes), s.getRoom(), s.getMovie());
        // Check that server answer with msg and statusCode
        mockMvc.perform(MockMvcRequestBuilders.post("/api/screenings")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(objectMapper.writeValueAsString(screeningReq)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.movie").exists());
        // Verify at least one of the screenings ended in the right place
        assertTrue(screeningRepository.count() > 1);
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, 129}) // runs once with each value as the minutes parameter
    public void testAddScreeningWhenRoomOccupied(int minutes) throws Exception {
        ScreeningRequest screeningReq = new ScreeningRequest(s.getStartTime().plusMinutes(minutes), s.getRoom(), s.getMovie());
        // Check that server answer with msg and statusCode
        addScreeningFail(screeningReq, "Room with id '" + s.getRoom().getId() + "' occupied", "409");
        // Verify only one screening actually ended in the database
        assertEquals(1, screeningRepository.count());
    }

    @Test
    public void testAddScreeningWhenRoomNonexistent() throws Exception {
        Room room = new Room("notSavedRoom");
        ScreeningRequest screeningReq = new ScreeningRequest(s.getStartTime().plusMinutes(200), room, s.getMovie());
        // Check that server answer with msg and statusCode
        addScreeningFail(screeningReq, "Room with id 'null' not found", "404");
        // Verify only one screening actually ended in the database
        assertEquals(1, screeningRepository.count());
    }

    @Test
    public void testScreeningNotFound() throws Exception {
        // request a nonexistent screening and verify HTTP Status and error response
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/screenings/" + nonExistentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Screening with id '" + nonExistentId + "' not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("404"));
    }

    @Test
    public void testDeleteNonexistentScreening() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/screenings/" + nonExistentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Screening with id '" + nonExistentId + "' not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("404"));
    }

    private void addScreeningFail(ScreeningRequest screeningRequest, String msg, String statusCode) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/screenings")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(objectMapper.writeValueAsString(screeningRequest)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(msg))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(statusCode));
    }
    // TODO: create loginAsAdmin to return a token for creating screenings
}


