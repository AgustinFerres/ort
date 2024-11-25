package obligatorio2p2.service;

import obligatorio2p2.model.Genre;

import java.util.Set;


/**
 * @author Agustin Ferres - n° 323408
 */
public class GenreService extends Service {

    public GenreService () {

        super();
    }

    public void registerGenre (
        String name,
        String description
    ) throws IllegalArgumentException {

        if ( database.getGenre(name) != null ) {
            throw new IllegalArgumentException("Ya existe un género con ese nombre");
        }

        database.saveGenre(new Genre(name, description));
    }

    public Genre getGenre ( String name ) {

        return database.getGenre(name);
    }

    public Set<Genre> getGenres () {

        return database.getGenres();
    }
}
