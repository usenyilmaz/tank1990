package tank1990.entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet {
    public int x, y;
    public String direction;
    public int speed = 12;
    public boolean active = true;
    public BufferedImage image = null;

    public Bullet(int x, int y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void move() {
        switch (direction) {
            case "UP":    y -= speed; break;
            case "DOWN":  y += speed; break;
            case "LEFT":  x -= speed; break;
            case "RIGHT": x += speed; break;
        }
    }

    public void draw(Graphics2D g) {
        if (image != null) {
            g.drawImage(image, x, y, 8, 8, null);
        } else {
            g.setColor(Color.YELLOW);
            g.fillRect(x, y, 8, 8);
        }
    }

    // Disappear if outside panel bounds
    public void disappear(int panelWidth, int panelHeight) {
        if (x < 0 || x > panelWidth - 8 || y < 0 || y > panelHeight - 8) {
            active = false;
        }
        // Obstacle collision logic will be added later
    }

    // Stub for future collision logic
    public void hit() {
        // To be implemented: set active = false if bullet hits an obstacle or tank
    }
} 