package kea.dat3.Configuration;

import kea.dat3.entities.*;
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
    ReservedSeatRepository reservedSeatRepository;

    public MakeTestData(PersonRepository personRepository, ScreeningRepository screeningRepository, RoomRepository roomRepository, MovieRepository movieRepository, ActorRepository actorRepository, ReservedSeatRepository reservedSeatRepository) {
        this.personRepository = personRepository;
        this.screeningRepository = screeningRepository;
        this.roomRepository = roomRepository;
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.reservedSeatRepository = reservedSeatRepository;
    }

    public void makeSeats() {
        Room r1 = new Room("Room1", 10);
        roomRepository.save(r1);
        System.out.println(r1.getSeats());
        Movie m1 = new Movie("TestMovie", "A test movie for screenings", 1999, 120, 120);
        movieRepository.save(m1);
        Screening s1 = new Screening(LocalDateTime.now(), r1, m1);
        screeningRepository.save(s1);
        for (ReservedSeat rs : s1.getScreeningSeats())
            {
                System.out.println(rs);
            }
        System.out.println(s1.getScreeningSeats());
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
       /* for (int i=1; i<6; i++) {
            Movie movie = movieRepository.save(new Movie("Movie Title Here",description,2000+i,100,100));
            for (int j=1; j<4; j++) {
                LocalDateTime startTime = LocalDateTime.of(2022, 10, 1, j * 3 + 8, 0);
                Screening screening = new Screening(startTime, room, movie);
                screeningRepository.save(screening);
            }
        }*/
    }

    private void makeMovies() {
        var m_1 = MovieBuilder.create("Lord of the Rings: Fellowship of the Ring", "Little hobbits go on a grand adventure and Gandalf dies", 2001)
                .addActor("Viggo", "Mortensen", Year.of(1984))
                .addLengthInMinutes(178)
                .build();
        var m_2 = MovieBuilder.create("Lord of the Rings: The Two Towers", "Things get hairy and the party splits. Also wizards.", 2002)
                .addActor("Cate", "Blanchet", Year.of(1975))
                .addActor("Miranda", "Otto", Year.of(1987))
                .addLengthInMinutes(179)
                .build();
        var m_3 = MovieBuilder.create("Lord of the Rings: The Return of the King", "Split story. Slow crawl through Mordor and epic battles. Golumn save the day", 2003)
                .addActor("Andy", "Serkis", Year.of(1969))
                .addActor("John", "Rhys-Davies", Year.of(1969))
                .addLengthInMinutes(201)
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
        makeSeats();
    }
}