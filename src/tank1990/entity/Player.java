package tank1990.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class Player implements Entity {
    public int x, y;         // Position
    public int speed = 4;    // Movement speed
    public String direction = "UP"; // "UP", "DOWN", "LEFT", "RIGHT"
    Map<String, BufferedImage> directionToImage = new HashMap<>();

    private long lastShotTime = 0;
    private final long fireRate = 300; // milliseconds

    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        loadImages();
    }

    // Entity interface methods
    @Override
    public int getX() { return x; }
    
    @Override
    public int getY() { return y; }
    
    @Override
    public int getWidth() { return 48; }
    
    @Override
    public int getHeight() { return 48; }
    
    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private void loadImages() {
        loadImageForDirection("UP", "tankup.png");
        loadImageForDirection("DOWN", "tankdown.png");
        loadImageForDirection("LEFT", "tankleft.png");
        loadImageForDirection("RIGHT", "tankright.png");
    }

    private void loadImageForDirection(String dir, String resourcePath) {
        try {
            java.net.URL imgURL = getClass().getResource(resourcePath);
            if (imgURL != null) {
                directionToImage.put(dir, ImageIO.read(imgURL));
            } else {
                System.err.println("Could not find " + resourcePath);
            }
        } catch (IOException e) {
            System.err.println("Could not load image for direction " + dir + ": " + resourcePath);
        }
    }

    public void move(int panelWidth, int panelHeight) {
        int newX = x, newY = y;
        switch (direction) {
            case "UP":    newY -= speed; break;
            case "DOWN":  newY += speed; break;
            case "LEFT":  newX -= speed; break;
            case "RIGHT": newX += speed; break;
        }
        // Clamp to boundaries
        if (newX >= 0 && newX <= panelWidth - 48 && newY >= 0 && newY <= panelHeight - 48) {
            x = newX;
            y = newY;
        }
    }

    public Bullet shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShotTime < fireRate) {
            return null; // Too soon to shoot again
        }
        lastShotTime = now;
        int bulletX = x + 20; // Center bullet horizontally in 48x48 tank
        int bulletY = y + 20; // Center bullet vertically in 48x48 tank
        switch (direction) {
            case "UP":    bulletX = x + 20; bulletY = y; break;
            case "DOWN":  bulletX = x + 20; bulletY = y + 48 - 8; break;
            case "LEFT":  bulletX = x;      bulletY = y + 20; break;
            case "RIGHT": bulletX = x + 48 - 8; bulletY = y + 20; break;
        }
        return new Bullet(bulletX, bulletY, direction);
    }

    public void draw(Graphics2D g) {
        BufferedImage image = directionToImage.get(direction);
        if (image != null) {
            g.drawImage(image, x, y, 48, 48, null);
        } else {
            // Fallback: If image is null, draw green square
            g.setColor(Color.GREEN);
            g.fillRect(x, y, 48, 48);
        }
    }
}
