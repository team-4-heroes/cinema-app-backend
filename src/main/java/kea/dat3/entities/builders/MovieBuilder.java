package kea.dat3.entities.builders;

import kea.dat3.entities.Actor;
import kea.dat3.entities.Movie;
import kea.dat3.entities.pegi.AgeLimit;

import java.time.LocalDate;
import java.time.Year;

public class MovieBuilder {

    private Movie movie;

    private MovieBuilder() {
        // Private constructor to prevent MovieBuilder from being created from the outside
    }

    public static MovieBuilder create(String title, String description, int releaseYear) {
        var movieBuilder = new MovieBuilder();
        var movie = new Movie();
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setReleaseYear(releaseYear);
        movieBuilder.setMovie(movie);
        return movieBuilder;
    }

    private void setMovie(Movie movie) {
        this.movie = movie;
    }

    public MovieBuilder addLengthInMinutes(int lengthInMinutes) {
        movie.setLengthInMinutes(lengthInMinutes);
        return this;
    }

    public MovieBuilder addActor(String firstName, String lastName, Year year) {
        return addActor(ActorBuilder.create(firstName, lastName, LocalDate.of(year.getValue(), 1, 1)).build());
    }

    public MovieBuilder addAgeLimit(AgeLimit ageLimit) {
        movie.setAgeLimit(ageLimit);
        return this;
    }

    public MovieBuilder addActor(Actor actor) {
        movie.getActors().add(actor);
        return this;
    }

    public Movie build() {
        var temp = movie;
        movie = null; // Crude way of ensuring that the MovieBuilder object isn't used anymore after call to build
        return temp;
    }
}
