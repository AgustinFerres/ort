package obligatorio2p2.gui.window;

import obligatorio2p2.controller.AuthorController;
import obligatorio2p2.controller.GenreController;
import obligatorio2p2.gui.common.RegisterPanel;
import obligatorio2p2.gui.common.Select;
import obligatorio2p2.gui.common.Window;
import obligatorio2p2.model.Author;
import obligatorio2p2.model.Database;
import obligatorio2p2.model.Genre;
import obligatorio2p2.types.ObserverType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class RegisterAuthorWindow extends Window {

    private final AuthorController controller;
    private final GenreController genreController;
    private DefaultTableModel tableModel;
    private JList<Genre> genreList;
    private Author author;

    public RegisterAuthorWindow () {

        super("Registrar Autor");
        this.controller = new AuthorController();
        this.genreController = new GenreController();
        // Subscribe to the database to get notified when a new editorial is added
        Database.getInstance().addObserver(this);

        tableModel = new DefaultTableModel(new Object[]{"Nombre", "Nacionalidad"}, 0);
        JTable table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(200, 300));
        updateTable();

        JScrollPane tableScrollPane = new JScrollPane(table);

        JPanel mainPanel = new RegisterPanel(
            "Nombre",
            "Nacionalidad",
            new Dimension(450, 400),
            tableScrollPane,
            this::registerAuthor
        );

        // Initialize genre select
        genreList = new JList<>();
        genreList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        updateGenres();
        Select<Genre> genrePanel = new Select<>("Géneros", genreList, new Dimension(350, 75), 40);

        JPanel inputPanel = (JPanel) mainPanel.getComponent(0);
        GridLayout layout = (GridLayout) inputPanel.getLayout();
        layout.setRows(4);

        inputPanel.add(genrePanel, 2);

        this.setContentPane(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null);

    }

    @Override
    public void update () {

        updateTable();
        updateGenres();
    }

    @Override
    public List<ObserverType> getOTypes () {

        return List.of(ObserverType.AUTHOR, ObserverType.GENRE);
    }

    private void updateTable () {

        Set<Author> authors = controller.getAuthors();
        tableModel.setRowCount(0);
        for ( Author author : authors ) {
            tableModel.addRow(new Object[]{author.getName(), author.getNationality()});
        }
    }

    private void updateGenres () {

        List<Genre> genres = new ArrayList<>(genreController.getGenres());
        genreList.setListData(genres.toArray(new Genre[0]));
    }

    private void registerAuthor (
        String name,
        String nationality
    ) {

        author = new Author(name, nationality);
        List<Genre> selectedGenres = genreList.getSelectedValuesList();

        if ( selectedGenres == null || selectedGenres.isEmpty() ) {
            throw new IllegalArgumentException("Debe seleccionar al menos un género");
        }

        selectedGenres.forEach(author::addGenre);

        controller.registerAuthor(author);

        // Clear fields on success
        genreList.clearSelection();
        author = null;
    }
}
