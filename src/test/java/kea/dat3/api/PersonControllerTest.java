package kea.dat3.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import kea.dat3.entities.Person;
import kea.dat3.repositories.PersonRepository;
import kea.dat3.services.PersonService;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class PersonControllerTest {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  PersonRepository personRepository;

  @Autowired
  PersonService personService;
  @Autowired
  ObjectMapper objectMapper;

  @BeforeEach
  public void setupPersonControllerTest() {
    personRepository.deleteAll();
    Person user = new Person("email@testUser.dk", "gitteUser", "test", "12342121", "testNameUser", "testPassowrdUser");
    Person admin = new Person("email@testAdmin.dk", "gitteAdmin", "test", "22342122", "testNameAdmin", "testPassowrdAdmin");
    Person user_admin = new Person("email@testUserAdmin.dk", "gitteUserAdmin", "test", "32342123", "testNameUserAdmin", "testPassowrdUserAdmin");
    //Add roles if your testing security
    personRepository.save(user);
    personRepository.save(admin);
  }

  @Test
  void testPersonFound() throws Exception {
    // request a nonexistent person and verify HTTP Status and error response
    mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/persons/u1")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("u1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("u1@e.dk"));
  }

  @Test
  void testPersonNotFound() throws Exception {
    // request a nonexistent person and verify HTTP Status and error response
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/persons/xxx")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();
    assertEquals("Person with id 'xxx' not found",result.getResponse().getErrorMessage());
  }

}
