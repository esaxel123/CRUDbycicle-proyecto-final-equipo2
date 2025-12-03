package main.java.com.bicyclerental.dialogs;

import main.java.com.bicyclerental.Database;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;

public class InsertRentalDialog extends JDialog {
    private final Database db;
    private final HashMap<String, Integer> bicycleMap, renterMap, paymentMethodMap, rrMap;

    private JComboBox<String> comboBicycle, comboRenter, comboPaymentMethod, comboPaymentStatus, comboAllDay, comboRR, comboResMethod;
    private JTextField txtStartDate, txtEndDate, txtPaymentDue, txtEmployee;
    private JButton okButton, cancelButton;

    public InsertRentalDialog(Frame parent, Database db) {
        super(parent, true);
        this.db = db;
        this.bicycleMap = new HashMap<>();
        this.renterMap = new HashMap<>();
        this.paymentMethodMap = new HashMap<>();
        this.rrMap = new HashMap<>();
        initComponents();
        buildCombos();
    }

    private void buildCombos() {
        // Cargar bicicletas disponibles
        try {
            String sqlBicycles = "SELECT b.bicycle_id, b.brand, b.model " +
                    "FROM Bicycles b " +
                    "JOIN Bicycles_in_Shops bis ON b.bicycle_id = bis.bicycle_id " +
                    "WHERE bis.current_status = 'Available' " +
                    "ORDER BY b.brand, b.model";
            ResultSet rs = db.query(sqlBicycles);
            while (rs.next()) {
                String id = rs.getString(1);
                String brand = rs.getString(2);
                String model = rs.getString(3);
                String display = id + " - " + brand + " " + model;
                comboBicycle.addItem(display);
                bicycleMap.put(display, Integer.parseInt(id));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar bicicletas: " + ex.getMessage());
        }

        //Rental rates
        try {
            String sqlRR = "SELECT rental_rates_id, daily_rate, hourly_rate FROM rental_rates";
            ResultSet rs = db.query(sqlRR);
            while (rs.next()) {
                String id = rs.getString(1);
                String display = id + " - por dia: " + rs.getString(2) + ", por hora " + rs.getString(3);
                comboRR.addItem(display);
                rrMap.put(display, Integer.parseInt(id));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar precios de renta: " + ex.getMessage());
        }

        // Cargar clientes
        try {
            String sqlRenters = "SELECT renter_id, full_name, phone_number FROM Renters ORDER BY full_name";
            ResultSet rs = db.query(sqlRenters);
            while (rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                String phone = rs.getString(3);
                String display = id + " - " + name + " (" + phone + ")";
                comboRenter.addItem(display);
                renterMap.put(display, Integer.parseInt(id));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + ex.getMessage());
        }

        // Configurar valores por defecto
        txtStartDate.setText(java.time.LocalDate.now().toString());
        txtEndDate.setText(java.time.LocalDate.now().plusDays(1).toString());
        txtPaymentDue.setText("25.00");
    }

    private void initComponents() {
        setTitle("Agregar Renta");
        setResizable(false);
        setPreferredSize(new Dimension(520, 520));
        setMinimumSize(new Dimension(500, 400));

        // Crear componentes
        Font f = new Font("Tahoma", Font.BOLD, 14);

        JLabel lblBicycle = new JLabel("Bicicleta:");
        lblBicycle.setFont(f);
        comboBicycle = new JComboBox<>();

        JLabel lblRenter = new JLabel("Cliente:");
        lblRenter.setFont(f);
        comboRenter = new JComboBox<>();
        comboRenter.addActionListener(e -> setRenterPaymentMethod());

        JLabel lblRR = new JLabel("Precio de rentas:");
        lblRR.setFont(f);
        comboRR = new JComboBox<>();

        JLabel lblAllDay = new JLabel("Renta Todo el Día:");
        lblAllDay.setFont(f);
        comboAllDay = new JComboBox<>(new String[]{"Y", "N"});

        JLabel lblStartDate = new JLabel("Fecha Inicio (YYYY-MM-DD):");
        lblStartDate.setFont(f);
        txtStartDate = new JTextField(10);

        JLabel lblEndDate = new JLabel("Fecha Fin (YYYY-MM-DD):");
        lblEndDate.setFont(f);
        txtEndDate = new JTextField(10);

        JLabel lblPaymentMethod = new JLabel("Método Pago:");
        lblPaymentMethod.setFont(f);
        comboPaymentMethod = new JComboBox<>();

        JLabel lblPaymentStatus = new JLabel("Estado Pago:");
        lblPaymentStatus.setFont(f);
        comboPaymentStatus = new JComboBox<>(new String[]{"PEND", "PAID"});

        JLabel lblPaymentDue = new JLabel("Monto a Pagar:");
        lblPaymentDue.setFont(f);
        txtPaymentDue = new JTextField(10);

        JLabel lblEmployee = new JLabel("Nombre de empleado encargado:");
        lblEmployee.setFont(f);
        txtEmployee = new JTextField(20);

        JLabel lblResMethod = new JLabel("Manera de reservacion:");
        lblResMethod.setFont(f);
        comboResMethod = new JComboBox<>(new String[]{"In Person", "Online", "Phone"});

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

        int row = 0;

        //Bicicleta
        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(lblBicycle, gbc);
        gbc.gridx = 1;
        panel.add(comboBicycle, gbc);

        //Cliente
        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(lblRenter, gbc);
        gbc.gridx = 1;
        panel.add(comboRenter, gbc);

        //precio de renta
        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(lblRR, gbc);
        gbc.gridx = 1;
        panel.add(comboRR, gbc);

        // Renta por el Día completo
        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(lblAllDay, gbc);
        gbc.gridx = 1;
        panel.add(comboAllDay, gbc);

        // Fecha Inicio
        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(lblStartDate, gbc);
        gbc.gridx = 1;
        panel.add(txtStartDate, gbc);

        // Fecha Fin
        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(lblEndDate, gbc);
        gbc.gridx = 1;
        panel.add(txtEndDate, gbc);

        // Metodo Pago
        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(lblPaymentMethod, gbc);
        gbc.gridx = 1;
        panel.add(comboPaymentMethod, gbc);

        // Estado Pago
        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(lblPaymentStatus, gbc);
        gbc.gridx = 1;
        panel.add(comboPaymentStatus, gbc);

        // Monto a Pagar
        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(lblPaymentDue, gbc);
        gbc.gridx = 1;
        panel.add(txtPaymentDue, gbc);

        // Monto a Pagar
        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(lblEmployee, gbc);
        gbc.gridx = 1;
        panel.add(txtEmployee, gbc);

        // Monto a Pagar
        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(lblResMethod, gbc);
        gbc.gridx = 1;
        panel.add(comboResMethod, gbc);

        // Botones
        gbc.gridx = 0; gbc.gridy = row++;
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
            // Validaciones básicas
            if (comboBicycle.getSelectedItem() == null || comboRenter.getSelectedItem() == null || comboAllDay.getSelectedItem() == null || comboRR.getSelectedItem() == null || txtStartDate.getText().isEmpty() || txtEndDate.getText().isEmpty() || txtPaymentDue.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bicicleta, cliente, precio de renta, fecha de inicio, inicio de tiempo reservado, fin de tiempo reservado, fecha de pago, y si es por todo el dia son obligatorios");
                return;
            }

            // Obtener IDs de los combos
            Integer bicycleId = bicycleMap.get(comboBicycle.getSelectedItem());
            Integer renterId = renterMap.get(comboRenter.getSelectedItem());
            Integer paymentMethodId = paymentMethodMap.get(comboPaymentMethod.getSelectedItem());

            // Construir SQL
            String sql = "INSERT INTO Rentals (" +
                    "bicycle_id, renter_id, renter_payment_method_id, all_day_rental_yn, " +
                    "booked_start_date_time, booked_end_date_time, actual_start_date_time, " +
                    "payment_status_code, rental_payment_due, rental_payment_made, rental_rates_id, employee_in_charge, reservation_method" +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = db.getConnection().prepareStatement(sql);


            ps.setInt(1, bicycleId);
            ps.setInt(2, renterId);

// renter_payment_method_id puede ser null
            if (paymentMethodId != null) {
                ps.setInt(3, paymentMethodId);
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }

            ps.setString(4, comboAllDay.getSelectedItem().toString());
            ps.setDate(5, java.sql.Date.valueOf(txtStartDate.getText().trim()));

            ps.setDate(6, java.sql.Date.valueOf(txtEndDate.getText().trim()));
            ;

// actual_start_date_time = booked_start_date_time por ahora
            ps.setDate(7, java.sql.Date.valueOf(txtStartDate.getText().trim()));


            ps.setString(8, comboPaymentStatus.getSelectedItem().toString());
            ps.setBigDecimal(9, new java.math.BigDecimal(txtPaymentDue.getText().trim()));

            ps.setInt(10, 0); // rental_payment_made = 0

            ps.setInt(11, rrMap.get(comboRR.getSelectedItem()));

            if (txtEmployee.getText().isEmpty()) ps.setString(12, txtEmployee.getText());
            else ps.setNull(12, Types.VARCHAR);

            if(comboResMethod.getSelectedItem() != null) ps.setString(13, (String) comboResMethod.getSelectedItem());
            else ps.setString(13, "In Person");

            ps.executeUpdate();
            System.out.println(ps);

            // Actualizar estado de la bicicleta
            String updateBikeSQL = "UPDATE Bicycles_in_Shops SET current_status = 'Rented' " +
                    "WHERE bicycle_id = " + bicycleId;
            db.update(updateBikeSQL);

            JOptionPane.showMessageDialog(this, "Renta agregada exitosamente");
            setVisible(false);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar renta: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void setRenterPaymentMethod(){
        if (comboRenter.getSelectedItem() != null){
            // Cargar métodos de pago
            try {
                comboPaymentMethod.removeAllItems();
                String sqlPaymentMethods = "SELECT renter_payment_method_id, issuing_bank " +
                        "FROM Renters_Payment_Methods WHERE renter_id = " + renterMap.get(comboRenter.getSelectedItem()) + " ORDER BY renter_payment_method_id";
                ResultSet rs = db.query(sqlPaymentMethods);
                while (rs.next()) {
                    String id = rs.getString(1);
                    String name = rs.getString(2);
                    String display = id + " - " + (name != null ? name : "Efectivo");
                    comboPaymentMethod.addItem(display);
                    paymentMethodMap.put(display, Integer.parseInt(id));
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar métodos de pago: " + ex.getMessage());
            }
        }
    }
    private void cancelButtonActionPerformed() {
        setVisible(false);
    }
}