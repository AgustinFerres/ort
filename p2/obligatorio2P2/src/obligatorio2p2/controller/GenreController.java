package obligatorio2p2.controller;

import obligatorio2p2.model.Genre;
import obligatorio2p2.service.GenreService;

import java.util.Set;


/**
 * @author Agustin Ferres - n° 323408
 */
public class GenreController {

    private final GenreService genreService;

    public GenreController () {

        genreService = new GenreService();
    }

    public void registerGenre (
        String name,
        String description
    ) throws IllegalArgumentException {

        genreService.registerGenre(name, description);
    }

    public Genre getGenre ( String name ) {

        return genreService.getGenre(name);
    }

    public Set<Genre> getGenres () {

        return genreService.getGenres();
    }
}
