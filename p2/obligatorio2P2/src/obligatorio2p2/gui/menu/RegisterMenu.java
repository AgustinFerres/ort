package obligatorio2p2.gui.menu;

import obligatorio2p2.gui.window.RegisterAuthorWindow;
import obligatorio2p2.gui.window.RegisterBookWindow;
import obligatorio2p2.gui.window.RegisterEditorialWindow;
import obligatorio2p2.gui.window.RegisterGenreWindow;

import javax.swing.*;


/**
 * @author Agustin Ferres - n° 323408
 */
public class RegisterMenu extends JMenu {

    public RegisterMenu () {

        super("Registros");

        JMenuItem registerEditorial = new JMenuItem("Registrar editorial");
        registerEditorial.addActionListener(e -> new RegisterEditorialWindow());

        JMenuItem registerGenre = new JMenuItem("Registrar género");
        registerGenre.addActionListener(e -> new RegisterGenreWindow());

        JMenuItem registerAuthor = new JMenuItem("Registrar autor");
        registerAuthor.addActionListener(e -> new RegisterAuthorWindow());

        JMenuItem registerBook = new JMenuItem("Registrar libro");
        registerBook.addActionListener(e -> new RegisterBookWindow());

        this.add(registerEditorial);
        this.add(registerGenre);
        this.add(registerAuthor);
        this.add(registerBook);
    }
}
