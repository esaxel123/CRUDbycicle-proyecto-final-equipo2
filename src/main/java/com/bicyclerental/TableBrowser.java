package main.java.com.bicyclerental;

import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class TableBrowser extends javax.swing.JInternalFrame {
    private JTable table = null;

    public TableBrowser(String title, TableModel modelo) {
        super(title, true, true, true, true);
        this.initComponents(modelo);
    }

    private void initComponents(TableModel modelo) {
        table = new JTable(modelo);
        table.setPreferredScrollableViewportSize(new Dimension(800, 400));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        this.getContentPane().add(scrollPane);
        this.pack();
    }
}