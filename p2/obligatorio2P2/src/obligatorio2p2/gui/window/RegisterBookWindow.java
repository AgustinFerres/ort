package obligatorio2p2.gui.window;

import obligatorio2p2.controller.AuthorController;
import obligatorio2p2.controller.BookController;
import obligatorio2p2.controller.EditorialController;
import obligatorio2p2.controller.GenreController;
import obligatorio2p2.gui.common.Button;
import obligatorio2p2.gui.common.Input;
import obligatorio2p2.gui.common.Select;
import obligatorio2p2.gui.common.Window;
import obligatorio2p2.model.Author;
import obligatorio2p2.model.Database;
import obligatorio2p2.model.Editorial;
import obligatorio2p2.model.Genre;
import obligatorio2p2.types.ObserverType;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Agustin Ferres - n° 323408
 */
public class RegisterBookWindow extends Window {

    private final BookController controller;
    private final EditorialController editorialController;
    private final GenreController genreController;
    private final AuthorController authorController;

    private JList<Editorial> editorialList;
    private JList<Genre> genreList;
    private JList<Author> authorList;
    private JTextField isbnField;
    private JTextField titleField;
    private JTextField stockField;
    private JTextField costField;
    private JTextField priceField;
    private JLabel imageLabel;
    private File coverFile;

    public RegisterBookWindow () {

        super("Registrar Libro");
        this.controller = new BookController();
        this.editorialController = new EditorialController();
        this.genreController = new GenreController();
        this.authorController = new AuthorController();

        // Subscribe to the database to get notified when a new editorial is added
        Database.getInstance().addObserver(this);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setPreferredSize(new Dimension(800, 600));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(1, 2));
        inputPanel.setPreferredSize(new Dimension(800, 500));

        Button registerButton = new Button("Registrar", new Dimension(200, 30));
        registerButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        registerButton.getButton().addActionListener(e -> onRegister());

        inputPanel.add(this.getLeftPanel());
        inputPanel.add(this.getRightPanel());

        mainPanel.add(inputPanel);
        mainPanel.add(registerButton);

        this.setContentPane(mainPanel);
        this.setPreferredSize(new Dimension(800, 600));
        this.pack();
        this.setLocationRelativeTo(null);
    }

    @Override
    public void update () {

        updateEditorials();
        updateGenres();
        updateAuthors();
    }

    @Override
    public List<ObserverType> getOTypes () {

        return List.of(ObserverType.EDITORIAL, ObserverType.GENRE, ObserverType.AUTHOR);
    }

    private JPanel getLeftPanel () {

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(350, 500));

        editorialList = new JList<>();
        editorialList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        updateEditorials();

        genreList = new JList<>();
        genreList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        genreList.addListSelectionListener(e -> updateAuthors());
        updateGenres();

        authorList = new JList<>();
        authorList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        updateAuthors();

        Select<Editorial> editorialPanel = new Select<>("Editorial", editorialList, new Dimension(400, 75), 40);
        Select<Genre> genrePanel = new Select<>("Género", genreList, new Dimension(400, 75), 40);
        Select<Author> authorPanel = new Select<>("Autores", authorList, new Dimension(400, 150), 40);


        leftPanel.add(editorialPanel);
        leftPanel.add(genrePanel);
        leftPanel.add(authorPanel);

        return leftPanel;
    }

    private JPanel getRightPanel () {

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setPreferredSize(new Dimension(350, 500));

        Input isbnInput = new Input("ISBN", new Dimension(300, 30));
        isbnField = isbnInput.getTextField();

        Input titleInput = new Input("Título", new Dimension(300, 30));
        titleField = titleInput.getTextField();

        Input stockInput = new Input("Stock", new Dimension(300, 30));
        stockField = stockInput.getTextField();

        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new GridLayout(1, 2));
        pricePanel.setPreferredSize(new Dimension(300, 30));

        Input costInput = new Input("Costo", new Dimension(150, 30));
        costField = costInput.getTextField();
        pricePanel.add(costInput);

        Input priceInput = new Input("Precio", new Dimension(150, 30));
        priceField = priceInput.getTextField();
        pricePanel.add(priceInput);

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
        imagePanel.setPreferredSize(new Dimension(300, 180));

        Button selectImageButton = new Button("Seleccionar Imagen", new Dimension(150, 30));
        selectImageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectImageButton.getButton().addActionListener(e -> selectImage());

        imageLabel = new JLabel("Sin foto");
        imageLabel.setSize(new Dimension(150, 150));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        imagePanel.add(imageLabel);
        imagePanel.add(Box.createVerticalStrut(10));
        imagePanel.add(selectImageButton);

        rightPanel.add(isbnInput);
        rightPanel.add(titleInput);
        rightPanel.add(stockInput);
        rightPanel.add(pricePanel);
        rightPanel.add(imagePanel);

        return rightPanel;
    }

    private void selectImage () {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png"));
        int result = fileChooser.showOpenDialog(this);
        if ( result == JFileChooser.APPROVE_OPTION ) {
            coverFile = fileChooser.getSelectedFile();
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(coverFile.getAbsolutePath()).getImage().getScaledInstance(
                150,
                150,
                Image.SCALE_SMOOTH
            ));
            imageLabel.setIcon(imageIcon);
            imageLabel.setText("");
        }
    }

    private void onRegister () {

        // Fields
        Editorial editorial = editorialList.getSelectedValue();
        Genre genre = genreList.getSelectedValue();
        Author author = authorList.getSelectedValue();
        String isbn = isbnField.getText();
        String title = titleField.getText();
        int stock;
        double cost;
        double price;

        // Validations
        if ( editorial == null || genre == null || author == null ) {
            JOptionPane.showMessageDialog(
                this,
                "Debe seleccionar una editorial, género y autor",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if ( isbn.isEmpty() || title.isEmpty() ) {
            JOptionPane.showMessageDialog(
                this,
                "ISBN y título son campos obligatorios",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            stock = Integer.parseInt(stockField.getText());
            cost = Double.parseDouble(costField.getText());
            price = Double.parseDouble(priceField.getText());
        } catch ( NumberFormatException e ) {
            JOptionPane.showMessageDialog(
                this,
                "Stock, costo y/o precio deben ser números",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            controller.registerBook(editorial, genre, author, isbn, title, cost, price, stock, coverFile);
            clearFields();
            JOptionPane.showMessageDialog(this, "Libro registrado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch ( IllegalArgumentException e ) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields () {

        editorialList.clearSelection();
        genreList.clearSelection();
        authorList.clearSelection();
        isbnField.setText("");
        titleField.setText("");
        stockField.setText("");
        costField.setText("");
        priceField.setText("");
        imageLabel.setIcon(null);
        imageLabel.setText("Sin foto");
    }

    private void updateEditorials () {

        List<Editorial> editorials = new ArrayList<>(editorialController.getEditorials());
        editorialList.setListData(editorials.toArray(new Editorial[0]));
    }

    private void updateGenres () {

        List<Genre> genres = new ArrayList<>(genreController.getGenres());
        genreList.setListData(genres.toArray(new Genre[0]));
    }

    private void updateAuthors () {

        Genre genre = genreList.getSelectedValue();
        if ( genre == null ) {
            authorList.setListData(new Author[0]);
            return;
        }

        List<Author> authors = new ArrayList<>(authorController.getAuthorsByGenre(genre.getName()));
        authorList.setListData(authors.toArray(new Author[0]));
    }
}
