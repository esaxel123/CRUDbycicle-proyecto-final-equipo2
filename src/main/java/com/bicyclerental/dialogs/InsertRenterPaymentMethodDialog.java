package main.java.com.bicyclerental.dialogs;

import main.java.com.bicyclerental.Database;
import main.java.com.bicyclerental.MainApp;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class InsertRenterPaymentMethodDialog extends JDialog {

    private final Database db;
    private final HashMap<String, Integer> renterMap;
    private final HashMap<String, Integer> methodMap;
    private final HashMap<String, String> renterNameMap;

    private JComboBox<String> comboRenter, comboMethod, comboVer;
    private JTextField txtExpDate, txtBank, txtOwner;
    private JButton okButton, cancelButton;


    // Constructor válido
    public InsertRenterPaymentMethodDialog(Frame parent, Database db) {
        super(parent, true);
        this.db = db;
        this.renterMap = new HashMap<>();
        this.methodMap = new HashMap<>();
        this.renterNameMap = new HashMap<>();
        initComponents();
        buildCombos();
    }


    // Componentes UI
    private void initComponents() {

        setTitle("Agregar Método de Pago a Cliente");
        setResizable(false);
        setPreferredSize(new Dimension(500, 450));

        Font f = new Font("Tahoma", Font.BOLD, 14);

        JLabel lblRenter = new JLabel("Cliente:");
        lblRenter.setFont(f);

        JLabel lblMethod = new JLabel("Método de Pago:");
        lblMethod.setFont(f);

        JLabel lblOwner = new JLabel("Nombre del Titular:");
        lblOwner.setFont(f);

        JLabel lblBank = new JLabel("Banco:");
        lblBank.setFont(f);

        JLabel lblExpDate = new JLabel("Fecha Exp (YYYY-MM-DD):");
        lblExpDate.setFont(f);

        JLabel lblVer = new JLabel("Método Verificación:");
        lblVer.setFont(f);

        comboRenter = new JComboBox<>();
        comboMethod = new JComboBox<>();
        comboVer = new JComboBox<>();

        comboMethod.addActionListener(e -> disableBankInfo());
        comboRenter.addActionListener(e -> addName());

        txtOwner = new JTextField(20);
        txtBank = new JTextField(20);
        txtExpDate = new JTextField(10);

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
        panel.add(lblRenter, gbc);
        gbc.gridx = 1;
        panel.add(comboRenter, gbc);

        // Método pago
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblMethod, gbc);
        gbc.gridx = 1;
        panel.add(comboMethod, gbc);

        // Titular
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblOwner, gbc);
        gbc.gridx = 1;
        panel.add(txtOwner, gbc);

        // Banco
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblBank, gbc);
        gbc.gridx = 1;
        panel.add(txtBank, gbc);

        // Fecha Exp.
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblExpDate, gbc);
        gbc.gridx = 1;
        panel.add(txtExpDate, gbc);

        // Verificación
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblVer, gbc);
        gbc.gridx = 1;
        panel.add(comboVer, gbc);

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


    // Cargar Combos desde DB
    private void buildCombos() {

        // Métodos de Verificación
        comboVer.removeAllItems();
        String[] ver = {"Manual", "PIN", "Online", "QR", "Receipt", "Biometric"};
        for (String v : ver) comboVer.addItem(v);


        // Métodos de pago
        try {
            comboMethod.removeAllItems();

            String sql = "SELECT payment_methods_code, payment_method_description FROM Ref_Payment_Methods ORDER BY payment_methods_code";
            ResultSet rs = db.query(sql);

            while (rs.next()) {
                String id = rs.getString(1);
                String method = rs.getString(2);
                String display = id + " - " + method;

                comboMethod.addItem(display);
                methodMap.put(display, Integer.parseInt(id));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar métodos: " + ex.getMessage());
        }

        // Clientes
        try {
            comboRenter.removeAllItems();

            String sql = "SELECT renter_id, full_name, phone_number FROM Renters ORDER BY renter_id";
            ResultSet rs = db.query(sql);

            while (rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                String phone = rs.getString(3);

                String display = id + " - " + name + " (" + (phone != null ? phone : "") + ")";

                comboRenter.addItem(display);
                renterMap.put(display, Integer.parseInt(id));
                renterNameMap.put(display, name);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + ex.getMessage());
        }
    }

    // Insertar método de pago
    private void okButtonActionPerformed() {

        try {
            if (comboRenter.getSelectedItem() == null || comboMethod.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Cliente y metodo de pago son obligatorios.");
                return;
            }
            String sql = "INSERT INTO renters_payment_methods (cardholder_name, expiration_date, issuing_bank, verification_method, renter_id, payment_methods_code) VALUES (";

            // cardholder name
            if (!txtOwner.getText().isEmpty()) sql = sql + "'" + txtOwner.getText() + "', ";
            else sql = sql + "NULL, ";

            // expiration date
            if (!txtExpDate.getText().isEmpty()) sql = sql + "'" + txtExpDate.getText().trim() + "', ";
            else sql = sql + "NULL, ";

            // bank
            if (!txtBank.getText().isEmpty()) sql = sql + "'" + txtBank.getText().trim() + "', ";
            else sql = sql + "NULL, ";

            // verification method, renter id & method id
            if (renterMap.get(comboMethod.getSelectedItem()) == null) sql = sql + "NULL, ";
            else {
                if (renterMap.get(comboMethod.getSelectedItem()) == 1) sql = sql + "NULL, ";
                else sql = sql + "'" + comboVer.getSelectedItem() + "', ";
            }
            sql = sql + renterMap.get(comboRenter.getSelectedItem()) + ", " + methodMap.get(comboMethod.getSelectedItem()) + ")";

            int result = db.getConnection().createStatement().executeUpdate(sql);

            if (result > 0) JOptionPane.showMessageDialog(this, "Método de pago agregado correctamente.");
            else JOptionPane.showMessageDialog(this, "Método de pago no se pudo agregar.");
            setVisible(false);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error SQL: " + ex.getMessage());
        }
    }

    // Lógica de UI
    private void disableBankInfo() {
        Integer method = methodMap.get(comboMethod.getSelectedItem());

        if (method == null) return;

        boolean enable = method != 1;

        txtBank.setEditable(enable);
        txtBank.disable();
        txtOwner.setEditable(enable);
        txtOwner.disable();
        txtExpDate.setEditable(enable);
        txtExpDate.disable();

        if (!enable) {
            txtBank.setText("");
            txtOwner.setText("");
            txtExpDate.setText("");
        }
    }

    private void addName() {
        if (comboRenter.getSelectedItem() == null) return;

        Integer method = methodMap.get(comboMethod.getSelectedItem());
        if (method != null && method != 1) {
            txtOwner.setText(renterNameMap.get(comboRenter.getSelectedItem()));
        }
    }
}
