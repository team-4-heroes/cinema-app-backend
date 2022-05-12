package kea.dat3.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import kea.dat3.entities.Person;
import kea.dat3.entities.Role;
import kea.dat3.repositories.PersonRepository;
import kea.dat3.security.dto.LoginRequest;
import kea.dat3.security.dto.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class DummyRoleControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    public void setupPersonControllerTest() {
        personRepository.deleteAll();
        Person user = new Person("email@testUser.dk", "gitteUser", "test", "12342121", "testNameUser", "testPassowrdUser");
        Person admin = new Person("email@testAdmin.dk", "gitteAdmin", "test", "22342122", "testNameAdmin", "testPassowrdAdmin");
        Person user_admin = new Person("email@testUserAdmin.dk", "gitteUserAdmin", "test", "32342123", "testNameUserAdmin", "testPassowrdUserAdmin");
        admin.addRole(Role.ADMIN);
        user.addRole(Role.CUSTUMER);
        personRepository.save(user);
        personRepository.save(admin);
    }

    private String login(String username, String pw) throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new LoginRequest(username, pw))))
                .andReturn();
        if (response.getResponse().getStatus() == 401) {
            throw new Exception("Failed to login");
        }
        return objectMapper.readValue(response.getResponse().getContentAsString(), LoginResponse.class).getToken();
    }

    @Test
    public void testNonAuthenticatedCannotAccess_api_message_user() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/message/user")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void testAuthenticatedUserCanAccess_api_message_user() throws Exception {
        //TODO --> Write a test to verify you can access "api/message/user" when logged-in whith the user role
    }
}
