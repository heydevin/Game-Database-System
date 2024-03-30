package ui;

import connecter.LoginConnector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class CharacterPage extends JFrame {
    private LoginConnector delegate;
    private JFrame frame;
    private JPanel panel;
    private JTable characterTable;
    private JButton addButton, updateButton, deleteButton, backButton;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;

    public CharacterPage(LoginConnector delegate) {
        this.delegate = delegate;

        frame = new JFrame("Character Management");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);

        createCharacterTable();
        createButtons();

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

    private void createCharacterTable() {
        String[] columnNames = {"Character Name", "Level", "HP", "Money", "Role", "Current Location"};
        tableModel = new DefaultTableModel(columnNames, 0);

        tableModel.addRow(new Object[]{"Peter", 5, 150, 200, "Warrior", "Town"});
        tableModel.addRow(new Object[]{"Tom", 8, 200, 300, "Mage", "Forest"});

        characterTable = new JTable(tableModel);
        scrollPane = new JScrollPane(characterTable);
        characterTable.setFillsViewportHeight(true);

        scrollPane.setBounds(10, 10, 760, 400);
        panel.add(scrollPane);
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
                tableModel.addRow(new Object[]{"defaultName", 1, 100, 0, null, "Home"});
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = characterTable.getSelectedRow();
                if (selectedRow >= 0) {
                    tableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a character to delete.");
                }
            }
        });
    }
    public void close() {
        frame.setVisible(false);
    }

    public void open() {
        frame.setVisible(true);
    }
}

