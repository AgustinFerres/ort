package obligatorio2p2.gui.menu;

import obligatorio2p2.gui.window.ViewBooksWindow;
import obligatorio2p2.gui.window.ViewSalesWindow;

import javax.swing.*;


/**
 * @author Agustin Ferres - n° 323408
 */
public class ViewMenu extends JMenu {

    public ViewMenu () {

        super("Consultas");

        JMenuItem viewBooks = new JMenuItem("Consultar libros");
        viewBooks.addActionListener(e -> new ViewBooksWindow());

        JMenuItem viewSales = new JMenuItem("Consultar ventas");
        viewSales.addActionListener(e -> new ViewSalesWindow());

        this.add(viewBooks);
        this.add(viewSales);
    }
}
