package obligatorio2p2.controller;

import obligatorio2p2.model.Author;
import obligatorio2p2.service.AuthorService;

import java.util.Set;


public class AuthorController {

    private final AuthorService authorService;

    public AuthorController () {

        authorService = new AuthorService();
    }

    public void registerAuthor ( Author author ) throws IllegalArgumentException {

        authorService.registerAuthor(author);
    }

    public Author getAuthor ( String name ) {

        return authorService.getAuthor(name);
    }

    public Set<Author> getAuthors () {

        return authorService.getAuthors();
    }

    public Set<Author> getAuthorsByGenre ( String genre ) {

        return authorService.getAuthorsByGenre(genre);
    }
}
