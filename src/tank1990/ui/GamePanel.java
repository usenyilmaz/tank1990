package tank1990.ui;

import java.awt.*;
import javax.swing.*;
import tank1990.entity.KeyHandler;
import tank1990.entity.Player;

public class GamePanel extends JPanel implements Runnable {
    Player player;
    KeyHandler keyH;

    public GamePanel() {
        this.setFocusable(true);
        keyH = new KeyHandler();
        this.addKeyListener(keyH);
        player = new Player(100, 100); // Starting position
        // Start game loop thread here...
    }

    public void update() {
        if (keyH.upPressed)    { player.direction = "UP";    player.move(); }
        if (keyH.downPressed)  { player.direction = "DOWN";  player.move(); }
        if (keyH.leftPressed)  { player.direction = "LEFT";  player.move(); }
        if (keyH.rightPressed) { player.direction = "RIGHT"; player.move(); }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        g2.dispose();
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
