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

public class PayProcedureDialog extends javax.swing.JDialog {

    public static final int RET_CANCEL = 0;
    public static final int RET_OK = 1;
    private int returnStatus = RET_CANCEL;
    private final Database db;

    private javax.swing.JButton okButton, cancelButton;
    private javax.swing.JLabel jLabel1, infoDue, infoPaid, infoTrueDue, infoDate, infoStatus;
    private javax.swing.JComboBox<String> comboRental;
    private HashMap<String, Double> dueMap, paidMap;
    private HashMap<String, Integer> idMap;
    private HashMap<String, String> statusMap;
    private HashMap<String, Date> dateMap;
    private JTextField txtPay;

    public PayProcedureDialog(java.awt.Frame parent, Database db) {
        super(parent, true);
        this.db = db;
        this.idMap = new HashMap<>();
        this.dueMap = new HashMap<>();
        this.paidMap = new HashMap<>();
        this.statusMap = new HashMap<>();
        this.dateMap = new HashMap<>();
        infoDue = new JLabel("Debido inicial:");
        infoPaid = new JLabel("Pagado:");
        infoTrueDue = new JLabel("Debido actual:");
        infoDate = new JLabel("Fecha de limite:");
        infoStatus = new JLabel("Estado de limite:");
        this.txtPay = new JTextField(10);
        initComponents();
        try {
            ResultSet rs = db.query("SELECT r.rental_id, re.full_name, b.brand, b.model, b.color, r.booked_end_date_time, r.rental_payment_due, r.rental_payment_made, r.payment_status_code FROM Rentals r JOIN Renters re ON r.renter_id = re.renter_id JOIN Bicycles b ON r.bicycle_id = b.bicycle_id WHERE r.payment_status_code IN ('PEND', 'OVRD') ORDER BY r.rental_id");
            while (rs.next()) {
                String text = rs.getString(2) + " - " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5);
                comboRental.addItem(text);
                idMap.put(text, Integer.parseInt(rs.getString(1)));
                dateMap.put(text, rs.getDate(6));
                dueMap.put(text, rs.getDouble(7));
                paidMap.put(text, rs.getDouble(8));
                statusMap.put(text, rs.getString(9));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

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
        setTitle("Pagar Rentas");
        setResizable(false);

        okButton = new JButton("Pagar");
        okButton.addActionListener(e -> okButtonActionPerformed());

        cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> cancelButtonActionPerformed());

        jLabel1 = new JLabel("Seleccione renta para pagar:");
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
        panel.add(infoDue, gbc);
        gbc.gridy++;
        panel.add(infoPaid, gbc);
        gbc.gridy++;
        panel.add(infoTrueDue, gbc);
        gbc.gridy++;
        panel.add(infoDate, gbc);
        gbc.gridy++;
        panel.add(infoStatus, gbc);

        gbc.gridy++;
        panel.add(txtPay, gbc);

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
            infoDue.setText("Debido inicial: " + dueMap.get(s));
            infoPaid.setText("Pagado: " + paidMap.get(s));
            infoTrueDue.setText("Debido actual: " + (dueMap.get(s) - paidMap.get(s)));
            infoDate.setText("Fecha limite: " + dateMap.get(s));
            infoStatus.setText("Estado de limite: " + statusMap.get(s));
        }
    }

    private void okButtonActionPerformed() {
        try {
            String item = (String) comboRental.getSelectedItem();
            double pay = Double.parseDouble(txtPay.getText());
            if (item != null && pay > 0) {
                double truePaid = paidMap.get(item) + pay;
                if (truePaid >= dueMap.get(item)){
                    if (truePaid > dueMap.get(item)){
                        JOptionPane.showMessageDialog(this, "Pago excedente, el cambio es: " + (truePaid - dueMap.get(item)));
                    }
                    truePaid = dueMap.get(item);
                    int result = db.update(String.format("UPDATE rentals SET payment_status_code = 'PAID' WHERE rental_id = " + idMap.get(item)));
                    if (result > 0) {
                        JOptionPane.showMessageDialog(this, "renta pagada completamente");
                        comboRental.removeItem(item); // Remover del combo
                    } else {
                        JOptionPane.showMessageDialog(this, "No se pudo pagar la renta.");
                    }
                }
                int result = db.update(String.format("UPDATE rentals SET rental_payment_made = " + truePaid + " WHERE rental_id = " + idMap.get(item)));
                if (result > 0) JOptionPane.showMessageDialog(this, "renta pagada exitosamente");
                else JOptionPane.showMessageDialog(this, "No se pudo pagar la renta.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al pagar la renta: " + ex.getMessage());
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Pago ingresado no es un numero valido: " + e.getMessage());
        }
    }

    private void cancelButtonActionPerformed() {
        returnStatus = RET_CANCEL;
        setVisible(false);
        dispose();
    }
}
