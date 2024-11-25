package obligatorio2p2.gui.window;

import obligatorio2p2.controller.BookController;
import obligatorio2p2.gui.common.Button;
import obligatorio2p2.gui.common.Input;
import obligatorio2p2.gui.common.Window;
import obligatorio2p2.model.Book;
import obligatorio2p2.types.ObserverType;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;


public class ViewBooksWindow extends Window {

    private final BookController controller;

    private JTextField genreField;
    private JTextField titleField;
    private JTextField authorField;

    private JPanel dataPanel;
    private Set<Book> books;

    public ViewBooksWindow () {

        super("Consultar libros");
        this.controller = new BookController();

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        Input genreInput = new Input("Género", new Dimension(200, 30));
        genreField = genreInput.getTextField();

        Input titleInput = new Input("Título", new Dimension(200, 30));
        titleField = titleInput.getTextField();

        Input authorInput = new Input("Autor", new Dimension(200, 30));
        authorField = authorInput.getTextField();

        Button searchButton = new Button("Buscar", new Dimension(100, 30));
        searchButton.getButton().addActionListener(e -> onSearch());

        this.initializeDataPanel();

        searchPanel.add(genreInput);
        searchPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        searchPanel.add(titleInput);
        searchPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        searchPanel.add(authorInput);
        searchPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        searchPanel.add(searchButton);

        mainPanel.add(searchPanel);
        mainPanel.add(dataPanel);

        this.setContentPane(mainPanel);
        this.setPreferredSize(new Dimension(900, 600));
        this.pack();
        this.setLocationRelativeTo(null);
    }

    @Override
    public void update () {

    }

    @Override
    public List<ObserverType> getOTypes () {

        return List.of(ObserverType.BOOK);
    }

    private void onSearch () {

        if ( genreField.getText().isEmpty() && titleField.getText().isEmpty() && authorField.getText().isEmpty() ) {
            JOptionPane.showMessageDialog(
                this,
                "Debe ingresar al menos un criterio de búsqueda",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        this.clearFields();

        books = controller.getBooks(genreField.getText(), titleField.getText(), authorField.getText());

        if ( books.isEmpty() ) {
            JOptionPane.showMessageDialog(this, "No se encontraron libros", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            for ( Book book : books ) {
                JPanel bookPanel = new JPanel();
                bookPanel.setPreferredSize(new Dimension(150, 150));
                bookPanel.setAlignmentY(Component.TOP_ALIGNMENT);

                JButton bookButton = new JButton();
                bookButton.addActionListener(e -> new BookInformationWindow(book));
                bookButton.setPreferredSize(new Dimension(150, 150));
                bookButton.setBackground(null);
                bookButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                if ( book.getImage() == null ) {
                    bookButton.setText(book.getIsbn());
                } else {
                    bookButton.setIcon(new ImageIcon(new ImageIcon(book.getImage().getAbsolutePath()).getImage().getScaledInstance(
                        150,
                        150,
                        Image.SCALE_SMOOTH
                    )));
                }


                bookPanel.add(bookButton);

                dataPanel.add(bookPanel);
            }
            dataPanel.setVisible(true);
        }

    }

    private void clearFields () {

        genreField.setText("");
        titleField.setText("");
        authorField.setText("");
    }

    private void initializeDataPanel () {

        dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayout(0, 4, 10, 10));
        dataPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        dataPanel.setVisible(false);

    }
}
