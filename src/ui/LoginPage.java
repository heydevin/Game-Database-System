package ui;

import connecter.LoginConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame implements ActionListener {
    private final ImageIcon image;
    private final JLabel imageLabel;
    private JFrame frame;
    private JPanel panel;
    private JLabel label_username, label_password;
    private JTextField textField_username, textField_password;
    private JButton button_login;
    private LoginConnector delegate;

    public LoginPage(LoginConnector delegate) {
        this.delegate = delegate;
        frame = new JFrame("Database Login");
        frame.setSize(540, 360);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();

        label_username = new JLabel("Username");
        label_username.setFont(new Font("Arial", Font.PLAIN, 16));
        label_username.setForeground(Color.WHITE);
        label_username.setBounds(140, 110, 75, 20);
        panel.add(label_username);

        label_password = new JLabel("Password");
        label_password.setFont(label_username.getFont());
        label_password.setForeground(Color.WHITE);
        label_password.setBounds(label_username.getX(), label_username.getY() + 40,
                label_username.getWidth(), label_username.getHeight());
        panel.add(label_password);

        textField_username = new JTextField();
        textField_username.setBounds(label_username.getX() + label_username.getWidth() + 10,
                label_username.getY(), 120, label_username.getHeight());
        panel.add(textField_username);

        textField_password = new JPasswordField();
        textField_password.setBounds(textField_username.getX(), label_password.getY(),
                120, label_password.getHeight());
        panel.add(textField_password);

        button_login = new JButton("Login");
        button_login.setBounds(textField_username.getX() + 20, label_username.getY() + 80, 80, 22);
        button_login.setFocusPainted(false);
        button_login.addActionListener(this);
        panel.add(button_login);

        image = new ImageIcon("src/image/Oracle.png");
        image.setImage(image.getImage().getScaledInstance(718,380,Image.SCALE_DEFAULT));
        imageLabel = new JLabel(image);
        imageLabel.setBounds(0, 0, 540, 360);
        panel.add(imageLabel);

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        frame.setLocation((width - frame.getWidth()) / 2, (height - frame.getHeight()) / 2);

        frame.add(panel);
        panel.setLayout(null);
        frame.setVisible(true);
    }

    public void handleLoginFailed() {
        textField_password.setText(""); // clear password field
    }

    public void closeLogin() {
        frame.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Trying to log in...");
        delegate.login(textField_username.getText(), String.valueOf(textField_password.getText()));
    }
}
