package obligatorio2p2.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Agustin Ferres - n° 323408
 */
public class Sale implements Serializable {

    private Date date;
    private String client;
    private Integer id;
    private final Map<Book, Integer> books;
    private boolean disabled;

    public Sale (
        Date date,
        String client,
        Integer id
    ) {

        this.date = date;
        this.client = client;
        this.id = id;
        this.books = new HashMap<>();
        this.disabled = false;
    }

    public void addBook ( Book book ) {

        books.merge(book, 1, Integer::sum);
    }

    public void removeBook ( Book book ) {

        int quantity = books.getOrDefault(book, 0);

        // If the book is not in the sale, do nothing
        if ( quantity == 0 ) {
            return;
        }

        // If the book is in the sale and there is only one, remove it
        if ( quantity == 1 ) {
            books.remove(book);
            return;
        }

        // Else, decrease the quantity by one
        books.put(book, books.get(book) - 1);
    }

    public Map<Book, Integer> getBooks () {

        return books;
    }

    public Date getDate () {

        return date;
    }

    public void setDate ( Date date ) {

        this.date = date;
    }

    public String getClient () {

        return client;
    }

    public void setClient ( String client ) {

        this.client = client;
    }

    public Integer getId () {

        return id;
    }

    public void setId ( Integer id ) {

        this.id = id;
    }

    public Double getTotal () {

        return books.entrySet().stream().mapToDouble(e -> e.getKey().getPrice() * e.getValue()).sum();
    }

    public boolean isActive () {

        return Boolean.FALSE.equals(disabled);
    }

    public void disable () {

        this.disabled = true;
    }
}
