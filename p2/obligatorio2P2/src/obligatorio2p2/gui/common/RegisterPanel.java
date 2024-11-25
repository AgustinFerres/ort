package obligatorio2p2.gui.common;

import obligatorio2p2.types.RegisterFunction;

import javax.swing.*;
import java.awt.*;


public class RegisterPanel extends JPanel {

    public RegisterPanel (
        String firstLabel,
        String secondLabel,
        Dimension leftPanelSize,
        JScrollPane table,
        RegisterFunction onRegister
    ) {

        super();
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setLayout(new GridLayout(1, 2, 10, 10));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 1, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setPreferredSize(leftPanelSize);

        Input firstInput = new Input(firstLabel, new Dimension(leftPanelSize.width - 100, 30));
        Input secondInput = new Input(secondLabel, new Dimension(leftPanelSize.width - 100, 30));
        Button registerButton = this.getRegisterButton(
            firstInput.getTextField(),
            secondInput.getTextField(),
            onRegister
        );

        inputPanel.add(firstInput);
        inputPanel.add(secondInput);
        inputPanel.add(registerButton);

        this.add(inputPanel, BorderLayout.NORTH);
        this.add(table, BorderLayout.CENTER);
    }

    // Generalized register button, to be used in common and simple register panels
    private Button getRegisterButton (
        JTextField firstField,
        JTextField secondField,
        RegisterFunction onRegister
    ) {

        Button registerButton = new Button("Registrar", new Dimension(200, 30));
        registerButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        registerButton.getButton().addActionListener(e -> {
            String firstFieldText = firstField.getText();
            String secondFieldText = secondField.getText();

            try {
                if ( firstFieldText.isEmpty() || secondFieldText.isEmpty() ) {
                    throw new IllegalArgumentException("Todos los campos son obligatorios");
                }
                onRegister.register(firstFieldText, secondFieldText);
            } catch ( IllegalArgumentException ex ) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return registerButton;
    }
}
