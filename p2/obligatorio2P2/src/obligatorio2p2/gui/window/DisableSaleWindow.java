package obligatorio2p2.gui.window;

import obligatorio2p2.controller.SaleController;
import obligatorio2p2.gui.common.Button;
import obligatorio2p2.gui.common.Input;
import obligatorio2p2.gui.common.Window;
import obligatorio2p2.model.Book;
import obligatorio2p2.model.Sale;
import obligatorio2p2.types.ObserverType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;


/**
 * @author Agustin Ferres - n° 323408
 */
public class DisableSaleWindow extends Window {

    private final SaleController controller;

    private JTextField idField;
    private JPanel dataPanel;
    private final Button disableButton = new Button("Anular", new Dimension(200, 30));
    private DefaultTableModel saleModel;
    private DefaultTableModel bookModel;

    private Sale sale;

    public DisableSaleWindow () {

        super("Desactivar venta");
        this.controller = new SaleController();

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        Input idInput = new Input("Factura", new Dimension(200, 30));
        idField = idInput.getTextField();

        Button searchButton = new Button("Buscar", new Dimension(100, 30));
        searchButton.getButton().addActionListener(e -> onSearch());

        searchPanel.add(idInput);
        searchPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        searchPanel.add(searchButton);

        this.initializeDataPanel();

        disableButton.getButton().addActionListener(e -> onDisable());
        disableButton.setVisible(false);

        mainPanel.add(searchPanel);
        mainPanel.add(dataPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(disableButton);

        this.setContentPane(mainPanel);
        this.setPreferredSize(new Dimension(600, 400));
        this.pack();
        this.setLocationRelativeTo(null);
    }

    @Override
    public void update () {

    }

    @Override
    public List<ObserverType> getOTypes () {

        return List.of(ObserverType.SALE);
    }

    private void onSearch () {

        try {
            int id = Integer.parseInt(idField.getText());
            sale = controller.getSale(id);
            if ( sale == null ) {
                JOptionPane.showMessageDialog(this, "No se encontró la venta", "Error", JOptionPane.ERROR_MESSAGE);
            }

            saleModel.setRowCount(0);
            bookModel.setRowCount(0);

            String status = sale.isActive() ? "Activa" : "Anulada";
            saleModel.addRow(new Object[]{sale.getId(), sale.getDate(), sale.getClient(), sale.getTotal(), status});

            for ( Map.Entry<Book, Integer> entry : sale.getBooks().entrySet() ) {
                Book book = entry.getKey();
                int quantity = entry.getValue();
                bookModel.addRow(new Object[]{book.getIsbn(), book.getTitle(), quantity, book.getPrice(), quantity * book.getPrice()});
            }

            dataPanel.setVisible(true);
            disableButton.setVisible(sale.isActive());

        } catch ( NumberFormatException e ) {
            JOptionPane.showMessageDialog(this, "La Factura debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void onDisable () {

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro que desea anular la venta?",
            "Confirmación",
            JOptionPane.YES_NO_OPTION
        );

        if ( confirm != JOptionPane.YES_OPTION ) {
            return;
        }

        try {
            controller.disableSale(sale.getId());
            clearFields();
            JOptionPane.showMessageDialog(this, "Venta anulada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } catch ( IllegalArgumentException e ) {
            clearFields();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields () {

        idField.setText("");
        saleModel.setRowCount(0);
        bookModel.setRowCount(0);
        dataPanel.setVisible(false);
    }

    private void initializeDataPanel () {

        dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));

        saleModel = new DefaultTableModel(new Object[]{"ID", "Fecha", "Cliente", "Total", "Status"}, 1);
        JTable saleTable = new JTable(saleModel);
        saleTable.setPreferredScrollableViewportSize(new Dimension(200, 20));

        bookModel = new DefaultTableModel(new Object[]{"ISBN", "Título", "Cantidad", "Precio", "Importe"}, 0);
        JTable bookTable = new JTable(bookModel);
        bookTable.setPreferredScrollableViewportSize(new Dimension(200, 150));

        JPanel salePanel = new JPanel();
        salePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel saleLabel = new JLabel("Venta");
        salePanel.add(saleLabel);

        JPanel bookPanel = new JPanel();
        bookPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel bookLabel = new JLabel("Libros");
        bookPanel.add(bookLabel);

        dataPanel.add(salePanel);
        dataPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        dataPanel.add(new JScrollPane(saleTable));
        dataPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        dataPanel.add(bookPanel);
        dataPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        dataPanel.add(new JScrollPane(bookTable));

        dataPanel.setVisible(false);
    }
}
