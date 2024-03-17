package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SavingData extends JFrame {
    private JFrame frame;
    private JPanel panel;
    private JComboBox<String> saveSlotComboBox;
    private JTable charactersTable;
    private DefaultTableModel tableModel;
    private Map<String, List<Object[]>> saveSlotData; // Maps save slots to character data lists

    public SavingData() {
        initializeData();

        frame = new JFrame("Select Save Slot");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);

        createSaveSlotComboBox();
        createCharactersTable();

        frame.add(panel);
        frame.setVisible(true);
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

    public static void main(String[] args) {
        new SavingData();
    }
}
