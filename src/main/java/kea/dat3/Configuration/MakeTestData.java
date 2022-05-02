package kea.dat3.Configuration;

import kea.dat3.entities.Person;
import kea.dat3.entities.Role;
import kea.dat3.repositories.PersonRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;


@Controller
@Profile("!test")
public class MakeTestData implements ApplicationRunner {

    PersonRepository personRepository;

    public MakeTestData( PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void makeUsers() {
        Person user = new Person("user@mail.dk","user","test12");
        Person admin = new Person("admin@mail.dk","admin","test12");
        Person user_admin = new Person("user-admin@mail.dk","user_admin","test12");
        user.addRole(Role.USER);
        admin.addRole(Role.ADMIN);
        user_admin.addRole(Role.USER);
        user_admin.addRole(Role.ADMIN);
        personRepository.save(user);
        personRepository.save(admin);
        personRepository.save(user_admin);
        System.out.println("CREATED " + personRepository.count() + " TEST PERSONS");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        makeUsers();
    }
}