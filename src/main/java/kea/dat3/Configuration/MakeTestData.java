package kea.dat3.Configuration;

import kea.dat3.entities.*;
import kea.dat3.entities.builders.ActorBuilder;
import kea.dat3.entities.builders.GenreBuilder;
import kea.dat3.entities.builders.MovieBuilder;
import kea.dat3.entities.pegi.AgeLimit;
import kea.dat3.repositories.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;

//@AllArgsConstructor
//@NoArgsConstructor
@Controller
@Profile("!test")
public class MakeTestData implements ApplicationRunner {

    PersonRepository personRepository;
    ScreeningRepository screeningRepository;
    RoomRepository roomRepository;
    MovieRepository movieRepository;
    ActorRepository actorRepository;
    GenreRepository genreRepository;
    ReservedSeatRepository reservedSeatRepository;

    public MakeTestData(PersonRepository personRepository, ScreeningRepository screeningRepository, RoomRepository roomRepository, MovieRepository movieRepository, ActorRepository actorRepository, GenreRepository genreRepository, ReservedSeatRepository reservedSeatRepository) {
        this.personRepository = personRepository;
        this.screeningRepository = screeningRepository;
        this.roomRepository = roomRepository;
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.genreRepository = genreRepository;
        this.reservedSeatRepository = reservedSeatRepository;
    }

    public void makeSeats() {
        Room r1 = new Room("Room1", 10);
        roomRepository.save(r1);
        System.out.println(r1.getSeats());
        Movie m1 = new Movie("TestMovie", "A test movie for screenings", 1999, 120, 120, "https://no-poster-url");
        movieRepository.save(m1);
        Screening s1 = new Screening(LocalDateTime.now(), r1, m1);
        screeningRepository.save(s1);
        for (ReservedSeat rs : s1.getScreeningSeats()) {
            // System.out.println(rs);
        }
        System.out.println(s1.getScreeningSeats());
    }

    public void makeUsers() {
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
        Movie movie = movieRepository.save(MovieBuilder.create().addAllDefaultAttributes().build());
        LocalDateTime startTime = LocalDateTime.now();
        Screening screening = new Screening(startTime, room, movie);
        screeningRepository.save(screening);
    }

    private void makeMovies() {
        var m_1 = MovieBuilder.create("Lord of the Rings: Fellowship of the Ring", "Little hobbits go on a grand adventure and Gandalf dies", 2001)
                .addActor("Viggo", "Mortensen", Year.of(1984))
                .addActor("John", "Rhys-Davies", Year.of(1969))
                .addActor("Christopher", "Lee", Year.of(1922))
                .addLengthInMinutes(178)
                .addAgeLimit(AgeLimit.PEGI_3)
                .addPosterUrl("https://d1nslcd7m2225b.cloudfront.net/Pictures/480xAny/4/7/7/1252477_fellowship.jpg")
                .build();
        var m_2 = MovieBuilder.create("Lord of the Rings: The Two Towers", "Things get hairy and the party splits. Also wizards.", 2002)
                .addActor("Cate", "Blanchet", Year.of(1975))
                .addActor("Miranda", "Otto", Year.of(1987))
                .addLengthInMinutes(179)
                .addAgeLimit(AgeLimit.PEGI_3)
                .addPosterUrl("https://thecinematicket.files.wordpress.com/2022/02/two-towers-poster.jpg")
                .build();
        var m_3 = MovieBuilder.create("Lord of the Rings: The Return of the King", "Split story. Slow crawl through Mordor and epic battles. Golumn save the day", 2003)
                .addActor("Andy", "Serkis", Year.of(1969))
                .addLengthInMinutes(201)
                .addAgeLimit(AgeLimit.PEGI_3)
                .addPosterUrl("https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1280x1280/products/82360/91855/THE-LORD-OF-THE-RINGS-THE-RETURN-OF-THE-KING-DS-ADV-Style-A-2003-ORIGINAL-CINEMA-POSTER__10176.1543239944.jpg?c=2")
                .build();

        movieRepository.save(m_1);
        movieRepository.save(m_2);
        movieRepository.save(m_3);
    }

    private void makeActors() {
        var a_1 = ActorBuilder.create("Eva", "Green", LocalDate.of(1980, 7, 06)).build();
        var a_2 = ActorBuilder.create("Eva", "Green", LocalDate.of(1980, 7, 06)).build();
        var a_3 = ActorBuilder.create("Matthew", "McConaughey", LocalDate.of(1969, 11, 9)).build();

        actorRepository.save(a_1);
        actorRepository.save(a_2);
        actorRepository.save(a_3);
    }

    private void makeGenres() {
        var g_1 = GenreBuilder.create("Horror").addId(1000L).build();
        var g_2 = GenreBuilder.create("Action").addId(2000L).build();
        var g_3 = GenreBuilder.create("Sci-fi").addId(3000L).build();

        genreRepository.save(g_1);
        genreRepository.save(g_2);
        genreRepository.save(g_3);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        makeActors();
        makeGenres();
        makeMovies();
        makeUsers();
        makeScreenings();
        makeSeats();
    }
}