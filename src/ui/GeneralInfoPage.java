package ui;

import connecter.LoginConnector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GeneralInfoPage {
    private LoginConnector delegate;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private JFrame desktop;
    private JButton groupByButton, divisionButton, aggregationButton, nestedAggregationButton;
    private JScrollPane scrollPane;
    private JPanel panel;
    private DefaultTableModel tableModel;
    private JTable table;


    public GeneralInfoPage(LoginConnector delegate) {
        this.delegate = delegate;
        desktop = new JFrame("General Information Page");
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(null);
        JLabel label = new JLabel("General Information Page");
        desktop.setSize(WIDTH, HEIGHT);
        desktop.pack();
        desktop.add(panel, BorderLayout.CENTER);
        panel.add(label);
        desktop.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        desktop.setSize(WIDTH, HEIGHT);
        centreOnScreen();
        createTable();
        createButtons();
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




    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        desktop.setLocation((width - desktop.getWidth()) / 2, (height - desktop.getHeight()) / 2);
    }
    private void createButtons() {
        JButton selectButton = new JButton("select");

        selectButton.setBounds(10, 420, 100, 25);

        JComboBox<String> selectionBox = new JComboBox<String>(new String[]{"names of the characters with highest level for each role", "the maximum damage of weapons with maximum weapon damage larger than 50 for each role","the maps that have all characters with money larger than 100"});
        selectionBox.setBounds(150, 400, 400, 100);
        selectionBox.setBackground(Color.white);
        panel.add(selectionBox);


        panel.add(selectButton);
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectionBox.getSelectedItem() == "names of the characters with highest level for each role") {
                    DefaultTableModel table = delegate.groupByQuery();
                    JTable rsTable = new JTable(table);
                    scrollPane = new JScrollPane(rsTable);
                    rsTable.setFillsViewportHeight(true);
                    scrollPane.setBounds(10, 10, 760, 400);
                    panel.add(scrollPane);
                }
                if (selectionBox.getSelectedItem() == "the maximum damage of weapons with maximum weapon damage larger than 50 for each role") {
                    DefaultTableModel table = delegate.havingQuery();
                    JTable rsTable = new JTable(table);
                    scrollPane = new JScrollPane(rsTable);
                    rsTable.setFillsViewportHeight(true);
                    scrollPane.setBounds(10, 10, 760, 400);
                    panel.add(scrollPane);
                }
                if (selectionBox.getSelectedItem() == "the maps that have all characters with money larger than 100") {
                    DefaultTableModel table = delegate.findAllQuery();
                    JTable rsTable = new JTable(table);
                    scrollPane = new JScrollPane(rsTable);
                    rsTable.setFillsViewportHeight(true);
                    scrollPane.setBounds(10, 10, 760, 400);
                    panel.add(scrollPane);
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

    private void createTable() {

        tableModel = new DefaultTableModel();


        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        scrollPane.setBounds(10, 10, 760, 400);
        panel.add(scrollPane);
    }

}
