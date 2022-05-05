package kea.dat3.services;

import kea.dat3.dto.PersonRequest;
import kea.dat3.dto.PersonResponse;
import kea.dat3.entities.Person;
import kea.dat3.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @Mock
    PersonRepository personRepository;
    PersonService personService;

    @BeforeEach
    void setupData(){
        personService = new PersonService(personRepository);
    }

    @Test
    void addPerson() {

        /*Person testPerson = new Person("email@test.dk", "gitte", "Sørensen", "22489032", "gitteTest", "123");
        long id = 100;
        testPerson.setId(id);
        Mockito.when(personRepository.save(any(Person.class))).thenReturn(testPerson);
        PersonResponse personRes = personService.addPerson(new PersonRequest(testPerson.getUsername(), testPerson.getFirstName(), testPerson.getLastName(), testPerson.getPhoneNumber(), testPerson.getUsername(), testPerson.getPassword()));
        assertEquals(100, personRes.getId());

        /*HobbyResponse hobbyRes = hobbyService.addHobby(new HobbyRequest(testHobby.getName(), testHobby.getEnvironment(),testHobby.getDescription(), testHobby.getCategory()));
        assertEquals(100, hobbyRes.getId());*/
    }
    @Test
    void getPersons() {
    }

    @Test
    void getPerson() {
    }

    @Test
    void editPerson() {
    }

    @Test
    void deletePerson() {
    }
}