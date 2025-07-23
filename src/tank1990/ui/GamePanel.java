package tank1990.ui;

import java.awt.*;
import javax.swing.*;
import tank1990.entity.KeyHandler;
import tank1990.entity.Player;

public class GamePanel extends JPanel implements Runnable {
    Player player;
    KeyHandler keyH;
    Thread gameThread;

    public GamePanel() {
        keyH = new KeyHandler();
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(keyH);
        this.setBackground(Color.BLACK);
        player = new Player(100, 100);
        startGameThread();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (true) {
            update();
            repaint();
            try { Thread.sleep(16); } catch (InterruptedException e) {}
        }
    }

    public void update() {
        if (keyH.wPressed) { player.direction = "UP"; player.move(); }
        if (keyH.sPressed) { player.direction = "DOWN"; player.move(); }
        if (keyH.aPressed) { player.direction = "LEFT"; player.move(); }
        if (keyH.dPressed) { player.direction = "RIGHT"; player.move(); }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        g2.dispose();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }
}
