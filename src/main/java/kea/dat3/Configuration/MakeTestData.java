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
        Person user = new Person("email@testUser.dk", "gitteUser", "test", "12342121", "testNameUser", "testPassowrdUser");
        Person admin = new Person("email@testAdmin.dk", "gitteAdmin", "test", "22342122", "testNameAdmin", "testPassowrdAdmin");
        Person user_admin = new Person("email@testUserAdmin.dk", "gitteUserAdmin", "test", "32342123", "testNameUserAdmin", "testPassowrdUserAdmin");
        user.addRole(Role.CUSTUMER);
        admin.addRole(Role.ADMIN);
        user_admin.addRole(Role.CUSTUMER);
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