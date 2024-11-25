package obligatorio2p2.gui.window;

import obligatorio2p2.gui.common.Window;
import obligatorio2p2.model.Book;
import obligatorio2p2.types.ObserverType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


/**
 * @author Agustin Ferres - n° 323408
 */
public class BookInformationWindow extends Window {

    public BookInformationWindow ( Book book ) {

        super("Información de libro");
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ISBN", "Título", "Autor", "Género", "Costo", "Precio", "Stock"},
            0
        );
        model.addRow(new Object[]{
            book.getIsbn(),
            book.getTitle(),
            book.getAuthor().getName(),
            book.getGenre().getName(),
            book.getCost(),
            book.getPrice(),
            book.getStock()
        });

        JTable table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 100));

        JScrollPane tableScrollPane = new JScrollPane(table);

        this.setContentPane(tableScrollPane);
        this.setPreferredSize(new Dimension(500, 100));
        this.pack();
        this.setLocationRelativeTo(null);
    }

    @Override
    public void update () {

    }

    @Override
    public List<ObserverType> getOTypes () {

        return List.of();
    }
}
