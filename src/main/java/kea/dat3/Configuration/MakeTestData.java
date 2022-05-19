package kea.dat3.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    ObjectMapper objectMapper;

    private final List<Movie> testMovies = new ArrayList<>();

    public MakeTestData(PersonRepository personRepository, ScreeningRepository screeningRepository, RoomRepository roomRepository, MovieRepository movieRepository, ActorRepository actorRepository, GenreRepository genreRepository, ReservedSeatRepository reservedSeatRepository, ObjectMapper objectMapper) {
        this.personRepository = personRepository;
        this.screeningRepository = screeningRepository;
        this.roomRepository = roomRepository;
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.genreRepository = genreRepository;
        this.reservedSeatRepository = reservedSeatRepository;
        this.objectMapper = objectMapper;
    }

    public void makeSeats() {
        Room r1 = new Room("Room1", 10);
        roomRepository.save(r1);
        System.out.println(r1.getSeats());
        Movie m1 = testMovies.get(2);
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
        Room room = roomRepository.save(new Room("testRoom1"));
        List<Screening> screenings = new ArrayList<>();
        List<Integer> startHours = Arrays.asList(9, 14, 17, 20);
        testMovies.stream().forEach(m -> startHours.stream().forEach(h -> {
            Screening screening = new Screening(LocalDateTime.now().withHour(h), room, m);
            screenings.add(screening);
            m.addScreening(screening); // Synchronize (Update movie with the screening)
        }));
        screeningRepository.saveAll(screenings);
    }

    private void makeMovies() throws Exception {
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

        var m_4 = MovieBuilder.create("The Labyrinth", "Selfish girl fumbles around in a labyrinth. David Bowie has a bulge", 1987)
                .addActor("David", "Bowie", Year.of(1947))
                .addPosterUrl("https://m.media-amazon.com/images/M/MV5BMjM2MDE4OTQwOV5BMl5BanBnXkFtZTgwNjgxMTg2NzE@._V1_.jpg")
                .addLengthInMinutes(120)
                .addBasePrice(120)
                .build();

        var m_5 = MovieBuilder.create("Star Wars: Return of the Jedi", "A farm boy discovers hidden magic powers and The Princess needs no rescuing", 1983)
                .addLengthInMinutes(131)
                .addBasePrice(120)
                .addPosterUrl("https://m.media-amazon.com/images/M/MV5BOWZlMjFiYzgtMTUzNC00Y2IzLTk1NTMtZmNhMTczNTk0ODk1XkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_.jpg")
                .build();

        testMovies.addAll(List.of(m_1, m_2, m_3, m_4, m_5));
        String testMoviesJson = "[{\"title\":\"Beta Test\",\"description\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\",\"releaseYear\":\"2016\",\"lengthInMinutes\":\"120\",\"posterUrl\":\"https://m.media-amazon.com/images/M/MV5BODdlMjU0MDYtMWQ1NC00YjFjLTgxMDQtNDYxNTg2ZjJjZDFiXkEyXkFqcGdeQXVyMTU2NTcxNDg@._V1_SX300.jpg\"},{\"title\":\"The Beta Test\",\"description\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\",\"releaseYear\":\"2021\",\"lengthInMinutes\":\"120\",\"posterUrl\":\"https://m.media-amazon.com/images/M/MV5BOTE4ZjI4ZjMtMzJhOS00Y2U5LTg3YTAtZjcyZmU1NjAyNjRjXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_SX300.jpg\"},{\"title\":\"Test Pilot\",\"description\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\",\"releaseYear\":\"1938\",\"lengthInMinutes\":\"120\",\"posterUrl\":\"https://m.media-amazon.com/images/M/MV5BZjVjZmQyNzAtNTBiOC00MjNkLTk1NjktNGI1YmFmYjA0ODNmXkEyXkFqcGdeQXVyMDI2NDg0NQ@@._V1_SX300.jpg\"},{\"title\":\"Test\",\"description\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\",\"releaseYear\":\"2013\",\"lengthInMinutes\":\"120\",\"posterUrl\":\"https://m.media-amazon.com/images/M/MV5BMTQwMDU5NDkxNF5BMl5BanBnXkFtZTcwMjk5OTk4OQ@@._V1_SX300.jpg\"},{\"title\":\"The Test\",\"description\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\",\"releaseYear\":\"2012\",\"lengthInMinutes\":\"120\",\"posterUrl\":\"https://m.media-amazon.com/images/M/MV5BMTYwNTgzMjM5M15BMl5BanBnXkFtZTcwNDUzMTE1OA@@._V1_SX300.jpg\"},{\"title\":\"The Test\",\"description\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\",\"releaseYear\":\"2013\",\"lengthInMinutes\":\"120\",\"posterUrl\":\"https://m.media-amazon.com/images/M/MV5BMjMzMDQwMzM2M15BMl5BanBnXkFtZTcwMzA1OTg1OQ@@._V1_SX300.jpg\"},{\"title\":\"This Is Not a Test\",\"description\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\",\"releaseYear\":\"1962\",\"lengthInMinutes\":\"120\",\"posterUrl\":\"https://m.media-amazon.com/images/M/MV5BOTU5MDkwNDAzOV5BMl5BanBnXkFtZTgwNjE4NDgwMzE@._V1_SX300.jpg\"},{\"title\":\"Rabbit Test\",\"description\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\",\"releaseYear\":\"1978\",\"lengthInMinutes\":\"120\",\"posterUrl\":\"https://m.media-amazon.com/images/M/MV5BYmMyYzYxNmYtMGU4OC00MDFlLWJiYmQtZTJmNTMwZjg1ZTkwXkEyXkFqcGdeQXVyMTY5MDE5NA@@._V1_SX300.jpg\"},{\"title\":\"Test Pattern\",\"description\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\",\"releaseYear\":\"2019\",\"lengthInMinutes\":\"120\",\"posterUrl\":\"https://m.media-amazon.com/images/M/MV5BOGIyYTBiYjQtNDg4Ny00MTVhLThhNzYtN2U0NTJhNDRjNjQ5XkEyXkFqcGdeQXVyMjQ5MzM4NzE@._V1_SX300.jpg\"},{\"title\":\"Sound Test for Blackmail\",\"description\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\",\"releaseYear\":\"1929\",\"lengthInMinutes\":\"120\",\"posterUrl\":\"https://m.media-amazon.com/images/M/MV5BYzhlZDJjZWItMThlMi00ZGJlLWI2OTQtZjViYTQ4ZWNkOGRmXkEyXkFqcGdeQXVyMjA3NDg2Mzg@._V1_SX300.jpg\"}]";
        // Parse JSON with jackson (json from here: http://www.omdbapi.com/?apikey=xxxxxxxx&type=movie&plot=short&s=test)
        List<Movie> movies = Arrays.asList(objectMapper.readValue(testMoviesJson, Movie[].class));
        testMovies.addAll(movies);
        movieRepository.saveAll(testMovies);
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