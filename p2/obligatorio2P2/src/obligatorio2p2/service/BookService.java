package obligatorio2p2.service;

import obligatorio2p2.model.Author;
import obligatorio2p2.model.Book;
import obligatorio2p2.model.Editorial;
import obligatorio2p2.model.Genre;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author Agustin Ferres - n° 323408
 */
public class BookService extends Service {

    public BookService () {

        super();
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
    )
        throws IllegalArgumentException {

        if ( database.getBook(isbn) != null ) {
            throw new IllegalArgumentException("Ya existe un libro con ese ISBN");
        }

        database.saveBook(new Book(
            editorial,
            genre,
            author,
            isbn,
            title,
            cost,
            price,
            stock,
            this.saveImage(isbn, image)
        ));
    }

    public Book getBook ( String isbn ) {

        return database.getBook(isbn);
    }

    public Set<Book> getBooks () {

        return database.getBooks();
    }

    public Set<Book> getBooksByGenre ( String genreName ) {

        Genre genre = database.getGenre(genreName);
        if ( genre == null ) {
            return Set.of();
        }

        Set<Book> books = database.getBooks();

        return books.stream().filter(book -> book.getGenre().equals(genre)).collect(Collectors.toSet());
    }

    public Set<Book> getBooksByAuthor ( String authorName ) {

        Author author = database.getAuthor(authorName);
        if ( author == null ) {
            return Set.of();
        }

        Set<Book> books = database.getBooks();

        return books.stream().filter(book -> book.getAuthor().equals(author)).collect(Collectors.toSet());
    }

    public Set<Book> getBooksByTitle ( String title ) {

        Set<Book> books = database.getBooks();

        return books.stream().filter(book -> book.getTitle().contains(title)).collect(Collectors.toSet());
    }

    private File saveImage (
        String isbn,
        File image
    ) {

        if ( image == null ) {
            return null;
        }

        // Save image to images directory
        File imagesDir = new File("images");
        if ( !imagesDir.exists() ) {
            imagesDir.mkdirs();
        }

        File destinationFile = new File(imagesDir, isbn + getFileExtension(image));
        try {
            Files.copy(image.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch ( IOException e ) {
            e.printStackTrace();
            return null;
        }

        return destinationFile;
    }

    private String getFileExtension ( File file ) {

        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if ( dotIndex > 0 && dotIndex < fileName.length() - 1 ) {
            return fileName.substring(dotIndex);
        }
        return "";
    }

}
