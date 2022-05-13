package kea.dat3.Configuration;

import kea.dat3.entities.*;
import kea.dat3.repositories.MovieRepository;
import kea.dat3.repositories.PersonRepository;
import kea.dat3.repositories.RoomRepository;
import kea.dat3.repositories.ScreeningRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;


@Controller
@Profile("!test")
public class MakeTestData implements ApplicationRunner {

    PersonRepository personRepository;
    ScreeningRepository screeningRepository;
    RoomRepository roomRepository;
    MovieRepository movieRepository;

    public MakeTestData(PersonRepository personRepository, ScreeningRepository screeningRepository, RoomRepository roomRepository, MovieRepository movieRepository) {
        this.personRepository = personRepository;
        this.screeningRepository = screeningRepository;
        this.roomRepository = roomRepository;
        this.movieRepository = movieRepository;
    }

    public void makeUsers() {
        System.out.println("Jeg er her");
        Person customer = new Person("email@testCustumer.dk", "gitteCustumer", "test", "12342121", "testNameCustumer", "testPassowrdCustumer");
        Person admin = new Person("email@testAdmin.dk", "gitteAdmin", "test", "22342122", "testNameAdmin", "testPassowrdAdmin");
        Person staff = new Person("email@testStaff.dk", "gitteStaff", "test", "32342123", "testNameStaff", "testPassowrdStaff");
        Person userAdmin = new Person("email@testUserAdmin.dk", "gitteUserAdmin", "test", "42342124", "testNameUserAdmin", "testPassowrdUserAdmin");

        customer.addRole(Role.CUSTUMER);
        admin.addRole(Role.ADMIN);
        staff.addRole(Role.STAFF);
        userAdmin.addRole(Role.CUSTUMER);
        userAdmin.addRole(Role.ADMIN);

        System.out.println(admin.getId());

        personRepository.save(customer);
        personRepository.save(admin);
        personRepository.save(staff);
        personRepository.save(userAdmin);
        System.out.println("CREATED " + personRepository.count() + " TEST PERSONS");
    }

    public void makeScreenings() {
        Room room = roomRepository.save(new Room("testRoom"));
        String description = "This is a dummy description to see how it looks.";
        for (int i=1; i<6; i++) {
            Movie movie = movieRepository.save(new Movie("Movie Title Here",description,2000+i,100,100));
            for (int j=1; j<4; j++) {
                LocalDateTime startTime = LocalDateTime.of(2022, 10, 1, j * 3 + 8, 0);
                Screening screening = new Screening(startTime, room, movie);
                screeningRepository.save(screening);
            }
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        makeUsers();
        makeScreenings();
    }
}