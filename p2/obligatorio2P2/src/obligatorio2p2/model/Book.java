package obligatorio2p2.model;

import java.io.File;
import java.io.Serializable;


/**
 * @author Agustin Ferres - n° 323408
 */
public class Book implements Serializable {

    private Editorial editorial;
    private Genre genre;
    private Author author;
    private String isbn;
    private String title;
    private Double cost;
    private Double price;
    private Integer stock;
    private File image;

    public Book (
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

        this.editorial = editorial;
        this.genre = genre;
        this.author = author;
        this.isbn = isbn;
        this.title = title;
        this.cost = cost;
        this.price = price;
        this.stock = stock;
        this.image = image;
    }

    public boolean hasStock () {

        return stock > 0;
    }

    public Genre getGenre () {

        return genre;
    }

    public void setGenre ( Genre genre ) {

        this.genre = genre;
    }

    public Editorial getEditorial () {

        return editorial;
    }

    public void setEditorial ( Editorial editorial ) {

        this.editorial = editorial;
    }

    public Author getAuthor () {

        return author;
    }

    public void setAuthor ( Author author ) {

        this.author = author;
    }

    public String getIsbn () {

        return isbn;
    }

    public void setIsbn ( String isbn ) {

        this.isbn = isbn;
    }

    public String getTitle () {

        return title;
    }

    public void setTitle ( String title ) {

        this.title = title;
    }

    public Double getCost () {

        return cost;
    }

    public void setCost ( Double cost ) {

        this.cost = cost;
    }

    public Double getPrice () {

        return price;
    }

    public void setPrice ( Double price ) {

        this.price = price;
    }

    public Integer getStock () {

        return stock;
    }

    public void setStock ( Integer stock ) {

        this.stock = stock;
    }

    public File getImage () {

        return image;
    }

    public void setImage ( File image ) {

        this.image = image;
    }

    @Override
    public String toString () {

        return isbn + " - " + title;
    }

    @Override
    public boolean equals ( Object obj ) {

        return obj instanceof Book && ((Book) obj).isbn.equals(isbn);
    }

    @Override
    public int hashCode () {

        return isbn.hashCode();
    }
}
