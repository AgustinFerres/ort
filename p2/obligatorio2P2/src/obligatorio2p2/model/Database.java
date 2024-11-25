package obligatorio2p2.model;

import obligatorio2p2.types.ObserverType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * @author Agustin Ferres - n° 323408
 */
public class Database extends Subject implements Serializable {

    private static Database instance;
    private final Map<String, Editorial> editorials;
    private final Map<String, Genre> genres;
    private final Map<String, Author> authors;
    private final Map<String, Book> books;
    private final Map<Integer, Sale> sales;

    private Database () {

        this.editorials = new HashMap<>();
        this.genres = new HashMap<>();
        this.authors = new HashMap<>();
        this.books = new HashMap<>();
        this.sales = new HashMap<>();
    }

    public static synchronized Database getInstance () {

        if ( instance == null ) {
            instance = new Database();
        }
        return instance;
    }

    // To load a database from a file
    public void setInstance ( Database instance ) {

        Database.instance = instance;
    }

    public void saveEditorial ( Editorial editorial ) {

        editorials.putIfAbsent(editorial.getName(), editorial);
        notifyObservers(ObserverType.EDITORIAL);
    }

    public Editorial getEditorial ( String name ) {

        return editorials.get(name);
    }

    public Set<Editorial> getEditorials () {

        return Set.copyOf(editorials.values());
    }

    public void saveGenre ( Genre genre ) {

        genres.putIfAbsent(genre.getName(), genre);
        notifyObservers(ObserverType.GENRE);
    }

    public Genre getGenre ( String name ) {

        return genres.get(name);
    }

    public Set<Genre> getGenres () {

        return Set.copyOf(genres.values());
    }

    public void saveAuthor ( Author author ) {

        authors.putIfAbsent(author.getName(), author);
        notifyObservers(ObserverType.AUTHOR);
    }

    public Author getAuthor ( String name ) {

        return authors.get(name);
    }

    public Set<Author> getAuthors () {

        return Set.copyOf(authors.values());
    }

    public void saveBook ( Book book ) {

        books.putIfAbsent(book.getIsbn(), book);
        notifyObservers(ObserverType.BOOK);
    }

    public Book getBook ( String isbn ) {

        return books.get(isbn);
    }

    public Set<Book> getBooks () {

        return Set.copyOf(books.values());
    }

    public void saveSale ( Sale sale ) {

        sales.putIfAbsent(sale.getId(), sale);
        notifyObservers(ObserverType.SALE);
    }

    public Sale getSale ( Integer id ) {

        return sales.get(id);
    }

    public Set<Sale> getSales () {

        return Set.copyOf(sales.values());
    }
}
