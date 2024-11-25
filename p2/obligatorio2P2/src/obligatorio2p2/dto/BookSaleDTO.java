package obligatorio2p2.dto;

import obligatorio2p2.model.Book;
import obligatorio2p2.model.Sale;

import java.util.Date;


public class BookSaleDTO {
    private Date date;
    private String client;
    private Integer id;
    private String isbn;
    private Integer quantity;
    private Double price;
    private Double total;
    private Double winnings;

    public static BookSaleDTO of (
        Sale sale,
        Book book
    ) {

        BookSaleDTO dto = new BookSaleDTO();
        dto.date = sale.getDate();
        dto.client = sale.getClient();
        dto.id = sale.getId();
        dto.isbn = book.getIsbn();
        dto.quantity = sale.getBooks().get(book);
        dto.price = book.getPrice();
        dto.total = dto.price * dto.quantity;
        dto.winnings = dto.total - book.getCost() * dto.quantity;
        return dto;
    }

    public Date getDate () {

        return date;
    }

    public String getClient () {

        return client;
    }

    public Integer getId () {

        return id;
    }

    public String getIsbn () {

        return isbn;
    }

    public Integer getQuantity () {

        return quantity;
    }

    public Double getPrice () {

        return price;
    }

    public Double getTotal () {

        return total;
    }

    public Double getWinnings () {

        return winnings;
    }
}
