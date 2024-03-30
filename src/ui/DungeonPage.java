package ui;

import connecter.LoginConnector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DungeonPage extends JFrame implements ActionListener {
    private LoginConnector delegate;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private JFrame desktop;
    private JTable DungeonTable;
    private JButton addButton, updateButton, deleteButton, backButton;
    private JScrollPane scrollPane;
    private JPanel panel;



    private DefaultTableModel tableModel;

    public DungeonPage(LoginConnector delegate) {
        this.delegate = delegate;
        desktop = new JFrame("Dungeon Page");
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(null);
        JLabel label = new JLabel("Dungeon Page");
        desktop.setSize(WIDTH, HEIGHT);
        desktop.pack();
        desktop.add(panel, BorderLayout.CENTER);
        panel.add(label);
        desktop.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        desktop.setSize(WIDTH, HEIGHT);
        createDungeonTable();
        createButtons();
        centreOnScreen();
        desktop.setVisible(true);

    }

    private void createDungeonTable() {
        String[] columnNames = {"Dungeon ID", "Dungeon Name", "Item", "Boss Name", "Boss", "MapID"};
        tableModel = new DefaultTableModel(columnNames, 0);

        tableModel.addRow(new Object[]{1, "Dungeon Name", "Item", "Boss Name", "Boss", "MapID"});
        tableModel.addRow(new Object[]{2, "Dungeon Name", "Item", "Boss Name", "Boss", "MapID"});

        DungeonTable = new JTable(tableModel);
        scrollPane = new JScrollPane(DungeonTable);
        DungeonTable.setFillsViewportHeight(true);

        scrollPane.setBounds(10, 10, 760, 400);
        panel.add(scrollPane);
    }

    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        desktop.setLocation((width - desktop.getWidth()) / 2, (height - desktop.getHeight()) / 2);
    }


    private void createButtons() {
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        backButton = new JButton("Back");

        addButton.setBounds(10, 420, 100, 25);
        updateButton.setBounds(120, 420, 100, 25);
        deleteButton.setBounds(230, 420, 100, 25);
        backButton.setBounds(340, 420, 100, 25);

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(backButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.addRow(new Object[]{1,"defaultName","defaultName"});
            }
        });


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = DungeonTable.getSelectedRow();
                if (selectedRow >= 0) {
                    tableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(desktop, "Please select a map to delete.");
                }
            }
        });
    }
    public void close() {
        desktop.setVisible(false);
    }

    public void open() {
        desktop.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
