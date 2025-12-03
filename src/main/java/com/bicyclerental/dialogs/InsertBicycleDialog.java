package main.java.com.bicyclerental.dialogs;

import main.java.com.bicyclerental.Database;
import main.java.com.bicyclerental.MainApp;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class InsertBicycleDialog extends JDialog {
    private final Database db;

    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JTextField txtBrand;
    private JTextField txtModel;
    private JTextField txtYear;
    private JComboBox<String> comboType;
    private JTextField txtColor;
    private JTextField txtFrameSize;
    private JComboBox<String> comboStatus;
    private JButton okButton;
    private JButton cancelButton;

    public InsertBicycleDialog(Frame parent, Database db) {
        super(parent, true);
        this.db = db;
        initComponents();
    }

    private void initComponents() {
        setTitle("Agregar Bicicleta");
        setResizable(true);

        jLabel1 = new JLabel();
        jLabel1.setFont(new Font("Tahoma", 1, 14));
        jLabel1.setText("Marca:");

        jLabel2 = new JLabel();
        jLabel2.setFont(new Font("Tahoma", 1, 14));
        jLabel2.setText("Modelo:");

        jLabel3 = new JLabel();
        jLabel3.setFont(new Font("Tahoma", 1, 14));
        jLabel3.setText("Año:");

        jLabel4 = new JLabel();
        jLabel4.setFont(new Font("Tahoma", 1, 14));
        jLabel4.setText("Tipo:");

        jLabel5 = new JLabel();
        jLabel5.setFont(new Font("Tahoma", 1, 14));
        jLabel5.setText("Color:");

        jLabel6 = new JLabel();
        jLabel6.setFont(new Font("Tahoma", 1, 14));
        jLabel6.setText("Tamaño Marco:");

        txtBrand = new JTextField();
        txtBrand.setColumns(20);

        txtModel = new JTextField();
        txtModel.setColumns(20);

        txtYear = new JTextField();
        txtYear.setColumns(4);
        txtYear.setText("2024");

        comboType = new JComboBox<>();
        comboType.setModel(new DefaultComboBoxModel<>(new String[] {
                "Mountain", "Road", "Hybrid", "Electric", "BMX", "Other"
        }));

        txtColor = new JTextField();
        txtColor.setColumns(15);

        txtFrameSize = new JTextField();
        txtFrameSize.setColumns(10);

        jLabel7 = new JLabel();
        jLabel7.setFont(new Font("Tahoma", 1, 14));
        jLabel7.setText("Condición:");

        comboStatus = new JComboBox<>();
        comboStatus.setModel(new DefaultComboBoxModel<>(new String[] {
                "Excellent", "Good", "Fair", "Needs Repair"
        }));

        okButton = new JButton();
        okButton.setText("Agregar");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton = new JButton();
        cancelButton.setText("Cancelar");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel4)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jLabel6)
                                                        .addComponent(jLabel7))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtBrand, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtModel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtYear, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(comboType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtColor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtFrameSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(comboStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(50, 50, 50)
                                                .addComponent(okButton)
                                                .addGap(18, 18, 18)
                                                .addComponent(cancelButton)))
                                .addContainerGap(20, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(txtBrand, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtModel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(txtYear, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(comboType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(txtColor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(txtFrameSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(txtFrameSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
//                                .addGap(10, 10, 10)
                                .addComponent(comboStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(okButton)
                                        .addComponent(cancelButton))
                                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(getParent());
    }

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String brand = txtBrand.getText().trim();
            String model = txtModel.getText().trim();
            String year = txtYear.getText().trim();
            String type = (String) comboType.getSelectedItem();
            String color = txtColor.getText().trim();
            String frameSize = txtFrameSize.getText().trim();
            String status = (String) comboStatus.getSelectedItem();

            if (brand.isEmpty() || model.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Marca y Modelo son obligatorios");
                return;
            }

            String sql = String.format(
                    "INSERT INTO Bicycles (brand, model, year_manufacture, type, color, frame_size, maintenance_status, last_service_date) " +
                            "VALUES ('%s', '%s', %s, '%s', '%s', '%s', '%s', CURRENT_DATE)",
                    brand, model, year, type, color, frameSize, status
            );

            db.update(sql);
            JOptionPane.showMessageDialog(this, "Bicicleta agregada exitosamente");
            setVisible(false);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar bicicleta: " + ex.getMessage());
        }
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        setVisible(false);
    }
}