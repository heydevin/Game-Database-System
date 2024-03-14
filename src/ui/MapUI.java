package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapUI extends JFrame implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private JFrame desktop;
    private JPanel panel;

    private JButton unlockedArea;
    private JButton lockedArea;

    public MapUI() {
        desktop = new JFrame();
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        JLabel label = new JLabel("Map");
        desktop.setTitle("Map");
        desktop.setSize(WIDTH, HEIGHT);
        addButtonPanel();
        desktop.pack();
        desktop.add(panel, BorderLayout.CENTER);
        panel.add(label);
        desktop.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        desktop.setSize(WIDTH, HEIGHT);
        centreOnScreen();
        desktop.setVisible(true);
    }


    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        desktop.setLocation((width - desktop.getWidth()) / 2, (height - desktop.getHeight()) / 2);
    }

    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 2));
        unlockedArea = new JButton("load");
        unlockedArea.addActionListener(this);
        lockedArea = new JButton("search");
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
    }

    private void lockedArea() {
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(null);
        addBackButton(secondPanel);
        desktop.add(secondPanel);
        panel.setVisible(false);
        secondPanel.setVisible(true);
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
}


