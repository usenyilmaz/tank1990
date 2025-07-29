package tank1990.ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import tank1990.entity.Bullet;
import tank1990.entity.KeyHandler;
import tank1990.entity.Player;
import tank1990.walls.AbstractWall;

public class GamePanel extends JPanel implements Runnable {
    Player player;
    KeyHandler keyH;
    Thread gameThread;
    List<Bullet> bullets = new ArrayList<>();
    List<AbstractWall> walls = new ArrayList<>();
    private int fps = 0;
    private int currentStageNumber;

    public GamePanel(int currentStageNumber) {
        this.currentStageNumber = currentStageNumber;
        keyH = new KeyHandler();
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(keyH);
        this.setBackground(Color.BLACK);
        player = new Player(48 * 4, 48 * 12);
        StageGenerator generator = new StageGenerator();
        walls = generator.generateStage(currentStageNumber);
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
        else if (keyH.downPressed)  { player.direction = "DOWN"; player.move(panelWidth, panelHeight); }
        else if (keyH.leftPressed)  { player.direction = "LEFT"; player.move(panelWidth, panelHeight); }
        else if (keyH.rightPressed) { player.direction = "RIGHT"; player.move(panelWidth, panelHeight); }
        // Shoot with Z
        if (keyH.zPressed) {
            Bullet b = player.shoot();
            if (b != null) {
                bullets.add(b);
            }
            keyH.zPressed = false;
        }
        // Update walls
        for (AbstractWall wall : walls) {
            wall.update();
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
        
        // Bullet-wall collision (separate loop for better control)
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            if (b != null && b.active) {
                for (AbstractWall wall : walls) {
                    if (!wall.isDestroyed() && wall.collidesWith(b.x, b.y, 8, 8)) {
                        wall.StumbleEntity(b); // Call StumbleEntity for bullets
                        // Only destroy bullet and explode wall if wall is destructible
                        if (wall.isDestructible()) {
                            wall.Explode();
                            b.active = false;
                        }
                        // If wall is not destructible (like water), bullet continues
                        break;
                    }
                }
            }
        }
        
        // Player-wall collision
        for (AbstractWall wall : walls) {
            if (!wall.isDestroyed() && wall.collidesWith(player.getX(), player.getY(), player.getWidth(), player.getHeight())) {
                wall.StumbleEntity(player); // Call StumbleEntity for player
                // Only reset player position if wall is destructible (blocks movement)
                if (wall.isDestructible()) {
                   player.setPosition(player.getPrevX(), player.getPrevY());
                }
                break;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // Draw walls first
        for (AbstractWall wall : walls) {
            wall.draw(g2);
        }
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
