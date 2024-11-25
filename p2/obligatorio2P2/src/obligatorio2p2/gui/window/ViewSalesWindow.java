package obligatorio2p2.gui.window;

import obligatorio2p2.controller.BookController;
import obligatorio2p2.controller.SaleController;
import obligatorio2p2.dto.BookSaleDTO;
import obligatorio2p2.gui.common.Button;
import obligatorio2p2.gui.common.Input;
import obligatorio2p2.gui.common.Window;
import obligatorio2p2.model.Author;
import obligatorio2p2.model.Book;
import obligatorio2p2.types.ObserverType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;


public class ViewSalesWindow extends Window {

    private final SaleController controller;
    private final BookController bookController;

    private JPanel bookPanel;

    private JLabel bookLabel;
    private JLabel totalSold;
    private JLabel total;
    private JLabel totalGain;

    private JTextField isbnField;
    private JList<Book> bookList;
    private JDialog dialog;
    private DefaultTableModel model;

    public ViewSalesWindow () {

        super("Consultar ventas");
        this.controller = new SaleController();
        this.bookController = new BookController();

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(this.getSearchPanel());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(this.getDataPanel());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(this.getTotalsPanel());

        this.setContentPane(mainPanel);
        this.setPreferredSize(new Dimension(600, 400));
        this.pack();
        this.setLocationRelativeTo(null);
    }

    @Override
    public void update () {
        updateBookList();
    }

    @Override
    public List<ObserverType> getOTypes () {

        return List.of(ObserverType.BOOK);
    }

    private JPanel getSearchPanel () {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        Input isbnInput = new Input("Isbn", new Dimension(200, 30));
        isbnField = isbnInput.getTextField();

        Button moreButton = new Button("...", new Dimension(30, 30));
        moreButton.getButton().addActionListener(e -> onMore());

        Button searchButton = new Button("Consultar", new Dimension(100, 30));
        searchButton.getButton().addActionListener(e -> onSearch());

        Button exportButton = new Button("Exportar", new Dimension(100, 30));
        exportButton.getButton().addActionListener(e -> onExport());

        searchPanel.add(isbnInput);
        searchPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        searchPanel.add(moreButton);
        searchPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        searchPanel.add(searchButton);
        searchPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        searchPanel.add(exportButton);

        return searchPanel;
    }

    private JPanel getDataPanel () {
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        bookLabel = new JLabel();
        bookLabel.setForeground(Color.BLUE);
        labelPanel.add(bookLabel);

        model = new DefaultTableModel(new Object[]{"Fecha", "Cliente", "Factura", "Cantidad", "Precio", "Importe"}, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        dataPanel.add(labelPanel);
        dataPanel.add(scrollPane);

        return dataPanel;
    }

    private JPanel getTotalsPanel () {
        JPanel totalsPanel = new JPanel();
        totalsPanel.setLayout(new GridLayout(2, 3));

        totalSold = new JLabel();
        total = new JLabel();
        totalGain = new JLabel();

        // set font to blue
        totalSold.setForeground(Color.BLUE);
        total.setForeground(Color.BLUE);
        totalGain.setForeground(Color.BLUE);

        totalsPanel.add(new JLabel("Ejemplares vendidos"));
        totalsPanel.add(new JLabel("Total recaudado"));
        totalsPanel.add(new JLabel("Total ganancia"));
        totalsPanel.add(totalSold);
        totalsPanel.add(total);
        totalsPanel.add(totalGain);

        return totalsPanel;
    }

    private void onMore () {
        dialog = new JDialog(this, "Select Book", true);
        dialog.setSize(300, 400);
        dialog.setLocationRelativeTo(this);

        bookList = new JList<>();
        bookList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookList.addListSelectionListener(e -> onSelect());
        updateBookList();
        JScrollPane scrollPane = new JScrollPane(bookList);



        dialog.add(scrollPane);
        dialog.setVisible(true);
    }

    private void onSelect () {
        Book book = bookList.getSelectedValue();
        if ( book == null ) {
            return;
        }

        isbnField.setText(book.getIsbn());
        dialog.dispose();
    }

    private void onSearch () {
        if ( isbnField.getText().isEmpty() ) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un ISBN", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        bookLabel.setText(bookController.getBook(isbnField.getText()).getTitle());
        updateTable();
    }

    private void onExport() {
        List<BookSaleDTO> sales = controller.getSalesByISBN(isbnField.getText());

        if ( sales.isEmpty() ) {
            JOptionPane.showMessageDialog(this, "No hay ventas para exportar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try ( FileWriter writer = new FileWriter("ventas.csv")) {
            writer.append("Fecha;Cliente;Factura;Cantidad;Precio;Importe\n");
            for (BookSaleDTO sale : sales) {
                writer.append(sale.getDate().toString()).append(';')
                      .append(sale.getClient()).append(';')
                      .append(sale.getId().toString()).append(';')
                      .append(sale.getQuantity().toString()).append(';')
                      .append(sale.getPrice().toString()).append(';')
                      .append(sale.getTotal().toString()).append('\n');
            }
            JOptionPane.showMessageDialog(this, "Exportación exitosa", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch ( IOException e) {
            JOptionPane.showMessageDialog(this, "Error al exportar los datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable () {
        List<BookSaleDTO> sales = controller.getSalesByISBN(isbnField.getText());
        model.setRowCount(0);
        for ( BookSaleDTO sale : sales ) {
            model.addRow(new Object[]{
                sale.getDate(),
                sale.getClient(),
                sale.getId(),
                sale.getQuantity(),
                sale.getPrice(),
                sale.getTotal()
            });
        }

        totalSold.setText(sales.stream().map(BookSaleDTO::getQuantity).reduce(0, Integer::sum).toString());
        total.setText(sales.stream().map(BookSaleDTO::getTotal).reduce(0.0, Double::sum).toString());
        totalGain.setText(sales.stream().map(BookSaleDTO::getWinnings).reduce(0.0, Double::sum).toString());
    }

    private void updateBookList () {
        Set<Book> books = bookController.getBooks();
        bookList.setListData(books.toArray(new Book[0]));
    }
}
