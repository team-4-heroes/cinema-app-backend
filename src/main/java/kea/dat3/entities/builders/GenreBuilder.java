package kea.dat3.entities.builders;

import kea.dat3.entities.Genre;

public class GenreBuilder {

    private Genre genre;

    private GenreBuilder() {
    }

    public static GenreBuilder create(String name) {
        var genreBuilder = new GenreBuilder();
        var genre = new Genre();
        genre.setName(name);
        genreBuilder.setGenre(genre);
        return genreBuilder;
    }

    private void setGenre(Genre genre) {
        this.genre = genre;
    }

    public GenreBuilder addId(Long id) {
        genre.setId(id);
        return this;
    }

    public Genre build() {
        var temp = genre;
        genre = null;
        return temp;
    }
}
