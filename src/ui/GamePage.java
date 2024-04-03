package ui;

import connecter.LoginConnector;
import entity.Account;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

public class GamePage extends JFrame implements ActionListener{

    private LoginConnector delegate;
    private JButton button_Data, button_Char, button_Map, button_Choose, button_login;
    private JLabel name, email, birthday;
    private JComboBox<String> combobox, userCombobox;
    private JLabel language;
    private JLabel UID;
    private Account account;
    private User user;
    private JFrame frame, userFrame, accountFrame;
    private JPanel panel, userPanel, accountPanel;
    private JLabel label_email, label_password, imageLabel, imageAccLabel;
    private JTextField textField_email, textField_password;
    private DefaultTableModel tableModel;
    private JTable userTable;
    private JScrollPane scrollPane;
    private int selectedIndex;

    private final ImageIcon image, imageAcc;

    public GamePage(LoginConnector delegate) {
        this.delegate = delegate;
        image = new ImageIcon("src/image/SkyView.jpg");
//        Set up account should use sql instead of manually typed in values.
        account = new Account(100000000, "123","Chinese");
        frame = new JFrame("Main Page");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();

        UID = new JLabel("UID:" + account.getUID());
        UID.setFont(new Font("Arial", Font.PLAIN, 20));
        UID.setBounds(60, 660, 300, 20);
        panel.add(UID);

//        Should be selectable text field
        language = new JLabel("Language");
        language.setFont(new Font("Arial", Font.PLAIN, 25));
        language.setBounds(1080, 100, 300, 30);
        panel.add(language);

        combobox = new JComboBox<String>(new String[]{"Chinese", "English", "Japanese"});
        combobox.setBounds(1080, 140, 114, 30);
        panel.add(combobox);

        // user info display section
        user = new User("Devin", "Devin@gmail.com", Date.valueOf("2024-03-27"));

        name = new JLabel("Name: " + user.getName());
        name.setFont(new Font("Arial", Font.PLAIN, 30));
        name.setBounds(160, 180, 300, 30);
        panel.add(name);

        email = new JLabel("Email: " + user.getEmail());
        email.setFont(new Font("Arial", Font.PLAIN, 30));
        email.setBounds(160, 220, 350, 35);
        panel.add(email);

        birthday = new JLabel("Birthday: " + user.getBirthday());
        birthday.setFont(new Font("Arial", Font.PLAIN, 30));
        birthday.setBounds(160, 260, 300, 32);
        panel.add(birthday);

        // button display section
        button_Data = new JButton("Data");
        button_Data.setBounds(300, 440, 150, 100);
        button_Data.addActionListener(this);
        panel.add(button_Data);

        button_Char = new JButton("Characters");
        button_Char.setBounds(550, 440, 150, 100);
        button_Char.addActionListener(this);
        panel.add(button_Char);

        button_Map = new JButton("Map");
        button_Map.setBounds(800, 440, 150, 100);
        button_Map.addActionListener(this);
        panel.add(button_Map);

        centreOnScreen(frame);
        frame.add(panel);
        panel.setLayout(null);
        frame.setVisible(false);

        // Frame 2
        // Starts
        // From here
        userFrame = new JFrame("Select User");
        userFrame.setSize(500, 400);
        userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        userPanel = new JPanel();
        userPanel.setLayout(null);

//        String[] columnNames = {"Name", "Birthday", "Email"};
//        tableModel = new DefaultTableModel(columnNames, 0);
//
//        tableModel.addRow(new Object[]{"Devin", "2000-1-1", "Town@gmailc.om"});
//        tableModel.addRow(new Object[]{"Kevin", "2000-10-01", "Forest@qq.com"});
//
//        userTable = new JTable(tableModel);
//        scrollPane = new JScrollPane(userTable);
//        userTable.setFillsViewportHeight(true);

//        scrollPane.setBounds(10, 10, 460, 100);
//        userPanel.add(scrollPane);

        userCombobox = new JComboBox<String>(new String[]{"Desheng", "Xiran", "James"});
        userCombobox.setBounds(75, 120, 100, 40);
        userCombobox.setBackground(Color.white);
        userPanel.add(userCombobox);

        button_Choose = new JButton("Choose");
        button_Choose.setBounds(75, 170, 100, 40);
        userPanel.add(button_Choose);

        button_Choose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedIndex = userCombobox.getSelectedIndex();
                setUpAccountFrame(userCombobox.getItemAt(selectedIndex));
                userFrame.setVisible(false);
                accountFrame.setVisible(true);
            }
        });

        imageAcc = new ImageIcon("src/image/black.jpg");
        imageAcc.setImage(imageAcc.getImage().getScaledInstance(960,405,Image.SCALE_DEFAULT));
        imageAccLabel = new JLabel(imageAcc);
        imageAccLabel.setBounds(0, 0, 540, 365);
        userPanel.add(imageAccLabel);

        centreOnScreen(userFrame);
        userFrame.add(userPanel);
        userFrame.setVisible(true);
    }
    private void setUpAccountFrame(String user) {
        accountFrame = new JFrame("Account Login");
        accountFrame.setSize(540, 360);
        accountFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        accountPanel = new JPanel();
        accountPanel.setLayout(null);

        label_email = new JLabel(user + "'s Email:");
        label_email.setFont(new Font("Arial", Font.BOLD, 16));
        label_email.setForeground(Color.WHITE);
        label_email.setBounds(80, 110, 135, 20);
        accountPanel.add(label_email);

        label_password = new JLabel("Enter Password:");
        label_password.setFont(label_email.getFont());
        label_password.setForeground(Color.WHITE);
        label_password.setBounds(label_email.getX()+2, label_email.getY() + 40,
                label_email.getWidth(), label_email.getHeight());
        accountPanel.add(label_password);

        textField_email = new JTextField();
        textField_email.setBounds(label_email.getX() + label_email.getWidth() + 20,
                label_email.getY(), 120, label_email.getHeight());
        accountPanel.add(textField_email);

        textField_password = new JPasswordField();
        textField_password.setBounds(textField_email.getX(), label_password.getY(),
                120, label_password.getHeight());
        accountPanel.add(textField_password);

        button_login = new JButton("Login");
        button_login.setBounds(textField_email.getX() + 20, label_email.getY() + 80, 80, 22);
        button_login.setFocusPainted(false);
        button_login.addActionListener(this);
        accountPanel.add(button_login);

        image.setImage(image.getImage().getScaledInstance(540,360,Image.SCALE_DEFAULT));
        imageLabel = new JLabel(image);
        imageLabel.setBounds(0, 0, 540, 360);
        accountPanel.add(imageLabel);

        centreOnScreen(accountFrame);
        accountFrame.add(accountPanel);
        accountFrame.setVisible(true);
    }
    public void close() {
        frame.setVisible(false);
    }
    public void open() {
        frame.setVisible(true);
    }

    private void centreOnScreen(JFrame setFrame) {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setFrame.setLocation((width - setFrame.getWidth()) / 2, (height - setFrame.getHeight()) / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 1 is Game Window, 2 is Saving Data Window, 3 is Character Window, 4 is Map Window
//        if (e.getSource() == button_Data) {
//            delegate.switchPage(1);
//        }
        if (e.getSource() == button_Data) {
            delegate.switchPage(2);
        }
        if (e.getSource() == button_Char) {
            delegate.switchPage(3);
        }
        if (e.getSource() == button_Map) {
            delegate.switchPage(4);
        }
    }
}
