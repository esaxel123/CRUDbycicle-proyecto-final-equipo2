
package main.java.com.bicyclerental.dialogs;

import main.java.com.bicyclerental.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class DeleteDialog extends javax.swing.JDialog {
    public static final int RET_CANCEL = 0;
    public static final int RET_OK = 1;
    private int returnStatus = RET_CANCEL;
    private final Database db;
    private String table;
    private String idName;
    private String title;

    private javax.swing.JButton okButton, cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JComboBox<String> comboRenter;

    public DeleteDialog(java.awt.Frame parent, Database db, String query, String table, String idName, String title) {
        super(parent, true);
        this.db = db;
        this.table = table;
        this.idName = idName;
        this.title = title;
        initComponents();
        try {
            ResultSet rs = db.query(query);
            int colCount = rs.getMetaData().getColumnCount();
            String comboBoxText;
            while (rs.next()){
                comboBoxText = rs.getString(1);
                for (int i = 2; i <= colCount; i++) {
                    comboBoxText = comboBoxText + " - " + rs.getString(i);
                }
                comboRenter.addItem(comboBoxText);
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
    }

    private void initComponents() {
        setTitle("Eliminar " + title);
        setResizable(false);

        okButton = new JButton("Eliminar");
        okButton.addActionListener(e -> okButtonActionPerformed());

        cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> cancelButtonActionPerformed());

        jLabel1 = new JLabel("Seleccione " + title.toLowerCase() + ":");
        jLabel1.setFont(new Font("Tahoma", Font.BOLD, 14));

        comboRenter = new JComboBox<>();
        comboRenter.setFont(new Font("Tahoma", Font.PLAIN, 12));
        comboRenter.setPreferredSize(new Dimension(300, 25));

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(jLabel1, gbc);

        gbc.gridy = 1;
        panel.add(comboRenter, gbc);

        gbc.gridy = 2;
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
            String item = (String) comboRenter.getSelectedItem();
            if (item != null && !item.isEmpty()) {
                String id = item.substring(0, item.indexOf(" - "));

                int confirm = JOptionPane.showConfirmDialog(this,
                        "¿Está seguro de eliminar este " + title.toLowerCase() + "?\nEsta acción no se puede deshacer.",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    int result = db.update(String.format("DELETE FROM " + table + " WHERE " + idName + " = " + id));
                    if (result > 0) {
                        JOptionPane.showMessageDialog(this, title + " eliminado exitosamente");
                        comboRenter.removeItem(item); // Remover del combo
                    } else {
                        JOptionPane.showMessageDialog(this, "No se pudo eliminar el " + title.toLowerCase() + ".");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un " + title.toLowerCase() + " para eliminar");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar " + title.toLowerCase() + ": " + ex.getMessage());
        }
    }

    private void cancelButtonActionPerformed() {
        returnStatus = RET_CANCEL;
        setVisible(false);
        dispose();
    }
}