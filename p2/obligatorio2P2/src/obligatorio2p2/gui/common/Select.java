package obligatorio2p2.gui.common;

import javax.swing.*;
import java.awt.*;


/**
 * @author Agustin Ferres - n° 323408
 */
public class Select<T> extends JPanel {

    public Select (
        String labelText,
        JList<T> list,
        Dimension size,
        int gap
    ) {

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(0, gap, 0, gap));

        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(size.width, 30));
        label.setHorizontalAlignment(SwingConstants.LEFT);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(size.width, size.height - 30));

        this.setPreferredSize(size);
        this.add(label, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

}
