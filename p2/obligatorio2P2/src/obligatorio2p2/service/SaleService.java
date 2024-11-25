package obligatorio2p2.service;

import obligatorio2p2.dto.BookSaleDTO;
import obligatorio2p2.model.Book;
import obligatorio2p2.model.Sale;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author Agustin Ferres - n° 323408
 */
public class SaleService extends Service {

    public SaleService () {

        super();
    }

    public void registerSale ( Sale sale ) throws IllegalArgumentException {

        this.validateStock(sale);

        database.saveSale(sale);
    }

    public int getNewId () {

        return database.getSales().size() + 1;
    }

    public Sale getSale ( Integer id ) {

        return database.getSale(id);
    }

    public Set<Sale> getSales () {

        return database.getSales().stream().filter(Sale::isActive).collect(Collectors.toSet());
    }

    public List<BookSaleDTO> getSalesByISBN ( String isbn ) throws IllegalArgumentException {

        Book book = database.getBook(isbn);

        if ( book == null ) {
            throw new IllegalArgumentException("No existe un libro con ese ISBN");
        }

        Set<Sale> sales = database.getSales();
        Set<Sale> filteredSales = new HashSet<>();

        for ( Sale sale : sales ) {
            if ( sale.isActive() && sale.getBooks().keySet().stream().anyMatch(b -> b.getIsbn().equals(isbn)) ) {
                filteredSales.add(sale);
            }
        }

        return filteredSales.stream().sorted(Comparator.comparing(Sale::getId)).map(s -> BookSaleDTO.of(
            s,
            book
        )).collect(Collectors.toList());
    }

    public void disableSale ( Integer id ) {

        Sale sale = database.getSale(id);
        if ( sale == null ) {
            throw new IllegalArgumentException("No existe una venta con ese ID");
        }

        // Restore stock
        sale.getBooks().forEach(( book, quantity ) -> book.setStock(book.getStock() + quantity));
        // Disable sale
        sale.disable();
    }

    private void validateStock ( Sale sale ) throws IllegalArgumentException {

        Map<Book, Integer> books = sale.getBooks();
        List<Book> outOfStock = new ArrayList<>();

        for ( Map.Entry<Book, Integer> entry : books.entrySet() ) {
            Book book = entry.getKey();
            Integer quantity = entry.getValue();

            if ( !book.hasStock() ) { // If there is no stock, remove the book from the sale
                outOfStock.add(book);
                for ( int i = 0 ; i < quantity ; i++ ) {
                    sale.removeBook(book);
                }
            } else if ( book.getStock() < quantity ) { // If there is not enough stock, adjust the quantity to the available stock
                outOfStock.add(book);
                entry.setValue(book.getStock());
            } else { // Else, decrease the stock by the quantity
                book.setStock(book.getStock() - quantity);
            }
        }

        if ( !outOfStock.isEmpty() ) { // If any changes were made to the sale, throw an exception to notify the user
            throw new IllegalArgumentException(
                "Algunos libros no tienen stock suficiente, se ajustó la cantidad de libros en la venta");
        }
    }
}
