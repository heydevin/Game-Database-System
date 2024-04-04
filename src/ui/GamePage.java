package ui;

import connecter.LoginConnector;
import entity.Account;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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
    private JLabel label_email, label_password, imageLabel, imageLabelBH, imageLabelLand;
    private JTextField textField_email, textField_password;
    private DefaultTableModel tableModel;
    private JTable userTable;
    private JScrollPane scrollPane;
    private int selectedIndex;

    private final ImageIcon imageSky, imageBlackHole, imageLand;

    public GamePage(LoginConnector delegate) {
        this.delegate = delegate;
        imageSky = new ImageIcon("src/image/SkyView.jpg");
        imageBlackHole = new ImageIcon("src/image/black.jpg");
        imageLand = new ImageIcon("src/image/Land.jpg");

        userFrame = new JFrame("Select User");
        userFrame.setSize(500, 400);
        userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        userPanel = new JPanel();
        userPanel.setLayout(null);

        userCombobox = new JComboBox<String>(new String[]{"Desheng", "Xiran", "James"});
        userCombobox.setBounds(75, 120, 105, 40);
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

        imageBlackHole.setImage(imageBlackHole.getImage().getScaledInstance(960,405,Image.SCALE_DEFAULT));
        imageLabelBH = new JLabel(imageBlackHole);
        imageLabelBH.setBounds(0, 0, 540, 380);
        userPanel.add(imageLabelBH);

        centreOnScreen(userFrame);
        userFrame.add(userPanel);
        userFrame.setVisible(true);
    }

    private void setUpMainPage(User chosenUser, Account accountOwnByUser) {
        //        Set up account should use sql instead of manually typed in values.
        user = chosenUser;
        account = accountOwnByUser;

        frame = new JFrame("Game Database System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);

        name = new JLabel("Welcome, " + user.getName() + "!");
        name.setFont(new Font("Arial", Font.BOLD, 40));
        name.setForeground(new Color(96, 96, 96));
        name.setBounds(200, 130, 400, 45);
        panel.add(name);

        birthday = new JLabel("Birthday: " + user.getBirthday());
        birthday.setFont(new Font("Arial", Font.PLAIN, 30));
        birthday.setForeground(new Color(102, 178, 255));
        birthday.setBounds(10, 0, 300, 35);
        panel.add(birthday);

        UID = new JLabel("UID:" + account.getUID());
        UID.setFont(new Font("Arial", Font.PLAIN, 30));
        UID.setForeground(new Color(102, 178, 255));
        UID.setBounds(10, 530, 300, 35);
        panel.add(UID);

        language = new JLabel("Language: " + account.getLanguage());
        language.setFont(new Font("Arial", Font.PLAIN, 30));
        language.setForeground(new Color(102, 178, 255));
        language.setBounds(510, 0, 300, 35);
        panel.add(language);

//        combobox = new JComboBox<String>(new String[]{"Chinese", "English", "Japanese"});
//        combobox.setBounds(1080, 140, 114, 30);
//        panel.add(combobox);

        // user info display section
//        email = new JLabel("Email: " + user.getEmail());
//        email.setFont(new Font("Arial", Font.BOLD, 30));
//        email.setForeground(Color.WHITE);
//        email.setBounds(160, 220, 350, 35);
//        panel.add(email);

        // button display section
        button_Data = new JButton("Data");
        button_Data.setBounds(90, 280, 150, 100);
        button_Data.addActionListener(this);
        panel.add(button_Data);

        button_Char = new JButton("Characters");
        button_Char.setBounds(button_Data.getX()+230, button_Data.getY(), 150, 100);
        button_Char.addActionListener(this);
        panel.add(button_Char);

        button_Map = new JButton("Map");
        button_Map.setBounds(button_Char.getX()+230, button_Data.getY(), 150, 100);
        button_Map.addActionListener(this);
        panel.add(button_Map);

//        imageLand.setImage(imageLand.getImage().getScaledInstance(1280,603,Image.SCALE_DEFAULT));
//        imageLabelLand = new JLabel(imageLand);
//        imageLabelLand.setBounds(0, 0, 1280, 603);
//        panel.add(imageLabelLand);

        centreOnScreen(frame);
        frame.add(panel);
        frame.setVisible(false);
    }

    private void setUpAccountFrame(String user) {
        accountFrame = new JFrame("Account Login");
        accountFrame.setSize(540, 360);
        accountFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

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

        // enter the email here from sql
        textField_email = new JTextField(delegate.getUserFromSQL(user).getEmail());
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
        button_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField_password.getText().equals(delegate.getAccountFromSQL(delegate.getUserFromSQL(user).getEmail()).getPassword())){
                    accountFrame.setVisible(false);
                    setUpMainPage(delegate.getUserFromSQL(user), delegate.getAccountFromSQL(delegate.getUserFromSQL(user).getEmail()));
                    frame.setVisible(true);
                } else {
                    System.out.println("Incorrect Password");
                }
            }
        });
        accountPanel.add(button_login);

        imageSky.setImage(imageSky.getImage().getScaledInstance(540,360,Image.SCALE_DEFAULT));
        imageLabel = new JLabel(imageSky);
        imageLabel.setBounds(0, 0, 540, 360);
        accountPanel.add(imageLabel);

        centreOnScreen(accountFrame);

        accountFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                userFrame.setVisible(true);
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
