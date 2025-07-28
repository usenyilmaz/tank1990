package tank1990.ui;

import javax.swing.*;

public class GameWindow extends JFrame {
    private StartPanel startPanel;
    private GamePanel gamePanel;

    public GameWindow() {
        setTitle("Tank 1990");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        startPanel = new StartPanel(this);
        setContentPane(startPanel);
        setVisible(true);
    }

    public void startGame() {
        gamePanel = new GamePanel(1); // Start with stage 1
        setContentPane(gamePanel);
        revalidate();
        repaint();
    }
}