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

    public void move() {
        switch (direction) {
            case "UP":    y -= speed; break;
            case "DOWN":  y += speed; break;
            case "LEFT":  x -= speed; break;
            case "RIGHT": x += speed; break;
        }
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
