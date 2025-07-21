package tank1990.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class StartPanel extends JPanel {
    public StartPanel(GameWindow window) {
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Logo Image
        ImageIcon logoIcon = null;
        java.net.URL imgURL = getClass().getResource("logo.png");
        if (imgURL != null) {
            logoIcon = new ImageIcon(imgURL);
        } else {
            System.out.println("Logo image not found at /tank1990/ui/resources/logo.png");
        }
        if (logoIcon != null && logoIcon.getIconWidth() > 0) {
            JLabel logoLabel = new JLabel(logoIcon);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 0, 20, 0);
            add(logoLabel, gbc);
        }

        // Title Label
        JLabel titleLabel = new JLabel("TANK 1990. Press Start to start the game.");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0); // Margin below label
        add(titleLabel, gbc);

        // Start Button
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 32));
        startButton.setBackground(Color.DARK_GRAY);
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.startGame();
            }
        });
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 10, 0); // Margin below button
        add(startButton, gbc);

        // Load Game Button
        JButton loadButton = new JButton("Load Game");
        loadButton.setFont(new Font("Arial", Font.BOLD, 28));
        loadButton.setBackground(Color.DARK_GRAY);
        loadButton.setForeground(Color.WHITE);
        loadButton.setFocusPainted(false);
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(StartPanel.this, "Load Game feature coming soon!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 10, 0);
        add(loadButton, gbc);

        // Exit Button
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 28));
        exitButton.setBackground(Color.DARK_GRAY);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(exitButton, gbc);
    }
} 