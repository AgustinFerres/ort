package obligatorio2p2.gui;


import obligatorio2p2.gui.menu.RegisterMenu;
import obligatorio2p2.gui.menu.SaleMenu;
import obligatorio2p2.gui.menu.ViewMenu;
import obligatorio2p2.model.Database;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Agustin Ferres - n° 323408
 */
public class MainWindow extends JFrame {

    private JMenuBar menuBar = new JMenuBar();
    private List<JMenu> menu = new ArrayList<>();

    public MainWindow () {

        super("Gestión de Librerías - Realizado por: Agustin Ferres - n° 323408");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(false);

        menu.add(new RegisterMenu());
        menu.add(new SaleMenu());
        menu.add(new ViewMenu());

        menu.forEach(menuBar::add);

        this.setJMenuBar(menuBar);


        int option = JOptionPane.showConfirmDialog(
            null,
            "Desea cargar los datos de la ultima sesión?",
            "Cargar datos",
            JOptionPane.YES_NO_OPTION
        );

        // Load data from last session
        if ( option == JOptionPane.YES_OPTION ) {
            try {
                ObjectInputStream in = new ObjectInputStream(Files.newInputStream(Paths.get("data.ser")));
                Database.getInstance().setInstance((Database) in.readObject());
                JOptionPane.showMessageDialog(
                    null,
                    "Datos cargados correctamente",
                    "Datos cargados",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } catch ( Exception e ) {
                JOptionPane.showMessageDialog(
                    null,
                    "No se pudieron cargar los datos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }

        // Save data when closing the window
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing ( WindowEvent e ) {

                try {
                    ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(Paths.get("data.ser")));
                    out.writeObject(Database.getInstance());
                    out.close();
                } catch ( Exception ex ) {
                    throw new RuntimeException("No se pudieron guardar los datos", ex);
                }
            }
        });

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }


}
