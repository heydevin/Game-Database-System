package ui;

import connecter.LoginConnector;
import entity.Account;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePage extends JFrame implements ActionListener{

    private LoginConnector delegate;
    private JButton button_Data, button_Char, button_Map;
    private JLabel name, email, birthday;
    private JComboBox<String> combobox;
    private JLabel language;
    private JLabel UID;
    private Account account;
    private User user;
    private JFrame frame;
    private JPanel panel;

    public GamePage(LoginConnector delegate) {
        this.delegate = delegate;
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
//        getLanguage = new JLabel(account.getLanguage());
//        getLanguage.setFont(new Font("Arial", Font.PLAIN, 20));
        combobox.setBounds(1080, 140, 114, 30);
        panel.add(combobox);

        // user info display section
        user = new User("Devin", "Devin@gmail.com", "2024-03-27");

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

        frame.add(panel);
        panel.setLayout(null);
        frame.setVisible(true);

    }
    public void close() {
        frame.setVisible(false);
    }
    public void open() {
        frame.setVisible(true);
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
