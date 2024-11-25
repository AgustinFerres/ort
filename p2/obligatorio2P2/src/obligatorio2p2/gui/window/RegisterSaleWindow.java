package obligatorio2p2.gui.window;

import obligatorio2p2.controller.BookController;
import obligatorio2p2.controller.SaleController;
import obligatorio2p2.dto.BookDTO;
import obligatorio2p2.gui.common.Button;
import obligatorio2p2.gui.common.Input;
import obligatorio2p2.gui.common.Select;
import obligatorio2p2.gui.common.Window;
import obligatorio2p2.model.Book;
import obligatorio2p2.model.Sale;
import obligatorio2p2.types.ObserverType;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author Agustin Ferres - n° 323408
 */
public class RegisterSaleWindow extends Window {

    private final SaleController controller;
    private final BookController bookController;

    private int id;
    private JTextField dateField;
    private JTextField clientField;
    private JList<Book> bookList;
    private JList<BookDTO> selectedBooks;
    private JLabel totalLabel;

    private Sale sale;

    public RegisterSaleWindow () {

        super("Venta de Libros");
        this.controller = new SaleController();
        this.bookController = new BookController();
        Dimension windowSize = new Dimension(800, 400);
        updateId();
        sale = new Sale(new Date(), "", id);
        totalLabel = new JLabel("Total: $ " + sale.getTotal());

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setPreferredSize(windowSize);

        mainPanel.add(this.getLeftPanel());
        mainPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        mainPanel.add(this.getCenterPanel());
        mainPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        mainPanel.add(this.getRightPanel());

        this.setContentPane(mainPanel);
        this.setPreferredSize(windowSize);
        this.pack();
        this.setLocationRelativeTo(null);
    }


    @Override
    public void update () {

        updateId();
        updateBookList();
    }

    @Override
    public List<ObserverType> getOTypes () {

        return List.of(ObserverType.BOOK, ObserverType.SALE);
    }

    private JPanel getLeftPanel () {

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(250, 600));

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel idLabel = new JLabel("Factura: " + id);
        labelPanel.add(idLabel);

        Input dateInput = new Input("Fecha", new Dimension(250, 30));
        dateInput.setLayout(new FlowLayout(FlowLayout.LEFT));
        dateField = dateInput.getTextField();

        bookList = new JList<>();
        bookList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        updateBookList();

        Select<Book> bookSelect = new Select<>(
            "Libros",
            bookList,
            new Dimension(250, 150),
            5
        );

        Button cancelButton = new Button("Cancelar", new Dimension(200, 30));
        cancelButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        cancelButton.getButton().addActionListener(e -> this.dispose());

        leftPanel.add(labelPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(dateInput);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(bookSelect);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(cancelButton);

        return leftPanel;
    }

    private JPanel getCenterPanel () {

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setPreferredSize(new Dimension(50, 600));

        Button addButton = new Button("=>", new Dimension(50, 40));
        addButton.getButton().addActionListener(e -> onAdd());
        Button removeButton = new Button("<=", new Dimension(50, 40));
        removeButton.getButton().addActionListener(e -> onRemove());

        centerPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        centerPanel.add(addButton);
        centerPanel.add(removeButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 100)));

        return centerPanel;
    }

    private JPanel getRightPanel () {

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setPreferredSize(new Dimension(300, 600));

        Input clientInput = new Input("Cliente", new Dimension(300, 30));
        clientInput.setLayout(new FlowLayout(FlowLayout.LEFT));
        clientField = clientInput.getTextField();

        selectedBooks = new JList<>();
        selectedBooks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        updateSelectedBooks();

        Select<BookDTO> selectedBooksSelect = new Select<>(
            "Venta",
            selectedBooks,
            new Dimension(300, 150),
            5
        );

        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        totalPanel.add(totalLabel);

        Button registerButton = new Button("Registrar", new Dimension(200, 30));
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.getButton().addActionListener(e -> onRegister());

        rightPanel.add(Box.createRigidArea(new Dimension(0, 35)));
        rightPanel.add(clientInput);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(selectedBooksSelect);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(totalPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(registerButton);

        return rightPanel;
    }

    private void onAdd () {

        Book book = bookList.getSelectedValue();

        if ( book == null ) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un libro", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        sale.addBook(book);
        updateSelectedBooks();
    }

    private void onRemove () {

        BookDTO bookDTO = selectedBooks.getSelectedValue();

        if ( bookDTO == null ) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un libro", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        sale.removeBook(bookController.getBook(bookDTO.getIsbn()));
        updateSelectedBooks();
    }

    private void onRegister () {

        Date date;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            date = dateFormat.parse(dateField.getText());
        } catch ( ParseException e ) {
            JOptionPane.showMessageDialog(null, "La fecha ingresada no es válida", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ( sale.getBooks().isEmpty() ) {
            JOptionPane.showMessageDialog(
                null,
                "Debe seleccionar al menos un libro",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        sale.setDate(date);
        sale.setClient(clientField.getText());

        try {
            controller.registerSale(sale);
            clearFields();
            JOptionPane.showMessageDialog(null, "Venta registrada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch ( IllegalArgumentException e ) {
            updateSelectedBooks();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields () {

        dateField.setText("");
        clientField.setText("");
        bookList.clearSelection();
        selectedBooks.setListData(new BookDTO[0]);
        sale = new Sale(new Date(), "", id);
    }

    private void updateId () {

        id = controller.getNewId();
    }

    private void updateBookList () {

        List<Book> books = new ArrayList<>(bookController.getBooks());
        bookList.setListData(books.toArray(new Book[0]));
    }

    private void updateSelectedBooks () {

        if ( sale.getBooks() != null ) {
            Map<Book, Integer> books = sale.getBooks();
            selectedBooks.setListData(books.entrySet().stream().map(e -> BookDTO.of(e.getKey(), e.getValue())).toArray(
                BookDTO[]::new));
        } else {
            selectedBooks.setListData(new BookDTO[0]);
        }

        totalLabel.setText("Total: $ " + sale.getTotal());
    }
}
