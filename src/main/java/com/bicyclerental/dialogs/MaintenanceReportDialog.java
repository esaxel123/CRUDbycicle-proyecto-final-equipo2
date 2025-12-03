package main.java.com.bicyclerental.dialogs;

import main.java.com.bicyclerental.Database;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class MaintenanceReportDialog extends javax.swing.JDialog {

    public static final int RET_CANCEL = 0;
    public static final int RET_OK = 1;
    private int returnStatus = RET_CANCEL;
    private final Database db;

    private javax.swing.JButton okButton, cancelButton, markRepairedButton;
    private javax.swing.JLabel jLabel1, totalBikesLabel, shopFilterLabel;
    private javax.swing.JComboBox<String> shopComboBox;
    private javax.swing.JTable maintenanceTable;
    private JScrollPane tableScrollPane;

    // Modelo para la tabla
    private DefaultTableModel tableModel;

    // Mapeo de tiendas
    private HashMap<String, Integer> shopMap;
    private HashMap<Integer, String> shopIdToNameMap;

    public MaintenanceReportDialog(java.awt.Frame parent, Database db) {
        super(parent, true);
        this.db = db;
        this.shopMap = new HashMap<>();
        this.shopIdToNameMap = new HashMap<>();

        initComponents();
        loadShops();
        loadMaintenanceData(null); // Cargar todas las bicicletas inicialmente

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

        // Filtrar por tienda
        shopComboBox.addActionListener(e -> filterByShop());
    }

    private void initComponents() {
        setTitle("Reporte de Mantenimiento");
        setResizable(true);
        setPreferredSize(new Dimension(800, 600));

        okButton = new JButton("Cerrar");
        okButton.addActionListener(e -> okButtonActionPerformed());

//        cancelButton = new JButton("Exportar PDF");
//        cancelButton.addActionListener(e -> exportButtonActionPerformed());

        markRepairedButton = new JButton("Marcar como Reparada");
        markRepairedButton.addActionListener(e -> markRepairedActionPerformed());

        jLabel1 = new JLabel("Bicicletas en Mantenimiento");
        jLabel1.setFont(new Font("Tahoma", Font.BOLD, 16));

        totalBikesLabel = new JLabel("Total: 0 bicicletas");
        totalBikesLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));

        shopFilterLabel = new JLabel("Filtrar por Tienda:");
        shopFilterLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));

        shopComboBox = new JComboBox<>();
        shopComboBox.addItem("Todas las Tiendas");
        shopComboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
        shopComboBox.setPreferredSize(new Dimension(200, 25));

        // Configurar tabla
        String[] columnNames = {
                "ID", "Marca", "Modelo", "Color", "Tipo", "Estado Mant.",
                "Último Servicio", "Ubicación", "Días en Mant."
        };

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };

        maintenanceTable = new JTable(tableModel);
        maintenanceTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
        maintenanceTable.setRowHeight(25);
        maintenanceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        maintenanceTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));

        tableScrollPane = new JScrollPane(maintenanceTable);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel superior con título y filtros
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        topPanel.add(jLabel1, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        topPanel.add(shopFilterLabel, gbc);

        gbc.gridx = 1;
        topPanel.add(shopComboBox, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        topPanel.add(totalBikesLabel, gbc);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.add(markRepairedButton);
//        buttonPanel.add(cancelButton);
        buttonPanel.add(okButton);

        // Agregar componentes al panel principal
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(getParent());
    }

    private void loadShops() {
        try {
            ResultSet rs = db.query("SELECT multi_shop_id, location_name FROM multi_shop ORDER BY location_name");
            while (rs.next()) {
                String shopName = rs.getString("location_name");
                int shopId = rs.getInt("multi_shop_id");
                shopComboBox.addItem(shopName);
                shopMap.put(shopName, shopId);
                shopIdToNameMap.put(shopId, shopName);
            }
        } catch (SQLException ex) {
            System.out.println("Error cargando tiendas: " + ex.getMessage());
        }
    }

    private void loadMaintenanceData(Integer shopId) {
        try {
            // Limpiar tabla
            tableModel.setRowCount(0);

            String query;
            if (shopId == null) {
                // Todas las tiendas
                query = "SELECT b.bicycle_id, b.brand, b.model, b.color, b.type, " +
                        "b.maintenance_status, b.last_service_date, " +
                        "bis.current_status, ms.location_name, " +
                        "CURRENT_DATE - bis.last_movement_date AS days_in_maintenance " +
                        "FROM bicycles b " +
                        "JOIN bicycles_in_shops bis ON b.bicycle_id = bis.bicycle_id " +
                        "JOIN multi_shop ms ON bis.multi_shop_id = ms.multi_shop_id " +
                        "WHERE bis.current_status = 'Maintenance' " +
                        "ORDER BY days_in_maintenance DESC, b.last_service_date";
            } else {
                // Tienda específica
                query = "SELECT b.bicycle_id, b.brand, b.model, b.color, b.type, " +
                        "b.maintenance_status, b.last_service_date, " +
                        "bis.current_status, ms.location_name, " +
                        "CURRENT_DATE - bis.last_movement_date AS days_in_maintenance " +
                        "FROM bicycles b " +
                        "JOIN bicycles_in_shops bis ON b.bicycle_id = bis.bicycle_id " +
                        "JOIN multi_shop ms ON bis.multi_shop_id = ms.multi_shop_id " +
                        "WHERE bis.current_status = 'Maintenance' " +
                        "AND bis.multi_shop_id = " + shopId + " " +
                        "ORDER BY days_in_maintenance DESC, b.last_service_date";
            }

            ResultSet rs = db.query(query);
            int rowCount = 0;

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("bicycle_id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("color"),
                        rs.getString("type"),
                        rs.getString("maintenance_status"),
                        rs.getDate("last_service_date"),
                        rs.getString("location_name"),
                        rs.getInt("days_in_maintenance")
                };
                tableModel.addRow(row);
                rowCount++;
            }

            totalBikesLabel.setText("Total: " + rowCount + " bicicleta(s) en mantenimiento");

        } catch (SQLException ex) {
            System.out.println("Error cargando datos de mantenimiento: " + ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Error al cargar datos de mantenimiento: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterByShop() {
        String selectedShop = (String) shopComboBox.getSelectedItem();
        if (selectedShop != null && !selectedShop.equals("Todas las Tiendas")) {
            Integer shopId = shopMap.get(selectedShop);
            loadMaintenanceData(shopId);
        } else {
            loadMaintenanceData(null);
        }
    }

    private void markRepairedActionPerformed() {
        int selectedRow = maintenanceTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor seleccione una bicicleta de la tabla",
                    "Selección requerida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int bikeId = (int) tableModel.getValueAt(selectedRow, 0);
        String bikeInfo = tableModel.getValueAt(selectedRow, 1) + " " +
                tableModel.getValueAt(selectedRow, 2);

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de marcar la bicicleta '" + bikeInfo + "' como reparada?",
                "Confirmar Reparación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Actualizar estado en bicycles_in_shops
                String updateShopStatus = "UPDATE bicycles_in_shops " +
                        "SET current_status = 'Available', " +
                        "last_movement_date = CURRENT_DATE " +
                        "WHERE bicycle_id = " + bikeId;

                // Actualizar estado de mantenimiento en bicycles
                String updateMaintenanceStatus = "UPDATE bicycles " +
                        "SET maintenance_status = 'Good', " +
                        "last_service_date = CURRENT_DATE " +
                        "WHERE bicycle_id = " + bikeId;

                db.update(updateShopStatus);
                db.update(updateMaintenanceStatus);

                // Recargar datos
                filterByShop();

                JOptionPane.showMessageDialog(this,
                        "Bicicleta marcada como reparada y disponible",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al actualizar el estado: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

//    private void exportButtonActionPerformed() {
//        JOptionPane.showMessageDialog(this,
//                "Función de exportación PDF será implementada próximamente",
//                "En desarrollo",
//                JOptionPane.INFORMATION_MESSAGE);
//        // En una implementación real, aquí se generaría un PDF del reporte
//    }

    private void okButtonActionPerformed() {
        returnStatus = RET_OK;
        setVisible(false);
        dispose();
    }

    public int getReturnStatus() {
        return returnStatus;
    }
}