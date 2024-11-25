package obligatorio2p2.dto;

import obligatorio2p2.model.Book;


/**
 * @author Agustin Ferres - n° 323408
 */
public class BookDTO {

    private String isbn;
    private String title;
    private Integer qty;
    private Double price;

    public static BookDTO of (
        Book book,
        Integer qty
    ) {

        BookDTO dto = new BookDTO();
        dto.isbn = book.getIsbn();
        dto.title = book.getTitle();
        dto.qty = qty;
        dto.price = book.getPrice();

        return dto;
    }

    public String getIsbn () {

        return isbn;
    }

    @Override
    public String toString () {

        return qty + " - " + title + " - $ " + price;
    }
}
