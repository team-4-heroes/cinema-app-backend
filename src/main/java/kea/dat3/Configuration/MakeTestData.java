package kea.dat3.Configuration;

import kea.dat3.entities.*;
import kea.dat3.entities.pegi.AgeLimit;
import kea.dat3.repositories.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.Year;


@Controller
@Profile("!test")
public class MakeTestData implements ApplicationRunner {

    PersonRepository personRepository;
    ScreeningRepository screeningRepository;
    RoomRepository roomRepository;
    MovieRepository movieRepository;
    ActorRepository actorRepository;

    public MakeTestData(PersonRepository personRepository, ScreeningRepository screeningRepository, RoomRepository roomRepository, MovieRepository movieRepository, ActorRepository actorRepository) {
        this.personRepository = personRepository;
        this.screeningRepository = screeningRepository;
        this.roomRepository = roomRepository;
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
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
        LocalDateTime startTime = LocalDateTime.of(2022, 10, 1, 10, 0);
        Room room = roomRepository.save(new Room("testRoom"));
        Movie movie = movieRepository.save(new Movie("film titel", "beskrivelse", 2000, 100, 100));
        Screening screening = new Screening(startTime, room, movie);
        screeningRepository.save(screening);
    }

    private void makeMovies() {
        var m_1 = MovieBuilder.create("Lord of the Rings: Fellowship of the Ring", "Little hobbits go on a grand adventure and Gandalf dies", 2001)
                .addActor("Viggo", "Mortensen", Year.of(1984))
                .addLengthInMinutes(178)
                .addAgeLimit(AgeLimit.PEGI_3)
                .build();
        var m_2 = MovieBuilder.create("Lord of the Rings: The Two Towers", "Things get hairy and the party splits. Also wizards.", 2002)
                .addActor("Cate", "Blanchet", Year.of(1975))
                .addActor("Miranda", "Otto", Year.of(1987))
                .addLengthInMinutes(179)
                .addAgeLimit(AgeLimit.PEGI_3)
                .build();
        var m_3 = MovieBuilder.create("Lord of the Rings: The Return of the King", "Split story. Slow crawl through Mordor and epic battles. Golumn save the day", 2003)
                .addActor("Andy", "Serkis", Year.of(1969))
                .addActor("John", "Rhys-Davies", Year.of(1969))
                .addLengthInMinutes(201)
                .addAgeLimit(AgeLimit.PEGI_3)
                .build();

        movieRepository.save(m_1);
        movieRepository.save(m_2);
        movieRepository.save(m_3);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        makeMovies();
        makeUsers();
        makeScreenings();
    }
}