package tank1990.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class Player {
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
        if (newX >= 0 && newX <= panelWidth - 32 && newY >= 0 && newY <= panelHeight - 32) {
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
        int bulletX = x + 12; // Center bullet horizontally in 32x32 tank
        int bulletY = y + 12; // Center bullet vertically in 32x32 tank
        switch (direction) {
            case "UP":    bulletX = x + 12; bulletY = y; break;
            case "DOWN":  bulletX = x + 12; bulletY = y + 32 - 8; break;
            case "LEFT":  bulletX = x;      bulletY = y + 12; break;
            case "RIGHT": bulletX = x + 32 - 8; bulletY = y + 12; break;
        }
        return new Bullet(bulletX, bulletY, direction);
    }

    public void draw(Graphics2D g) {
        BufferedImage image = directionToImage.get(direction);
        if (image != null) {
            g.drawImage(image, x, y, 32, 32, null);
        } else {
            // Fallback: If image is null, draw green square
            g.setColor(Color.GREEN);
            g.fillRect(x, y, 32, 32);
        }
    }
}
