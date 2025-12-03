package main.java.com.bicyclerental;

import java.sql.SQLException;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class BicyclesTableListener implements TableModelListener {
    private static final String CLASS_NAME = BicyclesTableListener.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    private final Database db;

    public BicyclesTableListener(Database d) {
        super();
        db = d;
    }

    @Override
    public void tableChanged(TableModelEvent event) {
        JDBCTableAdapter modelo = (JDBCTableAdapter) event.getSource();

        int row = event.getFirstRow();
        int column = event.getColumn();
        int type = event.getType();

        // No se puede modificar una clave primaria
        if (column == 0) {
            return;
        }

        String colName = modelo.getColumnName(column);
        String colSQLName = modelo.getSQLColumnName(column);
        String tableName = getTableNameFromModel(modelo);
        String primaryKey = modelo.getColumnName(0);
        String primaryKeyValue = modelo.getValueAt(row, 0).toString();

        String sql = String.format("UPDATE %s SET %s = '%s' WHERE %s = %s",
                tableName, colSQLName, modelo.getValueAt(row, column),
                primaryKey, primaryKeyValue);

        JOptionPane.showMessageDialog(null, "Se actualizo " + colName + " correctamente.", "Actualizacion " + tableName, JOptionPane.INFORMATION_MESSAGE);

        try {
            db.update(sql);
        } catch (SQLException ex) {
            LOGGER.severe("Error: " + ex.getMessage());
            LOGGER.severe("Codigo : " + ex.getErrorCode());
        }
    }

    private String getTableNameFromModel(JDBCTableAdapter modelo) {
        // Esta es una simplificación - en una implementación real
        // deberías tener una forma de determinar la tabla basada en las columnas
        if (modelo.getColumnCount() > 0) {
            String firstCol = modelo.getColumnName(0);
            if (firstCol.toLowerCase().contains("bicycle")) {
                return "Bicycles";
            } else if (firstCol.toLowerCase().contains("renter")) {
                return "Renters";
            } else if (firstCol.toLowerCase().contains("rental")) {
                return "Rentals";
            }
        }
        return "Bicycles"; // default
    }
}