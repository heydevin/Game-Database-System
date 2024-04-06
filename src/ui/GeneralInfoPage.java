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
    private static final int HEIGHT = 720;
    private JFrame desktop;
    private JButton groupByButton, divisionButton, aggregationButton, nestedAggregationButton;
    private JButton projectionButton;
    private JCheckBox CharacterName, CharacterLevel, Money, RoleName, MapID, Location;
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
        panel.add(label);
        desktop.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        desktop.setSize(WIDTH, HEIGHT);
        centreOnScreen();
        createTable();
        createButtons();
        desktop.add(panel, BorderLayout.CENTER);
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
        JButton selectButton = new JButton("Select");
        projectionButton = new JButton("Projection");

        CharacterName = new JCheckBox("Character Name");
        CharacterLevel = new JCheckBox("Character Level");
        Money = new JCheckBox("Character Money");
        RoleName = new JCheckBox("RoleName");
        MapID = new JCheckBox("Map ID Of Character Location");
        Location = new JCheckBox("Character Location");

        CharacterName.setBounds(180,420,130,20);
        CharacterLevel.setBounds(350,420,130,20);
        Money.setBounds(580,420,130,20);
        RoleName.setBounds(180,450,130,20);
        MapID.setBounds(350,450,200,20);
        Location.setBounds(580,450,160,20);

        panel.add(CharacterName);
        panel.add(CharacterLevel);
        panel.add(Money);
        panel.add(RoleName);
        panel.add(MapID);
        panel.add(Location);


        projectionButton.setBounds(30, 420, 100, 40);
        projectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] columns = new String[]{};
                String[] tableColumns = new String[]{};
                int countCheckBox = 0;
                if(CharacterName.isSelected()){
                    countCheckBox++;
                }
                if(CharacterLevel.isSelected()){
                    countCheckBox++;
                }
                if(Money.isSelected()){
                    countCheckBox++;
                }
                if(RoleName.isSelected()){
                    countCheckBox++;
                }
                if(MapID.isSelected()){
                    countCheckBox++;
                }
                if(Location.isSelected()){
                    countCheckBox++;
                }
                if(countCheckBox == 1){
                    columns = new String[]{"box"};
                } else if(countCheckBox == 2){
                    columns = new String[]{"box","box"};
                } else if(countCheckBox == 3){
                    columns = new String[]{"box","box","box"};
                } else if(countCheckBox == 4){
                    columns = new String[]{"box","box","box","box"};
                } else if(countCheckBox == 5){
                    columns = new String[]{"box","box","box","box","box"};
                } else if(countCheckBox == 6){
                    columns = new String[]{"box","box","box","box","box","box"};
                }
                if(countCheckBox >= 1 && countCheckBox <=6){
                    int i = 0;
                    if(CharacterName.isSelected()){
                        columns[i] = "Cname";
                        i++;
                    }
                    if(CharacterLevel.isSelected()){
                        columns[i] = "charLevel";
                        i++;
                    }
                    if(Money.isSelected()){
                        columns[i] = "Money";
                        i++;
                    }
                    if(RoleName.isSelected()){
                        columns[i] = "Rname";
                        i++;
                    }
                    if(MapID.isSelected()){
                        columns[i] = "MapID";
                        i++;
                    }
                    if(Location.isSelected()){
                        columns[i] = "currLoc";
                        i++;
                    }
                    System.out.println(columns.length);
                    DefaultTableModel table = delegate.getProjectionFromDB(columns);
                    JTable rsTable = new JTable(table);
                    scrollPane = new JScrollPane(rsTable);
                    rsTable.setFillsViewportHeight(true);
                    scrollPane.setBounds(10, 10, 760, 400);
                    panel.add(scrollPane);
                    panel.revalidate();
                    panel.repaint();
                }
            }
        });

        selectButton.setBounds(30, 550, 100, 40);

        JComboBox<String> selectionBox = new JComboBox<String>(new String[]{"[Aggregation with Group By] Display Maximum Character Level From Each Role",
                "[Aggregation With Having] Display Maximum Weapon Damage From Each Role",
                "[Division] Show Map That Includes ALL Characters With Money Value Over 100",
                "[Join] Display Character's Role And Weapon Information"});
        selectionBox.setBounds(160, 550, 600, 40);
        selectionBox.setBackground(Color.white);
        panel.add(selectionBox);

        panel.add(projectionButton);
        panel.add(selectButton);
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectionBox.getSelectedItem() == "[Aggregation with Group By] Display Maximum Character Level From Each Role") {
                    if(scrollPane != null) {
                        panel.remove(scrollPane);
                    }
                    DefaultTableModel table = delegate.groupByQuery();
                    JTable rsTable = new JTable(table);
                    scrollPane = new JScrollPane(rsTable);
                    rsTable.setFillsViewportHeight(true);
                    scrollPane.setBounds(10, 10, 760, 400);
                    panel.add(scrollPane);
                    panel.revalidate();
                    panel.repaint();
                }
                if (selectionBox.getSelectedItem() == "[Aggregation With Having] Display Maximum Weapon Damage From Each Role") {
                    if(scrollPane != null) {
                        panel.remove(scrollPane);
                    }
                    DefaultTableModel table = delegate.havingQuery();
                    JTable rsTable = new JTable(table);
                    scrollPane = new JScrollPane(rsTable);
                    rsTable.setFillsViewportHeight(true);
                    scrollPane.setBounds(10, 10, 760, 400);
                    panel.add(scrollPane);
                    panel.revalidate();
                    panel.repaint();
                }
                if (selectionBox.getSelectedItem() == "[Division] Show Map That Includes ALL Characters With Money Value Over 100") {
                    if(scrollPane != null) {
                        panel.remove(scrollPane);
                    }
                    DefaultTableModel table = delegate.findAllQuery();
                    JTable rsTable = new JTable(table);
                    scrollPane = new JScrollPane(rsTable);
                    rsTable.setFillsViewportHeight(true);
                    scrollPane.setBounds(10, 10, 760, 400);
                    panel.add(scrollPane);
                    panel.revalidate();
                    panel.repaint();
                } else if (selectionBox.getSelectedItem() == "[Join] Display Character's Role And Weapon Information") {
                    String charName = JOptionPane.showInputDialog(desktop, "Please enter the character name:");
                    if (charName != null) {
                        DefaultTableModel table = delegate.getCharacterWeaponByRole(charName);
                        if(scrollPane != null) {
                            panel.remove(scrollPane);
                        }
                        JTable rsTable = new JTable(table);
                        scrollPane = new JScrollPane(rsTable);
                        rsTable.setFillsViewportHeight(true);
                        scrollPane.setBounds(10, 10, 760, 400);
                        panel.add(scrollPane);
                        panel.revalidate();
                        panel.repaint();
                    }
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
