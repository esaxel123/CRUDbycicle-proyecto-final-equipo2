package main.java.com.bicyclerental;

import main.java.com.bicyclerental.dialogs.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class MainApp extends javax.swing.JFrame {
    private static final String CLASS_NAME = MainApp.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    private final String USER = "developer";
    private final String PASS = "4321";
    final private Database db;

    // Variables declaration
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem contentMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuBrowse;
    private javax.swing.JMenu menuDelete;
    private javax.swing.JMenu menuInsertar;
    private javax.swing.JMenu menuReports;

    // Menu items
    private javax.swing.JMenuItem insertBicycleMenuItem;
    private javax.swing.JMenuItem insertRenterMenuItem;
    private javax.swing.JMenuItem insertRentalMenuItem;
    private javax.swing.JMenuItem insertMultiShop;
    private javax.swing.JMenuItem insertRenterPaymentMethod;

    private javax.swing.JMenuItem browseBicyclesMenuItem;
    private javax.swing.JMenuItem browseRentersMenuItem;
    private javax.swing.JMenuItem browseRentalsMenuItem;
    private javax.swing.JMenuItem deleteBicycleMenuItem;
    private javax.swing.JMenuItem deleteRenterMenuItem;
    private javax.swing.JMenuItem deleteRentalMenuItem;
    private javax.swing.JMenuItem reportBicyclesMenuItem;
    private javax.swing.JMenuItem reportRentalsMenuItem;
    private javax.swing.JMenuItem reportPaymentsMenuItem;
    private javax.swing.JMenuItem maintenanceReportMenuItem;

    public MainApp() {
        db = Database.getDatabase(USER, PASS);
        initComponents();
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainApp().setVisible(true);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        desktopPane = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // File Menu
        fileMenu = new javax.swing.JMenu();
        fileMenu.setMnemonic('f');
        fileMenu.setText("Archivo");

        exitMenuItem = new javax.swing.JMenuItem();
        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Salir");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        // Insert Menu
        menuInsertar = new javax.swing.JMenu();
        menuInsertar.setText("Insertar");

        insertBicycleMenuItem = new javax.swing.JMenuItem();
        insertBicycleMenuItem.setText("Bicicleta");
        insertBicycleMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertBicycleMenuItemActionPerformed(evt);
            }
        });
        menuInsertar.add(insertBicycleMenuItem);

        insertMultiShop = new javax.swing.JMenuItem();
        insertMultiShop.setText("Tienda");
        insertMultiShop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertMultiShopItemActionPerformed(evt);
            }
        });
        menuInsertar.add(insertMultiShop);

        insertMultiShop = new javax.swing.JMenuItem();
        insertMultiShop.setText("Bicicleta en Tienda");
        insertMultiShop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertBicyclesInShopsItemActionPerformed(evt);
            }
        });
        menuInsertar.add(insertMultiShop);

        insertRenterMenuItem = new javax.swing.JMenuItem();
        insertRenterMenuItem.setText("Cliente");
        insertRenterMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertRenterMenuItemActionPerformed(evt);
            }
        });
        menuInsertar.add(insertRenterMenuItem);

        insertRenterPaymentMethod = new javax.swing.JMenuItem();
        insertRenterPaymentMethod.setText("Metodo de Pago de Cliente");
        insertRenterPaymentMethod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertRenterPaymentMethodItemActionPerformed(evt);
            }
        });
        menuInsertar.add(insertRenterPaymentMethod);

        insertRentalMenuItem = new javax.swing.JMenuItem();
        insertRentalMenuItem.setText("Precios de Renta");
        insertRentalMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertRentalRatesActionPerformed(evt);
            }
        });
        menuInsertar.add(insertRentalMenuItem);

        insertRentalMenuItem = new javax.swing.JMenuItem();
        insertRentalMenuItem.setText("Renta");
        insertRentalMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertRentalMenuItemActionPerformed(evt);
            }
        });
        menuInsertar.add(insertRentalMenuItem);

        menuBar.add(menuInsertar);

        // Browse Menu
        menuBrowse = new javax.swing.JMenu();
        menuBrowse.setText("Consultar");

        browseBicyclesMenuItem = new javax.swing.JMenuItem();
        browseBicyclesMenuItem.setText("Bicicletas");
        browseBicyclesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browse(evt, "Bicicletas", "SELECT * FROM Bicycles");
            }
        });
        menuBrowse.add(browseBicyclesMenuItem);

        browseRentalsMenuItem = new javax.swing.JMenuItem();
        browseRentalsMenuItem.setText("Tiendas");
        browseRentalsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browse(evt, "Tiendas", "SELECT * FROM Multi_Shop");
            }
        });
        menuBrowse.add(browseRentalsMenuItem);

        browseRentalsMenuItem = new javax.swing.JMenuItem();
        browseRentalsMenuItem.setText("Bicicletas en Tiendas");
        browseRentalsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browse(evt, "Bicicletas en Tiendas", "SELECT bs.bicycle_id, b.model, b.type, b.color, b.brand, mul.multi_shop_id, mul.location_name, mul.address, b.brand, bs.datetime_in, bs.datetime_out, bs.current_status, bs.inventory_responsible_person, bs.last_movement_date FROM bicycles_in_shops bs JOIN bicycles b ON b.bicycle_id = bs.bicycle_id JOIN multi_shop mul ON mul.multi_shop_id = bs.multi_shop_id");
            }
        });
        menuBrowse.add(browseRentalsMenuItem);

        browseRentersMenuItem = new javax.swing.JMenuItem();
        browseRentersMenuItem.setText("Clientes");
        browseRentersMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browse(evt, "Clientes","SELECT * FROM Renters");
            }
        });
        menuBrowse.add(browseRentersMenuItem);

        browseRentalsMenuItem = new javax.swing.JMenuItem();
        browseRentalsMenuItem.setText("Tipos de Metodos de Pago");
        browseRentalsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browse(evt, "Tipos de Metodos de Pago", "SELECT * FROM ref_payment_methods");
            }
        });
        menuBrowse.add(browseRentalsMenuItem);

        browseRentalsMenuItem = new javax.swing.JMenuItem();
        browseRentalsMenuItem.setText("Metodos de Pago de Usuarios");
        browseRentalsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browse(evt, "Metodos de Pago de Usuarios", "SELECT rpm.renter_payment_method_id, rpm.renter_id, rpm.cardholder_name, rpm.payment_methods_code, pm.payment_method_description, rpm.issuing_bank, rpm.verification_method FROM renters_payment_methods rpm JOIN ref_payment_methods pm ON pm.payment_methods_code = rpm.payment_methods_code ORDER BY renter_payment_method_id");
            }
        });
        menuBrowse.add(browseRentalsMenuItem);

        browseRentalsMenuItem = new javax.swing.JMenuItem();
        browseRentalsMenuItem.setText("Estados de Pago");
        browseRentalsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browse(evt, "Estados de Pago", "SELECT * FROM ref_payment_status");
            }
        });
        menuBrowse.add(browseRentalsMenuItem);

        browseRentalsMenuItem = new javax.swing.JMenuItem();
        browseRentalsMenuItem.setText("Rentas");
        browseRentalsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browse(evt, "Rentas",
                        "SELECT r.rental_id, re.full_name, b.brand, b.model, r.actual_start_date_time, r.actual_end_date_time, r.rental_payment_due, r.rental_payment_made, ps.payment_status_description, rr.daily_rate, rr.hourly_rate FROM Rentals r JOIN Renters re ON r.renter_id = re.renter_id JOIN Bicycles b ON r.bicycle_id = b.bicycle_id JOIN Ref_Payment_Status ps ON r.payment_status_code = ps.payment_status_code JOIN rental_rates rr ON rr.rental_rates_id = r.rental_rates_id ORDER BY r.rental_id");
            }
        });
        menuBrowse.add(browseRentalsMenuItem);



        menuBar.add(menuBrowse);

        // Delete Menu
        menuDelete = new javax.swing.JMenu();
        menuDelete.setText("Eliminar");

        deleteBicycleMenuItem = new javax.swing.JMenuItem();
        deleteBicycleMenuItem.setText("Bicicleta");
        deleteBicycleMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMenuItemActionPerformed(evt, "SELECT bicycle_id, brand, model FROM Bicycles ORDER BY bicycle_id", "bicycles", "bicycle_id", "Bicicleta");
            }
        });
        menuDelete.add(deleteBicycleMenuItem);

        deleteRentalMenuItem = new javax.swing.JMenuItem();
        deleteRentalMenuItem.setText("Tienda");
        deleteRentalMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMenuItemActionPerformed(evt, "SELECT multi_shop_id, location_name, address FROM Multi_Shop ORDER BY multi_shop_id", "multi_shop", "multi_shop_id", "Tienda");
            }
        });
        menuDelete.add(deleteRentalMenuItem);

        deleteRentalMenuItem = new javax.swing.JMenuItem();
        deleteRentalMenuItem.setText("Bicicletas en Tiendas");
        deleteRentalMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMenuItemActionPerformed(evt, "SELECT bis.bicycle_id, b.model, b.brand, ms.location_name FROM bicycles_in_shops bis JOIN bicycles b ON b.bicycle_id = bis.bicycle_id JOIN multi_shop ms ON ms.multi_shop_id = bis.multi_shop_id ORDER BY bis.bicycle_id", "bicycles_in_shops", "bicycle_id", "Bicicleta en Tienda");
            }
        });
        menuDelete.add(deleteRentalMenuItem);

        deleteRenterMenuItem = new javax.swing.JMenuItem();
        deleteRenterMenuItem.setText("Cliente");
        deleteRenterMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMenuItemActionPerformed(evt, "SELECT renter_id, full_name FROM Renters ORDER BY renter_id","renters", "renter_id", "Cliente");
            }
        });
        menuDelete.add(deleteRenterMenuItem);

        deleteBicycleMenuItem = new javax.swing.JMenuItem();
        deleteBicycleMenuItem.setText("Metodo de Pago de Clientes");
        deleteBicycleMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMenuItemActionPerformed(evt, "SELECT r.renter_payment_method_id, re.payment_method_description, r.issuing_bank, r.cardholder_name FROM renters_payment_methods r JOIN ref_payment_methods re ON re.payment_methods_code = r.payment_methods_code ORDER BY r.renter_payment_method_id", "renters_payment_methods", "renter_payment_method_id", "Metodo de Pago de Clientes");
            }
        });
        menuDelete.add(deleteBicycleMenuItem);

        deleteRentalMenuItem = new javax.swing.JMenuItem();
        deleteRentalMenuItem.setText("Bicicletas en Tiendas");
        deleteRentalMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMenuItemActionPerformed(evt, "SELECT bis.bicycle_id, b.model, b.brand, ms.location_name FROM bicycles_in_shops bis JOIN bicycles b ON b.bicycle_id = bis.bicycle_id JOIN multi_shop ms ON ms.multi_shop_id = bis.multi_shop_id ORDER BY bis.bicycle_id", "bicycles_in_shops", "bicycle_id", "Bicicleta en Tienda");
            }
        });
        menuDelete.add(deleteRentalMenuItem);

        deleteRentalMenuItem = new javax.swing.JMenuItem();
        deleteRentalMenuItem.setText("Renta");
        deleteRentalMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMenuItemActionPerformed(evt, "SELECT r.rental_id, re.full_name, b.brand, b.model, r.actual_start_date_time FROM Rentals r JOIN Renters re ON r.renter_id = re.renter_id JOIN Bicycles b ON r.bicycle_id = b.bicycle_id ORDER BY r.rental_id", "rentals", "rental_id", "Renta");
            }
        });
        menuDelete.add(deleteRentalMenuItem);

        deleteRentalMenuItem = new javax.swing.JMenuItem();
        deleteRentalMenuItem.setText("Precios de renta");
        deleteRentalMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMenuItemActionPerformed(evt, "SELECT rental_rates_id, daily_rate, hourly_rate FROM rental_rates", "rental_rates", "rental_rates_id", "Cobros de Rentas");
            }
        });
        menuDelete.add(deleteRentalMenuItem);

        menuBar.add(menuDelete);

        // REPORTS menu

        menuReports = new javax.swing.JMenu();
        menuReports.setText("Reportes");

        reportBicyclesMenuItem = new javax.swing.JMenuItem();
        reportBicyclesMenuItem.setText("Bicicletas Disponibles");
        reportBicyclesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browse(evt, "Bicicletas Disponibles", "SELECT b.bicycle_id, b.brand, b.model, bis.current_status, " +
                        "s.location_name " +
                        "FROM Bicycles b " +
                        "JOIN Bicycles_in_Shops bis ON b.bicycle_id = bis.bicycle_id " +
                        "JOIN Multi_Shop s ON bis.multi_shop_id = s.multi_shop_id " +
                        "WHERE bis.current_status = 'Available' " +
                        "ORDER BY b.brand, b.model");
            }
        });
        menuReports.add(reportBicyclesMenuItem);

        reportRentalsMenuItem = new javax.swing.JMenuItem();
        reportRentalsMenuItem.setText("Rentas Activas");
        reportRentalsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browse(evt, "Rentas Activas", "SELECT r.rental_id, re.full_name, b.brand, b.model, " +
                        "r.actual_start_date_time, r.payment_status_code " +
                        "FROM Rentals r " +
                        "JOIN Renters re ON r.renter_id = re.renter_id " +
                        "JOIN Bicycles b ON r.bicycle_id = b.bicycle_id " +
                        "WHERE r.payment_status_code IN ('PEND', 'OVRD') " +
                        "ORDER BY r.actual_start_date_time");
            }
        });
        menuReports.add(reportRentalsMenuItem);

        reportPaymentsMenuItem = new javax.swing.JMenuItem();
        reportPaymentsMenuItem.setText("Pagos Pendientes");
        reportPaymentsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browse(evt, "Pagos Pendientes", "SELECT r.rental_id, re.full_name, b.brand, b.model, " +
                        "r.rental_payment_due, r.rental_payment_made, " +
                        "ps.payment_status_description " +
                        "FROM Rentals r " +
                        "JOIN Renters re ON r.renter_id = re.renter_id " +
                        "JOIN Bicycles b ON r.bicycle_id = b.bicycle_id " +
                        "JOIN Ref_Payment_Status ps ON r.payment_status_code = ps.payment_status_code " +
                        "WHERE ps.payment_status_description IN ('Pending', 'Overdue') " +
                        "ORDER BY r.rental_payment_due DESC");
            }
        });
        menuReports.add(reportPaymentsMenuItem);

        menuBar.add(menuReports);

        // PROCEDURES Menu
        menuReports = new javax.swing.JMenu();
        menuReports.setText("Procedimientos");
        deleteRentalMenuItem = new javax.swing.JMenuItem();
        deleteRentalMenuItem.setText("Pagar Renta");
        deleteRentalMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payProcedureItemActionPerformed(evt);
            }
        });
        menuReports.add(deleteRentalMenuItem);

        deleteRentalMenuItem = new javax.swing.JMenuItem();
        deleteRentalMenuItem.setText("Liberar Renta");
        deleteRentalMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentalReleaseItemActionPerformed(evt);
            }
        });
        menuReports.add(deleteRentalMenuItem);

        maintenanceReportMenuItem = new javax.swing.JMenuItem();
        maintenanceReportMenuItem.setText("Reporte de Mantenimiento");
        maintenanceReportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maintenanceReportItemActionPerformed(evt);
            }
        });
        menuReports.add(maintenanceReportMenuItem);

        menuBar.add(menuReports);



        // Help Menu
        helpMenu = new javax.swing.JMenu();
        helpMenu.setMnemonic('h');
        helpMenu.setText("Ayuda");

        contentMenuItem = new javax.swing.JMenuItem();
        contentMenuItem.setMnemonic('c');
        contentMenuItem.setText("Contenidos");
        helpMenu.add(contentMenuItem);

        aboutMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("Acerca de");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );

        pack();
    }

    private void payProcedureItemActionPerformed(ActionEvent evt) {
        PayProcedureDialog dlg = new PayProcedureDialog(this, db);
        dlg.setVisible(true);
    }

    private void rentalReleaseItemActionPerformed(ActionEvent evt) {
        RentalReleaseDialog dlg = new RentalReleaseDialog(this, db);
        dlg.setVisible(true);
    }

    private void maintenanceReportItemActionPerformed(java.awt.event.ActionEvent evt) {
        MaintenanceReportDialog dialog = new MaintenanceReportDialog(this, db);
        dialog.setVisible(true);
    }


    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        JOptionPane.showMessageDialog(this,
                "Sistema de Renta de Bicicletas\nVersión 3.0\nEquipo 2",
                "Acerca de",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void insertBicycleMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        InsertBicycleDialog dlg = new InsertBicycleDialog(this, db);
        dlg.setVisible(true);
    }

    private void insertBicyclesInShopsItemActionPerformed(ActionEvent evt) {
        InsertBicycleInShop dlg = new InsertBicycleInShop(this, db);
        dlg.setVisible(true);
    }

    private void insertRenterMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        InsertRenterDialog dlg = new InsertRenterDialog(this, db);
        dlg.setVisible(true);
    }

    private void insertRentalMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        InsertRentalDialog dlg = new InsertRentalDialog(this, db);
        dlg.setVisible(true);
    }

    private void insertMultiShopItemActionPerformed(java.awt.event.ActionEvent evt) {
        InsertMultiShop dlg = new InsertMultiShop(this, db);
        dlg.setVisible(true);
    }

    private void insertRenterPaymentMethodItemActionPerformed(java.awt.event.ActionEvent evt) {
        InsertRenterPaymentMethodDialog dlg = new InsertRenterPaymentMethodDialog(this, db);
        dlg.setVisible(true);
    }

    private void insertRentalRatesActionPerformed(ActionEvent evt) {
        InsertRentalRatesDialog dlg = new InsertRentalRatesDialog(this, db);
        dlg.setVisible(true);
    }


    private void browse(java.awt.event.ActionEvent evt, String title, String query){
        try {
            ResultSet rs = db.query(query);
            JDBCTableAdapter modelo = new JDBCTableAdapter(rs);
            modelo.addTableModelListener(new BicyclesTableListener(db));
            TableBrowser browser = new TableBrowser(title, modelo);
            browser.setVisible(true);
            this.desktopPane.add(browser);
        } catch (SQLException ex) {
            LOGGER.severe("Error: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Error al cargar " + title + ": " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void deleteMenuItemActionPerformed(java.awt.event.ActionEvent evt, String query, String table, String idName, String title) {
        DeleteDialog dialog = new DeleteDialog(this, db, query, table, idName, title);
        dialog.setVisible(true);
    }
}
