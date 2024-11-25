package obligatorio2p2.gui.common;

import obligatorio2p2.model.Database;
import obligatorio2p2.model.Observer;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public abstract class Window extends JFrame implements Observer {

    public Window ( String title ) {

        super(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600, 300);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Unsubscribe from database when closing the window
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing ( WindowEvent e ) {

                Database.getInstance().removeObserver(Window.this);
            }
        });
    }
}
