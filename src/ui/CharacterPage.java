package ui;

import connecter.LoginConnector;
import entity.CharacterInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.SQLException;
import util.PrintablePreparedStatement;

public class CharacterPage extends JFrame {
    private LoginConnector delegate;
    private JFrame frame, addCharFrame;
    private JPanel panel, addCharPanel;
    private JTable characterTable;
    private JButton addButton, updateButton, deleteButton, backButton, insertButton;
    private JScrollPane scrollPane;
    private JLabel characterName, selectRoleLabel, selectLocationLabel;
    private JComboBox<String> rolesBox, locationsBox;
    private JTextField textField_charName;
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;

    private JButton affordableWeaponsButton;
    private DefaultTableModel tableModel;
    private JLabel updateTips;
    private JTextField textField_updateLevel;
    private JButton levelUpButton;

    public CharacterPage(LoginConnector delegate) {
        this.delegate = delegate;

        frame = new JFrame("Character Management");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);

        createCharacterTable();
        createButtons();

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

    // New window for insert characters
    private void setUpAddCharacterPanel() {
        addCharFrame = new JFrame("Insert New Character");
        addCharFrame.setSize(600,400);
        addCharFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        addCharPanel = new JPanel();
        addCharPanel.setLayout(null);

        characterName = new JLabel("Enter New Character Name: ");
        characterName.setFont(new Font("Arial", Font.PLAIN, 15));
        characterName.setForeground(Color.BLACK);
        characterName.setBounds(20, 80, 250, 30);
        addCharPanel.add(characterName);

        textField_charName = new JTextField();
        textField_charName.setBounds(characterName.getX()+200, characterName.getY(), 200, 30);
        addCharPanel.add(textField_charName);

        rolesBox = new JComboBox<String>(new String[]{"Warrior", "Assassin", "Mage", "Archer", "Berserker"});
        rolesBox.setBounds(textField_charName.getX(), 140, 100, 30);
        rolesBox.setBackground(Color.white);
        addCharPanel.add(rolesBox);

        locationsBox = new JComboBox<String>(new String[]{"Town", "Forest", "Ocean", "Desert", "Highland"});
        locationsBox.setBounds(textField_charName.getX(), 200, 100, 30);
        locationsBox.setBackground(Color.white);
        addCharPanel.add(locationsBox);

        selectRoleLabel = new JLabel("Select New Role: ");
        selectRoleLabel.setFont(characterName.getFont());
        selectRoleLabel.setForeground(Color.BLACK);
        selectRoleLabel.setBounds(70, rolesBox.getY(), 250, 30);
        addCharPanel.add(selectRoleLabel);

        selectLocationLabel = new JLabel("Select New Location: ");
        selectLocationLabel.setFont(characterName.getFont());
        selectLocationLabel.setForeground(Color.BLACK);
        selectLocationLabel.setBounds(50, locationsBox.getY(), 250, 30);
        addCharPanel.add(selectLocationLabel);

        insertButton = new JButton("Insert");
        insertButton.setBounds(locationsBox.getX() + 50, locationsBox.getY() + 40, 80, 22);
        insertButton.setFocusPainted(false);
        addCharPanel.add(insertButton);
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField_charName.getText().equals("") || textField_charName.getText() == null) {
                    JOptionPane.showMessageDialog(addCharFrame, "Please enter character name");
                } else {
                    CharacterInfo newChar = new CharacterInfo(1, 999, textField_charName.getText(),
                            rolesBox.getItemAt(rolesBox.getSelectedIndex()),
                            locationsBox.getItemAt(locationsBox.getSelectedIndex()));
                    delegate.insertCharacterIntoSQL(newChar);
                    tableModel.addRow(new Object[]{textField_charName.getText(), 1, 999,
                            rolesBox.getItemAt(rolesBox.getSelectedIndex()),
                            locationsBox.getItemAt(locationsBox.getSelectedIndex())});
                    addCharFrame.setVisible(false);
                }
            }
        });

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        addCharFrame.setLocation((width - addCharFrame.getWidth()) / 2, (height - addCharFrame.getHeight()) / 2);

        addCharFrame.add(addCharPanel);
        addCharFrame.setVisible(true);
    }

    private void createCharacterTable() {
        String[] columnNames = {"Character Name", "Level", "Money", "Role", "Current Location"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // add new characters
        tableModel.addRow(new Object[]{"Bobby", 15, 100, "Warrior", "Ocean"});
        tableModel.addRow(new Object[]{"Austin", 40, 200, "Assassin", "Town"});
        tableModel.addRow(new Object[]{"Carols", 33, 330, "Mage", "Town"});
        tableModel.addRow(new Object[]{"Katty", 44, 700, "Warrior", "Town"});
        tableModel.addRow(new Object[]{"Jones", 35, 220, "Archer", "Town"});
        tableModel.addRow(new Object[]{"Duke", 72, 90, "Archer", "Desert"});
        tableModel.addRow(new Object[]{"Julia", 18, 170, "Berserker", "Highland"});

        characterTable = new JTable(tableModel);
        scrollPane = new JScrollPane(characterTable);
        characterTable.setFillsViewportHeight(true);
        characterTable.setDefaultEditor(Object.class, null);

        scrollPane.setBounds(10, 10, 760, 400);
        panel.add(scrollPane);
    }

    private void createButtons() {
        addButton = new JButton("Insert");
        updateButton = new JButton("Update Money");
        deleteButton = new JButton("Delete");
        backButton = new JButton("Back");

        addButton.setBounds(10, 420, 100, 25);
        deleteButton.setBounds(120, 420, 100, 25);
        updateButton.setBounds(230, 420, 120, 25);
        backButton.setBounds(360, 420, 100, 25);

        updateTips = new JLabel("Enter New Money Value Here:");
        updateTips.setBounds(80, 455, 200,25);
        textField_updateLevel = new JTextField();
        textField_updateLevel.setBounds(120+140,updateTips.getY(),60,updateTips.getHeight());

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(backButton);

        panel.add(updateTips);
        panel.add(textField_updateLevel);

        affordableWeaponsButton = new JButton("Affordable Weapons");
        affordableWeaponsButton.setBounds(backButton.getX() + backButton.getWidth() + 20, 420, 160, 25);
        panel.add(affordableWeaponsButton);

        levelUpButton = new JButton("Level Up");
        levelUpButton.setBounds(backButton.getX() + backButton.getWidth() + 190, 420, 100, 25);
        panel.add(levelUpButton);

        JLabel levelText = new JLabel("level");
        levelText.setBounds(300, 45, 60, 25);
        JTextField level = new JTextField(20);
        level.setBounds(300, 80, 60, 25);
        JLabel moneyText = new JLabel("money");
        moneyText.setBounds(370, 45, 60, 25);
        JTextField money = new JTextField(20);
        money.setBounds(370, 80, 60, 25);
        JLabel cnameText = new JLabel("character name");
        cnameText.setBounds(440, 45, 60, 25);
        JTextField cname = new JTextField(20);
        cname.setBounds(440, 80, 60, 25);
        JLabel rnameText = new JLabel("role name");
        rnameText.setBounds(510, 45, 60, 25);
        JTextField rname = new JTextField(20);
        rname.setBounds(510, 80, 60, 25);
        JLabel mapIDText = new JLabel("map ID");
        mapIDText.setBounds(580, 45, 60, 25);
        JTextField mapID = new JTextField(20);
        mapID.setBounds(580, 80, 60, 25);
        JLabel currLocText = new JLabel("current location");
        currLocText.setBounds(650, 45, 60, 25);
        JTextField currLoc = new JTextField(20);
        currLoc.setBounds(650, 80, 60, 25);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(characterTable.getSelectionModel().isSelectionEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please select or create a character before you update.");
                } else if(textField_updateLevel.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter the new money value for character before you update.");
                } else {
                    String name = tableModel.getValueAt(characterTable.getSelectedRow(), 0).toString();
                    delegate.updateCharacterMoney(Integer.parseInt(textField_updateLevel.getText()), name);
                    tableModel.setValueAt(textField_updateLevel.getText(), characterTable.getSelectedRow(), 2);
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                int levelInput = Integer.parseInt(level.getText());
//                int moneyInput = Integer.parseInt(money.getText());
//                String cnameInput = cname.getText();
//                String rnameInput = rname.getText();
//                String mapIdInput = mapID.getText();
//                String currLocInput = currLoc.getText();
//                CharacterInfo characterInfo = new CharacterInfo(levelInput,moneyInput,cnameInput,rnameInput,mapIdInput,currLocInput);
//                insertCharacter_Info(characterInfo);
                setUpAddCharacterPanel();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delegate.switchPage(1);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(characterTable.getSelectionModel().isSelectionEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please select or create a character before you delete.");
                } else{
                    String name = tableModel.getValueAt(characterTable.getSelectedRow(),0).toString();
                    System.out.println(name);
                    delegate.deleteCharacterInfoFromSQL(name);
                    tableModel.removeRow(characterTable.getSelectedRow());
                }
            }
        });

        affordableWeaponsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(characterTable.getSelectionModel().isSelectionEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please select a character.");
                } else{
                    String characterName = tableModel.getValueAt(characterTable.getSelectedRow(),0).toString();
                    String[] affordableWeapons = delegate.getAffordableWeapons(characterName);
                    //show the affordableWeapons to user
                    JOptionPane.showMessageDialog(frame, "Affordable weapons for " + characterName + ": " + String.join(", ", affordableWeapons));
                }
            }
        });

        levelUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(characterTable.getSelectionModel().isSelectionEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please select a character before you level up.");
                } else{
                    int currentLevel = Integer.parseInt(tableModel.getValueAt(characterTable.getSelectedRow(),1).toString());
                    currentLevel++; // Increment Level
                    String name = tableModel.getValueAt(characterTable.getSelectedRow(),0).toString();
                    delegate.updateCharacterLevel(currentLevel, name);
                    tableModel.setValueAt(String.valueOf(currentLevel), characterTable.getSelectedRow(), 1);
                }
            }
        });

    }

    public void insertCharacter_Info(CharacterInfo characterInfo) {
        try {
            String query = "INSERT INTO Characters_Info VALUES (?,?,?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, characterInfo.getLevel());
            ps.setInt(2, characterInfo.getMoney());
            ps.setString(3, characterInfo.getCharacterName());
            ps.setString(4, characterInfo.getRoleName());
            ps.setString(5, characterInfo.getMapID());
            ps.setString(6, characterInfo.getCurrLoc());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }
    public void close() {
        frame.setVisible(false);
    }

    public void open() {
        frame.setVisible(true);
    }
}