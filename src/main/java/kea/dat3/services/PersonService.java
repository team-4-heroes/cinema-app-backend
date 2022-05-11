package kea.dat3.services;

import kea.dat3.dto.PersonRequest;
import kea.dat3.dto.PersonResponse;
import kea.dat3.error.PersonNotFoundException;
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
          throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken");
        }
        if(personRepository.existsByEmail(body.getEmail())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Email is used by another person");
        }
        if(personRepository.existsByPhoneNumber(body.getPhoneNumber())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Phonenumber is already in use");
        }
        Person newPerson = new Person(body);
        newPerson.addRole(Role.CUSTUMER);
        personRepository.save(newPerson);
        return new PersonResponse(newPerson);

    }

    public List<PersonResponse> getPersons() {
        List<Person> persons = personRepository.findAll();
        return PersonResponse.getPersonsFromEntities(persons);
    }

    public PersonResponse getPerson(long id) {
        Person person = personRepository.findById(id).orElseThrow(()->new PersonNotFoundException(id));
        return new PersonResponse(person);
    }

    public PersonResponse getPerson(String username) {
        Person person = personRepository.findByUsername(username).orElseThrow(()->new PersonNotFoundException(username));
        return new PersonResponse(person);
    }

    public PersonResponse editPerson(PersonRequest body, long id) {
        Person person = personRepository.findById(id).orElseThrow(()->new PersonNotFoundException(id));
        person.setFirstName(body.getFirstName());
        person.setLastName(body.getLastName());
        person.setPhoneNumber(body.getPhoneNumber());
        person.setEmail(body.getEmail());
        person.setPassword(body.getPassword());
        return new PersonResponse(personRepository.save(person));
    }

    public PersonResponse editPerson(PersonRequest body, String username) {
        Person person = personRepository.findByUsername(username).orElseThrow(()->new PersonNotFoundException(username));
        person.setFirstName(body.getFirstName());
        person.setLastName(body.getLastName());
        person.setPhoneNumber(body.getPhoneNumber());
        person.setEmail(body.getEmail());
        person.setPassword(body.getPassword());
        return new PersonResponse(personRepository.save(person));
    }

    public void deletePerson(long id) {
        Person person = personRepository.findById(id).orElseThrow(()->new PersonNotFoundException(id));
        personRepository.delete(person);
    }

    public void deletePerson(String username) {
        Person person = personRepository.findByUsername(username).orElseThrow(()->new PersonNotFoundException(username));
        personRepository.delete(person);
    }
}