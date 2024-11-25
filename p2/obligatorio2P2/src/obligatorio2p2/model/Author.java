package obligatorio2p2.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * @author Agustin Ferres - n° 323408
 */
public class Author implements Serializable {

    private String name;
    private String nationality;
    private Set<Genre> genres;

    public Author (
        String name,
        String nationality
    ) {

        this.name = name;
        this.nationality = nationality;
        this.genres = new HashSet<>();
    }

    public void addGenre ( Genre genre ) {

        genres.add(genre);
    }

    public void removeGenre ( Genre genre ) {

        genres.remove(genre);
    }

    public String getName () {

        return name;
    }

    public String getNationality () {

        return nationality;
    }

    public Set<Genre> getGenres () {

        return genres;
    }

    @Override
    public String toString () {

        return name;
    }
}
