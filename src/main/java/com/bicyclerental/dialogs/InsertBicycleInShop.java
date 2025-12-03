package main.java.com.bicyclerental.dialogs;

import main.java.com.bicyclerental.Database;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class InsertBicycleInShop extends JDialog {
    private final Database db;
    private final HashMap<String, Integer> bicycleMap;
    private final HashMap<String, Integer> shopMap;
    private final HashMap<String, String> renterNameMap;

    private JComboBox<String> comboBicycle, comboShop, comboAvail;
    private JTextField txtLastDate, txtDateout, txtDateIn, txtInventory;
    private JButton okButton, cancelButton;

    public InsertBicycleInShop(Frame parent, Database db) {
        super(parent, true);
        this.db = db;
        this.bicycleMap = new HashMap<>();
        this.shopMap = new HashMap<>();
        this.renterNameMap = new HashMap<>();
        initComponents();
        buildCombos();
    }

    private void initComponents() {

        setTitle("Agregar Bicicleta en Tienda");
        setResizable(false);
        setPreferredSize(new Dimension(760, 400));

        Font f = new Font("Tahoma", Font.BOLD, 14);

        JLabel lblRenter = new JLabel("Bicicleta:");
        lblRenter.setFont(f);

        JLabel lblMethod = new JLabel("Tienda:");
        lblMethod.setFont(f);

        JLabel lblDateIn = new JLabel("Fecha de entrada (YYYY-MM-DD):");
        lblDateIn.setFont(f);

        JLabel lblDateOut = new JLabel("Fecha de salida (YYYY-MM-DD):");
        lblDateOut.setFont(f);

        JLabel lblInventory = new JLabel("Persona responsable:");
        lblInventory.setFont(f);

        JLabel lblLastDate = new JLabel("Ultima fecha de mantenimiento (YYYY-MM-DD):");
        lblLastDate.setFont(f);

        JLabel lblAvail = new JLabel("Disponibilidad:");
        lblAvail.setFont(f);

        comboBicycle = new JComboBox<>();
        comboShop = new JComboBox<>();
        comboAvail = new JComboBox<>();

        txtDateIn = new JTextField(10);
        txtDateout = new JTextField(20);
        txtLastDate = new JTextField(10);
        txtInventory = new JTextField(20);

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

        // bicicleta
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblRenter, gbc);
        gbc.gridx = 1;
        panel.add(comboBicycle, gbc);

        // tienda
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblMethod, gbc);
        gbc.gridx = 1;
        panel.add(comboShop, gbc);

        // fecha entrada
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblDateIn, gbc);
        gbc.gridx = 1;
        panel.add(txtDateIn, gbc);

        // fecha salida
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblDateOut, gbc);
        gbc.gridx = 1;
        panel.add(txtDateout, gbc);

        // responsable
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblInventory, gbc);
        gbc.gridx = 1;
        panel.add(txtInventory, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblLastDate, gbc);
        gbc.gridx = 1;
        panel.add(txtLastDate, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblAvail, gbc);
        gbc.gridx = 1;
        panel.add(comboAvail, gbc);
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

        // disponibilidad
        comboAvail.removeAllItems();
        String[] ver = {"Available", "Rented", "Maintenance"};
        for (String v : ver) comboAvail.addItem(v);


        // tiendas
        try {
            comboShop.removeAllItems();

            String sql = "SELECT multi_shop_id, location_name, address FROM multi_shop ORDER BY multi_shop_id";
            ResultSet rs = db.query(sql);

            while (rs.next()) {
                String id = rs.getString(1);
                String display = id + " - " + rs.getString(2) + " - " + rs.getString(3);
                comboShop.addItem(display);
                shopMap.put(display, Integer.parseInt(id));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar tiendas: " + ex.getMessage());
        }

        // bicicletas
        try {
            comboBicycle.removeAllItems();

            String sql = "SELECT bicycle_id, brand, model, color FROM bicycles ORDER BY bicycle_id";
            ResultSet rs = db.query(sql);

            while (rs.next()) {
                String id = rs.getString(1);

                String display = id + " - " + rs.getString(2) + " - " + rs.getString(3) + " - " + rs.getString(4);

                comboBicycle.addItem(display);
                bicycleMap.put(display, Integer.parseInt(id));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar bicicletas: " + ex.getMessage());
        }
    }

    // Insertar método de pago
    private void okButtonActionPerformed() {

        try {
            if (comboBicycle.getSelectedItem() == null || comboShop.getSelectedItem() == null || comboAvail.getSelectedItem() == null || txtDateIn.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bicicleta, tienda, fecha de entrada, y disponibilidad son obligatorios.");
                return;
            }

            String sql = "INSERT INTO bicycles_in_shops (DateTime_In, DateTime_Out, Current_Status, Last_Movement_Date, Multi_Shop_ID, Bicycle_ID, Inventory_Responsible_Person) VALUES (";

            sql = sql + "'" + txtDateIn.getText().trim() + "', ";
            if (!txtDateout.getText().isEmpty()) sql = sql + "'" + txtDateout.getText().trim() + "', ";
            else sql = sql + "NULL, ";
            sql = sql + "'" + comboAvail.getSelectedItem() + "', ";
            if (!txtLastDate.getText().isEmpty()) sql = sql + "'" + txtLastDate.getText().trim() + "', ";
            else sql = sql + "NULL, ";

            sql = sql + shopMap.get(comboShop.getSelectedItem()) + ", " + bicycleMap.get(comboBicycle.getSelectedItem()) + ", ";

            if (!txtInventory.getText().isEmpty()) sql = sql + "'" + txtInventory.getText() + "')";
            else sql = sql + "NULL)";

            System.out.println(sql);

            int result = db.getConnection().createStatement().executeUpdate(sql);

            if (result > 0) JOptionPane.showMessageDialog(this, "Bicicleta en tienda agregado correctamente.");
            else JOptionPane.showMessageDialog(this, "Bicicleta en tienda no se pudo agregar.");
            setVisible(false);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error SQL: " + ex.getMessage());
        }
    }
}
