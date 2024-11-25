package obligatorio2p2.gui.window;

import obligatorio2p2.controller.GenreController;
import obligatorio2p2.gui.common.RegisterPanel;
import obligatorio2p2.gui.common.Window;
import obligatorio2p2.model.Database;
import obligatorio2p2.model.Genre;
import obligatorio2p2.types.ObserverType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Set;


/**
 * @author Agustin Ferres - n° 323408
 */
public class RegisterGenreWindow extends Window {

    private final GenreController controller;
    private final DefaultTableModel tableModel;

    public RegisterGenreWindow () {

        super("Registrar Género");
        this.controller = new GenreController();
        // Subscribe to the database to get notified when a new editorial is added
        Database.getInstance().addObserver(this);

        tableModel = new DefaultTableModel(new Object[]{"Nombre", "Descripción"}, 0);
        JTable table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(200, 300));
        updateTable();

        JScrollPane tableScrollPane = new JScrollPane(table);

        JPanel mainPanel = new RegisterPanel(
            "Nombre",
            "Descripción",
            new Dimension(300, 200),
            tableScrollPane,
            controller::registerGenre
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

        return List.of(ObserverType.GENRE);
    }

    private void updateTable () {

        Set<Genre> editorials = controller.getGenres();
        tableModel.setRowCount(0);
        for ( Genre editorial : editorials ) {
            tableModel.addRow(new Object[]{editorial.getName(), editorial.getDescription()});
        }
    }

}
