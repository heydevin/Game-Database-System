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
import java.util.List;

public class CharacterPage extends JFrame {
    private LoginConnector delegate;
    private JFrame frame;
    private JPanel panel;
    private JTable characterTable;
    private JButton addButton, updateButton, deleteButton, backButton;
    private JScrollPane scrollPane;
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;


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


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int levelInput = Integer.parseInt(level.getText());
                int moneyInput = Integer.parseInt(money.getText());
                String cnameInput = cname.getText();
                String rnameInput = rname.getText();
                String mapIdInput = mapID.getText();
                String currLocInput = currLoc.getText();
                CharacterInfo characterInfo = new CharacterInfo(levelInput,moneyInput,cnameInput,rnameInput,mapIdInput,currLocInput);
                insertCharacter_Info(characterInfo);
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
                int selectedRow = characterTable.getSelectedRow();
                if (selectedRow >= 0) {
                    tableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a character to delete.");
                }
            }
        });
    }

    public void insertCharacter_Info(CharacterInfo characterInfo) {
        try {
            String query = "INSERT INTO Character_Info VALUES (?,?,?,?,?,?)";
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