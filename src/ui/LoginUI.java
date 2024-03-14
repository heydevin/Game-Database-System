package ui;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {
    private final ImageIcon image;
    private final JLabel imageLabel;
    private JFrame frame;
    private JPanel panel;
    private JLabel label_username, label_password;
    private JTextField textField_username, passwordField_password;
    private JButton button_login;
    public LoginUI() {
        frame = new JFrame("Login Page");
        frame.setSize(540, 360);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();

        label_username = new JLabel("Username");
        label_username.setFont(new Font("Arial", Font.PLAIN, 14));
        label_username.setForeground(Color.WHITE);
        label_username.setBounds(130, 110, 70, 20);
        panel.add(label_username);

        label_password = new JLabel("Password");
        label_password.setFont(label_username.getFont());
        label_password.setForeground(Color.WHITE);
        label_password.setBounds(label_username.getX(), label_username.getY() + 40,
                label_username.getWidth(), label_username.getHeight());
        panel.add(label_password);

        textField_username = new JTextField();
        textField_username.setBounds(label_username.getX() + label_username.getWidth() + 20,
                label_username.getY(), 120, label_username.getHeight());
        panel.add(textField_username);

        passwordField_password = new JPasswordField();
        passwordField_password.setBounds(textField_username.getX(), label_password.getY(),
                120, label_password.getHeight());
        panel.add(passwordField_password);

        button_login = new JButton("Login");
        button_login.setBounds(textField_username.getX() + 20, label_username.getY() + 80, 80, 22);
        button_login.setFocusPainted(false);
        panel.add(button_login);

        image = new ImageIcon("src/image/SkyView.jpg");
        image.setImage(image.getImage().getScaledInstance(540,360,Image.SCALE_DEFAULT));
        imageLabel = new JLabel(image);
        imageLabel.setBounds(0, 0, 540, 360);
        panel.add(imageLabel);

        frame.add(panel);
        panel.setLayout(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginUI();
    }
}
