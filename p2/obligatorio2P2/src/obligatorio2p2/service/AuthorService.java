package obligatorio2p2.service;

import obligatorio2p2.model.Author;
import obligatorio2p2.model.Genre;

import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author Agustin Ferres - n° 323408
 */
public class AuthorService extends Service {

    public AuthorService () {

        super();
    }

    public void registerAuthor ( Author author ) throws IllegalArgumentException {

        if ( database.getAuthor(author.getName()) != null ) {
            throw new IllegalArgumentException("Ya existe un autor con ese nombre");
        }

        database.saveAuthor(author);
    }

    public Author getAuthor ( String name ) {

        return database.getAuthor(name);
    }

    public Set<Author> getAuthors () {

        return database.getAuthors();
    }

    public Set<Author> getAuthorsByGenre ( String genreName ) {

        Genre genre = database.getGenre(genreName);
        if ( genre == null ) {
            return Set.of();
        }

        Set<Author> authors = database.getAuthors();

        return authors.stream().filter(author -> author.getGenres().contains(genre)).collect(Collectors.toSet());
    }
}
