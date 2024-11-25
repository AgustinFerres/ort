package obligatorio2p2.controller;

import obligatorio2p2.dto.BookSaleDTO;
import obligatorio2p2.model.Book;
import obligatorio2p2.model.Sale;
import obligatorio2p2.service.SaleService;

import java.util.Date;
import java.util.List;
import java.util.Set;


public class SaleController {

    private final SaleService service;

    public SaleController () {

        this.service = new SaleService();
    }

    public void registerSale ( Sale sale ) {

        service.registerSale(sale);
    }

    public int getNewId () {
        return service.getNewId();
    }

    public Sale getSale ( Integer id ) {

        return service.getSale(id);
    }

    public Set<Sale> getSales () {

        return service.getSales();
    }

    public List<BookSaleDTO> getSalesByISBN ( String isbn ) {

        return service.getSalesByISBN(isbn);
    }

    public void disableSale ( Integer id ) {
        service.disableSale(id);
    }
}
