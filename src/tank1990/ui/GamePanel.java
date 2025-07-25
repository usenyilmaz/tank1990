package tank1990.ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import tank1990.entity.Bullet;
import tank1990.entity.KeyHandler;
import tank1990.entity.Player;

public class GamePanel extends JPanel implements Runnable {
    Player player;
    KeyHandler keyH;
    Thread gameThread;
    List<Bullet> bullets = new ArrayList<>();
    private int fps = 0;

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
        long lastTime = System.currentTimeMillis();
        int frames = 0;
        while (true) {
            update();
            repaint();
            frames++;
            long now = System.currentTimeMillis();
            if (now - lastTime >= 1000) {
                fps = frames;
                frames = 0;
                lastTime = now;
            }
            try { Thread.sleep(16); } catch (InterruptedException e) {}
        }
    }

    public void update() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        // Yön tuşları
        if (keyH.upPressed)    { player.direction = "UP"; player.move(panelWidth, panelHeight); }
        if (keyH.downPressed)  { player.direction = "DOWN"; player.move(panelWidth, panelHeight); }
        if (keyH.leftPressed)  { player.direction = "LEFT"; player.move(panelWidth, panelHeight); }
        if (keyH.rightPressed) { player.direction = "RIGHT"; player.move(panelWidth, panelHeight); }
        // Shoot with Z
        if (keyH.zPressed) {
            Bullet b = player.shoot();
            if (b != null) {
                bullets.add(b);
            }
            keyH.zPressed = false;
        }
        // Update bullets
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            if(b != null){
                if (b.active) {
                    b.move();
                    b.disappear(panelWidth, panelHeight);
                } else {
                    bullets.remove(i);
                    i--;
                }
            }
            
            
            
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        for (Bullet b : bullets) {
            if (b.active) b.draw(g2);
        }
        // Draw FPS at top right
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        String fpsText = "FPS: " + fps;
        int textWidth = g2.getFontMetrics().stringWidth(fpsText);
        g2.drawString(fpsText, getWidth() - textWidth - 10, 20);
        g2.dispose();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }
}
