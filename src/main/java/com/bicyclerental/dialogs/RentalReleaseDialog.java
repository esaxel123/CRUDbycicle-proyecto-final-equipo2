package main.java.com.bicyclerental.dialogs;

import main.java.com.bicyclerental.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class RentalReleaseDialog extends javax.swing.JDialog {

    public static final int RET_CANCEL = 0;
    public static final int RET_OK = 1;
    private int returnStatus = RET_CANCEL;
    private final Database db;

    private javax.swing.JButton okButton, cancelButton;
    private javax.swing.JLabel jLabel1, infoStartDate, infoEndDate, infoRenter, infoBicycle;
    private javax.swing.JComboBox<String> comboRental;
    private HashMap<String, Integer> idMap;
    private HashMap<String, String> renterMap;
    private HashMap<String, String> bicycleMap;
    private HashMap<String, Date> startDateMap;
    private HashMap<String, Date> endDateMap;

    public RentalReleaseDialog(java.awt.Frame parent, Database db) {
        super(parent, true);
        this.db = db;
        this.idMap = new HashMap<>();
        this.renterMap = new HashMap<>();
        this.bicycleMap = new HashMap<>();
        this.startDateMap = new HashMap<>();
        this.endDateMap = new HashMap<>();

        infoStartDate = new JLabel("Fecha inicio:");
        infoEndDate = new JLabel("Fecha fin programada:");
        infoRenter = new JLabel("Cliente:");
        infoBicycle = new JLabel("Bicicleta:");

        initComponents();
        try {
            // Consulta para obtener rentas activas
            ResultSet rs = db.query("SELECT r.rental_id, re.full_name, b.brand, b.model, b.color, " +
                    "r.booked_start_date_time, r.booked_end_date_time " +
                    "FROM Rentals r " +
                    "JOIN Renters re ON r.renter_id = re.renter_id " +
                    "JOIN Bicycles b ON r.bicycle_id = b.bicycle_id " +
                    "WHERE r.actual_end_date_time IS NULL " +
                    "ORDER BY r.rental_id");

            while (rs.next()) {
                String renterInfo = rs.getString(2);
                String bikeInfo = rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5);
                String text = renterInfo + " - " + bikeInfo;

                comboRental.addItem(text);
                idMap.put(text, rs.getInt(1));
                renterMap.put(text, renterInfo);
                bicycleMap.put(text, bikeInfo);
                startDateMap.put(text, rs.getDate(6));
                endDateMap.put(text, rs.getDate(7));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        // Configurar tecla ESC para cancelar
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                returnStatus = RET_CANCEL;
                setVisible(false);
                dispose();
            }
        });

        comboRental.addActionListener(e -> addInfo());
    }

    private void initComponents() {
        setTitle("Liberar Bicicleta Rentada");
        setResizable(false);

        okButton = new JButton("Liberar");
        okButton.addActionListener(e -> okButtonActionPerformed());

        cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> cancelButtonActionPerformed());

        jLabel1 = new JLabel("Seleccione renta para liberar:");
        jLabel1.setFont(new Font("Tahoma", Font.BOLD, 14));

        comboRental = new JComboBox<>();
        comboRental.setFont(new Font("Tahoma", Font.PLAIN, 12));
        comboRental.setPreferredSize(new Dimension(300, 25));

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(jLabel1, gbc);

        gbc.gridy++;
        panel.add(comboRental, gbc);

        gbc.gridy++;
        panel.add(infoStartDate, gbc);
        gbc.gridy++;
        panel.add(infoEndDate, gbc);
        gbc.gridy++;
        panel.add(infoRenter, gbc);
        gbc.gridy++;
        panel.add(infoBicycle, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(getParent());
    }

    private void addInfo() {
        if(comboRental.getSelectedItem() != null){
            String s = (String) comboRental.getSelectedItem();
            infoStartDate.setText("Fecha inicio: " + startDateMap.get(s));
            infoEndDate.setText("Fecha fin programada: " + endDateMap.get(s));
            infoRenter.setText("Cliente: " + renterMap.get(s));
            infoBicycle.setText("Bicicleta: " + bicycleMap.get(s));
        }
    }

    private void okButtonActionPerformed() {
        try {
            String item = (String) comboRental.getSelectedItem();
            if (item != null) {
                int rentalId = idMap.get(item);

                // 1. Actualizar la renta con la fecha actual de fin
                String updateRentalSQL = "UPDATE rentals SET actual_end_date_time = CURRENT_DATE WHERE rental_id = " + rentalId;
                int rentalUpdate = db.update(updateRentalSQL);

                if (rentalUpdate > 0) {
                    // 2. Obtener el bicycle_id de esta renta
                    ResultSet rs = db.query("SELECT bicycle_id FROM rentals WHERE rental_id = " + rentalId);
                    if (rs.next()) {
                        int bicycleId = rs.getInt("bicycle_id");

                        // 3. Actualizar el estado de la bicicleta a "Available"
                        String updateBikeSQL = "UPDATE bicycles_in_shops SET current_status = 'Available', " +
                                "last_movement_date = CURRENT_DATE WHERE bicycle_id = " + bicycleId;
                        int bikeUpdate = db.update(updateBikeSQL);

                        if (bikeUpdate > 0) {
                            JOptionPane.showMessageDialog(this, "Bicicleta liberada exitosamente");
                            comboRental.removeItem(item); // Remover del combo
                        } else {
                            JOptionPane.showMessageDialog(this, "Error al actualizar estado de la bicicleta");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar la renta");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al liberar la bicicleta: " + ex.getMessage());
        }
    }

    private void cancelButtonActionPerformed() {
        returnStatus = RET_CANCEL;
        setVisible(false);
        dispose();
    }

    public int getReturnStatus() {
        return returnStatus;
    }
}