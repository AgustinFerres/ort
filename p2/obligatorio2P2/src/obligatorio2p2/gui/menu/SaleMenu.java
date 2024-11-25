package obligatorio2p2.gui.menu;

import obligatorio2p2.gui.window.DisableSaleWindow;
import obligatorio2p2.gui.window.RegisterSaleWindow;

import javax.swing.*;


/**
 * @author Agustin Ferres - n° 323408
 */
public class SaleMenu extends JMenu {

    public SaleMenu () {

        super("Ventas");

        JMenuItem registerSale = new JMenuItem("Registrar venta");
        registerSale.addActionListener(e -> new RegisterSaleWindow());

        JMenuItem disableSale = new JMenuItem("Anular venta");
        disableSale.addActionListener(e -> new DisableSaleWindow());

        this.add(registerSale);
        this.add(disableSale);
    }
}
