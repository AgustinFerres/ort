package obligatorio2p2.gui.window;

import obligatorio2p2.controller.EditorialController;
import obligatorio2p2.gui.common.RegisterPanel;
import obligatorio2p2.gui.common.Window;
import obligatorio2p2.model.Database;
import obligatorio2p2.model.Editorial;
import obligatorio2p2.types.ObserverType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Set;


public class RegisterEditorialWindow extends Window {

    private final EditorialController controller;
    private final DefaultTableModel tableModel;

    public RegisterEditorialWindow () {

        super("Registrar Editorial");
        this.controller = new EditorialController();
        // Subscribe to the database to get notified when a new editorial is added
        Database.getInstance().addObserver(this);

        tableModel = new DefaultTableModel(new Object[]{"Nombre", "País"}, 0);
        JTable table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(200, 300));
        updateTable();

        JScrollPane tableScrollPane = new JScrollPane(table);

        JPanel mainPanel = new RegisterPanel(
            "Nombre",
            "País",
            new Dimension(300, 200),
            tableScrollPane,
            controller::registerEditorial
        );

        this.setContentPane(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    @Override
    public void update () {

        updateTable();
    }

    @Override
    public List<ObserverType> getOTypes () {

        return List.of(ObserverType.EDITORIAL);
    }

    private void updateTable () {

        Set<Editorial> editorials = controller.getEditorials();
        tableModel.setRowCount(0);
        for ( Editorial editorial : editorials ) {
            tableModel.addRow(new Object[]{editorial.getName(), editorial.getCountry()});
        }
    }
}
