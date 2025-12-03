package main.java.com.bicyclerental.dialogs;

import main.java.com.bicyclerental.Database;
import main.java.com.bicyclerental.MainApp;


import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertRenterDialog extends JDialog {
    private final Database db;

    private JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6;
    private JTextField txtFullName, txtAddress, txtPhone, txtEmail, txtDateOfBirth;
    private JTextField txtRentalHistory, txtCustomerRating;
    private JButton okButton, cancelButton;

    public InsertRenterDialog(Frame parent, Database db) {
        super(parent, true);
        this.db = db;
        initComponents();
    }

    public InsertRenterDialog(MainApp parent, Database db, Database db1) {
        this.db = db1;
    }

    private void initComponents() {
        setTitle("Agregar Cliente");
        setResizable(false);

        // Etiquetas

        jLabel1 = new JLabel("Nombre Completo:");
        jLabel1.setFont(new Font("Tahoma", Font.BOLD, 14));

        jLabel2 = new JLabel("Dirección:");
        jLabel2.setFont(new Font("Tahoma", Font.BOLD, 14));

        jLabel3 = new JLabel("Teléfono:");
        jLabel3.setFont(new Font("Tahoma", Font.BOLD, 14));

        jLabel4 = new JLabel("Email:");
        jLabel4.setFont(new Font("Tahoma", Font.BOLD, 14));

        jLabel5 = new JLabel("Fecha Nacimiento (YYYY-MM-DD):");
        jLabel5.setFont(new Font("Tahoma", Font.BOLD, 14));

        jLabel6 = new JLabel("Historial Rentas:");
        jLabel6.setFont(new Font("Tahoma", Font.BOLD, 14));

        JLabel jLabel7 = new JLabel("Calificación (1-5):");
        jLabel7.setFont(new Font("Tahoma", Font.BOLD, 14));

        // Campos de texto
        txtFullName = new JTextField(30);
        txtAddress = new JTextField(30);
        txtPhone = new JTextField(15);
        txtEmail = new JTextField(25);
        txtDateOfBirth = new JTextField(10);
        txtDateOfBirth.setText("1990-01-01");
        txtRentalHistory = new JTextField(5);
        txtRentalHistory.setText("0");
        txtCustomerRating = new JTextField(5);
        txtCustomerRating.setText("5");

        // Botones
        okButton = new JButton("Agregar");
        okButton.addActionListener(e -> okButtonActionPerformed());

        cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> cancelButtonActionPerformed());

        // Layout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;


        // Fila 1
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(jLabel1, gbc);
        gbc.gridx = 1;
        panel.add(txtFullName, gbc);

        // Fila 2
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(jLabel2, gbc);
        gbc.gridx = 1;
        panel.add(txtAddress, gbc);

        // Fila 3
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(jLabel3, gbc);
        gbc.gridx = 1;
        panel.add(txtPhone, gbc);

        // Fila 4
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(jLabel4, gbc);
        gbc.gridx = 1;
        panel.add(txtEmail, gbc);

        // Fila 5
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(jLabel5, gbc);
        gbc.gridx = 1;
        panel.add(txtDateOfBirth, gbc);

        // Fila 6
        gbc.gridx = 0; gbc.gridy = 6;
        panel.add(jLabel6, gbc);
        gbc.gridx = 1;
        panel.add(txtRentalHistory, gbc);

        // Fila 7
        gbc.gridx = 0; gbc.gridy = 7;
        panel.add(jLabel7, gbc);
        gbc.gridx = 1;
        panel.add(txtCustomerRating, gbc);

        // Fila 8 - Botones
        gbc.gridx = 0; gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(getParent());
    }

    private void okButtonActionPerformed() {
        try {
            String fullName = txtFullName.getText().trim();
            String address = txtAddress.getText().trim();
            String phone = txtPhone.getText().trim();
            String email = txtEmail.getText().trim();
            String dateOfBirth = txtDateOfBirth.getText().trim();
            String rentalHistory = txtRentalHistory.getText().trim();
            String customerRating = txtCustomerRating.getText().trim();

            // Validaciones
            if (fullName.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre y Teléfono son obligatorios");
                return;
            }

            if (!rentalHistory.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Historial de rentas debe ser un número");
                return;
            }

            if (!customerRating.matches("[1-5]")) {
                JOptionPane.showMessageDialog(this, "Calificación debe ser entre 1 y 5");
                return;
            }

            // Construir SQL
            String sql = "INSERT INTO Renters " +
                    "(full_name, address, phone_number, email_address, date_of_birth, " +
                    "rental_history, customer_rating, registration_date_time) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_DATE)";

            PreparedStatement ps = db.getConnection().prepareStatement(sql);

            ps.setString(1, fullName);

            // address puede ser null
            if (address.isEmpty()) {
                ps.setNull(2, java.sql.Types.VARCHAR);
            } else {
                ps.setString(2, address);
            }

            ps.setString(3, phone);

            // email puede ser null
            if (email.isEmpty()) {
                ps.setNull(4, java.sql.Types.VARCHAR);
            } else {
                ps.setString(4, email);
            }

            // fecha de nacimiento
            ps.setDate(5, java.sql.Date.valueOf(dateOfBirth));

            ps.setInt(6, Integer.parseInt(rentalHistory));
            ps.setInt(7, Integer.parseInt(customerRating));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Cliente agregado exitosamente");
            setVisible(false);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar cliente: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void cancelButtonActionPerformed() {
        setVisible(false);
    }
}