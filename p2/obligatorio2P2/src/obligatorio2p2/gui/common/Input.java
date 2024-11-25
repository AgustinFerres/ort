package obligatorio2p2.gui.common;

import javax.swing.*;
import java.awt.*;


/**
 * @author Agustin Ferres - n° 323408
 */
public class Input extends JPanel {

    private JLabel label;
    private JTextField textField;

    public Input (
        String labelText,
        Dimension size
    ) {

        this.setPreferredSize(size);

        label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(size.width / 4, size.height));
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(size.width * 3 / 4, size.height));

        this.add(label);
        this.add(textField);
    }

    public JLabel getLabel () {

        return label;
    }

    public JTextField getTextField () {

        return textField;
    }
}
