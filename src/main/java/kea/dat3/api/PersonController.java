package kea.dat3.api;

import kea.dat3.dto.PersonRequest;
import kea.dat3.dto.PersonResponse;
import kea.dat3.services.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/persons")
public class PersonController {

    PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<PersonResponse> addPerson(@RequestBody @Valid PersonRequest body) {
        return ResponseEntity.ok(personService.addPerson(body));
    }

    @GetMapping
    public List<PersonResponse> getPersons(){
        return personService.getPersons();
    }

    @GetMapping("/{username}")
    public PersonResponse getPerson(@PathVariable String username)  {
        return personService.getPerson(username);
    }

    @PutMapping("/{username}")
    public PersonResponse editPerson(@RequestBody PersonRequest body, @PathVariable String username){
        return personService.editPerson(body, username);
    }

    @DeleteMapping("/{username}")
    public void deletePerson(@PathVariable String username){
        personService.deletePerson(username);
    }
}
