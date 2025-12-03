package main.java.com.bicyclerental.dialogs;

import main.java.com.bicyclerental.Database;
import main.java.com.bicyclerental.MainApp;


import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InsertMultiShop extends JDialog {
    private final Database db;

    private JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8, jLabel9;
    private JTextField txtContactName, txtLocationName, txtEmail, txtAddress, txtPhoneNumber,txtBusinessHours, txtManagerName, txtNumberOfEmployees;
    JComboBox<String> AdditionalServices;
    private JButton okButton, cancelButton;

    public InsertMultiShop(Frame parent, Database db) {
        super(parent, true);
        this.db = db;
        initComponents();
    }

    public InsertMultiShop(MainApp parent, Database db, Database db1) {
        this.db = db1;
    }
    private void initComponents() {
        setTitle("Agregar Multi Shop");
        setResizable(false);

        jLabel1 = new JLabel();
        jLabel1.setFont(new Font("Tahoma", 1, 14));
        jLabel1.setText("Nombre del contacto:");

        jLabel2 = new JLabel();
        jLabel2.setFont(new Font("Tahoma", 1, 14));
        jLabel2.setText("Nombre de la locación:");

        jLabel3 = new JLabel();
        jLabel3.setFont(new Font("Tahoma", 1, 14));
        jLabel3.setText("Email:");

        jLabel4 = new JLabel();
        jLabel4.setFont(new Font("Tahoma", 1, 14));
        jLabel4.setText("Dirección:");

        jLabel5 = new JLabel();
        jLabel5.setFont(new Font("Tahoma", 1, 14));
        jLabel5.setText("Teléfono:");

        jLabel6 = new JLabel();
        jLabel6.setFont(new Font("Tahoma", 1, 14));
        jLabel6.setText("Horas de negocio:");

        jLabel7 = new JLabel();
        jLabel7.setFont(new Font("Tahoma", 1, 14));
        jLabel7.setText("Nombre del encargado:");

        jLabel8 = new JLabel();
        jLabel8.setFont(new Font("Tahoma", 1, 14));
        jLabel8.setText("Numero de empleados:");

        jLabel9 = new JLabel();
        jLabel9.setFont(new Font("Tahoma", 1, 14));
        jLabel9.setText("Servicios adicionales:");

//        txtID = new JTextField();
//        txtID.setColumns(10);
        int col = 30;

        txtContactName = new JTextField();
        txtContactName.setColumns(col);

        txtLocationName = new JTextField();
        txtLocationName.setColumns(col);

        txtEmail = new JTextField();
        txtEmail.setColumns(col);
        txtEmail.setText("gerardoTapiaEstuvoAqui@gmail.com");

        txtAddress = new JTextField();
        txtAddress.setColumns(col);
        txtAddress.setText("Calle hamburguesa esquina con pollo ramos");

        txtPhoneNumber = new JTextField();
        txtPhoneNumber.setColumns(col);

        txtBusinessHours = new JTextField();
        txtBusinessHours.setColumns(col);
        txtBusinessHours.setText("9:00 a.m. a 6:00 p.m.");

        txtManagerName = new JTextField();
        txtManagerName.setColumns(col);

        txtNumberOfEmployees = new JTextField();
        txtNumberOfEmployees.setColumns(col);

        AdditionalServices = new JComboBox<>();
        AdditionalServices.setModel(new DefaultComboBoxModel<>(new String[] {
                "General Repairs", "Bike Wash", "Tire Inflation", "Chain Lubrication", "Accessory Installation", "Helmet or Lock Rental", "Battery Charging", "Bike Storage", "Roadside Assistance", "Basic Adjustment"
        }));

        // ya para agregar
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
                                                        .addComponent(jLabel7)
                                                        .addComponent(jLabel8)
                                                        .addComponent(jLabel9))))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtContactName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtLocationName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtPhoneNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtBusinessHours, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtManagerName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtNumberOfEmployees, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(AdditionalServices, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                ))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(50, 50, 50)
                                                .addComponent(okButton)
                                                .addGap(18, 18, 18)
                                                .addComponent(cancelButton))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(txtContactName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtLocationName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(txtAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(txtPhoneNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(txtBusinessHours, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(txtManagerName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel8)
                                        .addComponent(txtNumberOfEmployees, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(AdditionalServices, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
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
            String contactName = txtContactName.getText();
            String locationName = txtLocationName.getText().trim(); //not null
            String emailAddress = txtEmail.getText().trim();
            String address = txtAddress.getText(); //not null
            String phoneNumber = txtPhoneNumber.getText().trim();
            String businessHours = txtBusinessHours.getText();
            String managerName = txtManagerName.getText();
            String numEmployees = txtNumberOfEmployees.getText().trim();
            String extraServices = (String) AdditionalServices.getSelectedItem();

            if (locationName.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre y Dirección del negocio son obligatorias");
                return;
            }

            String sql = "INSERT INTO multi_shop "
                    + "(contact_name, location_name, email_address, address, phone_number, "
                    + "business_hours, manager_name, number_of_employees, additional_services) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, contactName);
            ps.setString(2, locationName);
            ps.setString(3, emailAddress);
            ps.setString(4, address);
            ps.setString(5, phoneNumber);
            ps.setString(6, businessHours);
            ps.setString(7, managerName);

            // Manejo seguro del número
            if (numEmployees.isEmpty()) {
                ps.setNull(8, java.sql.Types.INTEGER);
            } else {
                ps.setInt(8, Integer.parseInt(numEmployees));
            }

            ps.setString(9, extraServices);

            ps.executeUpdate();
            System.out.println(sql);

            // db.update(sql);
            JOptionPane.showMessageDialog(this, "Tienda agregada exitosamente");
            setVisible(false);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar tienda: " + ex.getMessage());
        }
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        setVisible(false);
    }

}
