package ui;

import connecter.LoginConnector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SavingDataPage extends JFrame {
    private LoginConnector delegate;
    private JFrame frame;
    private JPanel panel;
    private JComboBox<String> saveSlotComboBox;
    private JTable charactersTable;
    private DefaultTableModel tableModel;
    private Map<String, List<Object[]>> saveSlotData; // Maps save slots to character data lists

    public SavingDataPage(LoginConnector delegate) {
        initializeData();

        frame = new JFrame("Select Save Slot");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);

        createSaveSlotComboBox();
        createCharactersTable();

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        frame.setLocation((width - frame.getWidth()) / 2, (height - frame.getHeight()) / 2);

        frame.add(panel);
        frame.setVisible(true);

        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                delegate.switchPage(1);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    private void initializeData() {
        saveSlotData = new HashMap<>();
        saveSlotData.put("Save 1", List.of(new Object[]{"Ken", "1"}, new Object[]{"oliver", "99"}));
        saveSlotData.put("Save 2", List.of(new Object[][]{new Object[]{"Sam", "37"}}));
    }

    private void createSaveSlotComboBox() {
        saveSlotComboBox = new JComboBox<>(saveSlotData.keySet().toArray(new String[0]));
        saveSlotComboBox.setBounds(10, 10, 200, 25);
        panel.add(saveSlotComboBox);

        saveSlotComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedSaveSlot = (String) saveSlotComboBox.getSelectedItem();
                updateCharactersTable(selectedSaveSlot);
            }
        });
    }

    private void createCharactersTable() {
        String[] columnNames = {"Character Name", "Level"};
        tableModel = new DefaultTableModel(columnNames, 0);
        charactersTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(charactersTable);
        charactersTable.setFillsViewportHeight(true);

        scrollPane.setBounds(10, 50, 760, 400);
        panel.add(scrollPane);
    }

    private void updateCharactersTable(String saveSlot) {
        tableModel.setRowCount(0);
        if (saveSlot != null && saveSlotData.containsKey(saveSlot)) {
            List<Object[]> characters = saveSlotData.get(saveSlot);
            for (Object[] characterData : characters) {
                tableModel.addRow(characterData);
            }
        }
    }

    public void close() {
        frame.setVisible(false);
    }

    public void open() {
        frame.setVisible(true);
    }
}
