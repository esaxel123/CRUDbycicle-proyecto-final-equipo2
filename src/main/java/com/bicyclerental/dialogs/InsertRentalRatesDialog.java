package main.java.com.bicyclerental.dialogs;

import main.java.com.bicyclerental.Database;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class InsertRentalRatesDialog extends JDialog {

    private final Database db;
    private JTextField txtDaily, txtHourly;
    private JButton okButton, cancelButton;


    // Constructor válido
    public InsertRentalRatesDialog(Frame parent, Database db) {
        super(parent, true);
        this.db = db;
        initComponents();
    }


    // Componentes UI
    private void initComponents() {

        setTitle("Agregar Precios de Renta");
        setResizable(false);
        setPreferredSize(new Dimension(260, 220));

        Font f = new Font("Tahoma", Font.BOLD, 14);

        JLabel lblHourly = new JLabel("Pago por hora:");
        lblHourly.setFont(f);

        JLabel lblDaily = new JLabel("Pago por dia:");
        lblDaily.setFont(f);

        txtHourly = new JTextField(6);
        txtDaily = new JTextField(6);

        okButton = new JButton("Agregar");
        okButton.addActionListener(e -> okButtonActionPerformed());

        cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> setVisible(false));

        // PANEL PRINCIPAL
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 7, 7, 7);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Cliente
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblHourly, gbc);
        gbc.gridx = 1;
        panel.add(txtHourly, gbc);

        // Método pago
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblDaily, gbc);
        gbc.gridx = 1;
        panel.add(txtDaily, gbc);

        // Botones
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel bp = new JPanel();
        bp.add(okButton);
        bp.add(cancelButton);
        panel.add(bp, gbc);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(getParent());
    }

    // Insertar precio de renta
    private void okButtonActionPerformed() {

        try {
            if (txtHourly.getText() == null || txtDaily == null) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }
            double daily = Double.parseDouble(txtDaily.getText());
            double hourly = Double.parseDouble(txtHourly.getText());

            String sql = "INSERT INTO rental_rates (Daily_Rate, Hourly_Rate) VALUES (" + daily + ", " + hourly + ")";
            int result = db.getConnection().createStatement().executeUpdate(sql);

            if (result > 0) JOptionPane.showMessageDialog(this, "Método de pago agregado correctamente.");
            else JOptionPane.showMessageDialog(this, "Método de pago no se pudo agregar.");
            setVisible(false);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error SQL: " + ex.getMessage());
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Valor ingresado no es una cantidad de dinero valida.");
        }
    }

}
