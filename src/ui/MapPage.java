package ui;
import connecter.LoginConnector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MapPage extends JFrame implements ActionListener {
    private LoginConnector delegate;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private JFrame desktop;
    private JTable MapTable;
    private JButton addButton, updateButton, deleteButton, backButton;
    private JScrollPane scrollPane;
    private JPanel panel;

    private JButton unlockedArea;
    private JButton lockedArea;
    private DefaultTableModel tableModel;


    public MapPage(LoginConnector delegate) {
        this.delegate = delegate;
        desktop = new JFrame("Map Page");
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(null);
        JLabel label = new JLabel("Map Page");
        desktop.setTitle("Map");
        desktop.setSize(WIDTH, HEIGHT);
        addButtonPanel();
        desktop.pack();
        desktop.add(panel, BorderLayout.CENTER);
        panel.add(label);
        desktop.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        desktop.setSize(WIDTH, HEIGHT);
        createMapTable();
        centreOnScreen();
        desktop.setVisible(true);

        desktop.addWindowListener(new WindowListener() {
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

    private void createMapTable() {
        String[] columnNames = {"Map ID", "Map Name", "Foggy Area"};
        tableModel = new DefaultTableModel(columnNames, 0);

        tableModel.addRow(new Object[]{1, "Map1", "Foggy Area1"});
        tableModel.addRow(new Object[]{2, "Map2", "Foggy Area2"});

        MapTable = new JTable(tableModel);
        scrollPane = new JScrollPane(MapTable);
        MapTable.setFillsViewportHeight(true);

        scrollPane.setBounds(10, 10, 760, 400);
        panel.add(scrollPane);
    }


    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        desktop.setLocation((width - desktop.getWidth()) / 2, (height - desktop.getHeight()) / 2);
    }

    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 2));
        unlockedArea = new JButton("unlockedArea");
        unlockedArea.addActionListener(this);
        lockedArea = new JButton("lockedArea");
        lockedArea.addActionListener(this);
        buttonPanel.add(unlockedArea);
        buttonPanel.add(lockedArea);
        panel.add(buttonPanel, BorderLayout.WEST);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == unlockedArea) {
            unlockedArea();
        } else if (e.getSource() == lockedArea) {
            lockedArea();
        }
    }

    private void unlockedArea() {
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(null);
        addBackButton(secondPanel);
        desktop.add(secondPanel);
        panel.setVisible(false);
        secondPanel.setVisible(true);
        createButtons();
    }

    private void lockedArea() {
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(null);
        addBackButton(secondPanel);
        desktop.add(secondPanel);
        panel.setVisible(false);
        secondPanel.setVisible(true);
        createButtons();
    }

    private void addBackButton(JPanel subPanel) {

        JButton backButton = new JButton("Back");
        subPanel.add(backButton);
        backButton.setBounds(660, 80, 80, 25);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the main panel and hide the second panel
                subPanel.setVisible(false);
                panel.setVisible(true);
            }
        });
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
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.addRow(new Object[]{1,"defaultName","defaultName"});
            }
        });


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = MapTable.getSelectedRow();
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
}


