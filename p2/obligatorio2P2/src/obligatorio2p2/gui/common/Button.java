package obligatorio2p2.gui.common;

import javax.swing.*;
import java.awt.*;


/**
 * @author Agustin Ferres - n° 323408
 */
public class Button extends JPanel {

    private JButton button;

    public Button ( String text, Dimension size ) {

        super();

        this.button = new JButton(text);
        button.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        button.setPreferredSize(size);

        this.add(button);
    }

    public JButton getButton () {

        return button;
    }

}
