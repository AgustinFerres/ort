package obligatorio2p2.controller;

import obligatorio2p2.model.Author;
import obligatorio2p2.model.Book;
import obligatorio2p2.model.Editorial;
import obligatorio2p2.model.Genre;
import obligatorio2p2.service.BookService;

import java.io.File;
import java.util.HashSet;
import java.util.Set;


/**
 * @author Agustin Ferres - n° 323408
 */
public class BookController {

    private final BookService service;

    public BookController () {

        this.service = new BookService();
    }

    public void registerBook (
        Editorial editorial,
        Genre genre,
        Author author,
        String isbn,
        String title,
        Double cost,
        Double price,
        Integer stock,
        File image
    ) {

        service.registerBook(editorial, genre, author, isbn, title, cost, price, stock, image);
    }

    public Book getBook ( String isbn ) {

        return service.getBook(isbn);
    }

    public Set<Book> getBooks () {

        return service.getBooks();
    }

    public Set<Book> getBooks (
        String genre,
        String title,
        String author
    ) {

        Set<Book> books = new HashSet<>(service.getBooksByGenre(genre));

        books.addAll(service.getBooksByTitle(title));
        books.addAll(service.getBooksByAuthor(author));

        return books;
    }
}
