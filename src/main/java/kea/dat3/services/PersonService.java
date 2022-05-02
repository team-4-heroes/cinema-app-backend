package kea.dat3.services;

import kea.dat3.dto.PersonRequest;
import kea.dat3.dto.PersonResponse;
import kea.dat3.repositories.PersonRepository;
import kea.dat3.entities.Person;
import kea.dat3.entities.Role;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PersonService {

    PersonRepository personRepository;


    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonResponse addPerson(PersonRequest body) {
        if(personRepository.existsByUsername(body.getUsername())) {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username is already taken");
        }
        if(personRepository.existsByEmail(body.getEmail())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Email is used by another person");
        }

        Person person = new Person(body);
        // All new users are by default given the role USER
        person.addRole(Role.USER);
        personRepository.save(person);
        return new PersonResponse(person);
    }

    public List<PersonResponse> getPersons() {
        List<Person> persons = personRepository.findAll();
        return PersonResponse.getPersonsFromEntities(persons);
    }

    public PersonResponse getPerson(String id) {
        Person person = personRepository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Person with id '"+id+"' not found"));
        return new PersonResponse(person);
    }

    public PersonResponse editPerson(PersonRequest body, String id) {
        Person person = personRepository.findById(id).orElseThrow();
        return new PersonResponse(personRepository.save(person));
    }

    public void deletePerson(String id) {
        Person person = personRepository.findById(id).orElseThrow();
        personRepository.delete(person);
    }

}