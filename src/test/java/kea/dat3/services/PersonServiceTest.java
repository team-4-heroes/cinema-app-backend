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
        Person testPerson = new Person("email@test.dk", "gitte", "Sørensen", "22489032", "gitteTest", "123");
        long id = 100;
        Mockito.when(personRepository.save(any(Person.class))).thenReturn(testPerson);
        PersonResponse personRes = personService.addPerson(new PersonRequest(id ,testPerson.getUsername(), testPerson.getFirstName(), testPerson.getLastName(), testPerson.getPhoneNumber(), testPerson.getUsername(), testPerson.getPassword()));
        assertEquals(100, personRes.getId());
    }
    @Test
    void getPersons() {
        Mockito.when(personRepository.findAll()).thenReturn(List.of(
                new Person("email@test.dk", "gitte", "Sørensen", "22489032", "gitteTest", "123"),
                new Person("email@test2.dk", "gitte2", "Sørensen", "22489032", "gitteTest", "123")
        ));
        List<PersonResponse> persons = personService.getPersons();
        assertEquals(2,persons.size());
    }

    @Test
    void getPerson() {
        Person person = new Person("email@test.dk", "gitte", "Sørensen", "22489032", "gitteTest", "123");
        long id = 100;
        person.setId(id);
        Mockito.when(personRepository.findById(id)).thenReturn(Optional.of(person));
        PersonResponse personRes = personService.getPerson(id);
        assertEquals("gitteTest", personRes.getUsername());
    }

    @Test
    void editPerson() {
    }

    @Test
    void deletePerson() {
    }
}