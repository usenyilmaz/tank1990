package tank1990.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {
    public int x, y;         // Position
    public int speed = 4;    // Movement speed
    public String direction = "UP"; // "UP", "DOWN", "LEFT", "RIGHT"

    public BufferedImage up, down, left, right;

    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        getPlayerImage();
    }

    public void getPlayerImage() {
        try {
            // Load the image for the upward-facing tank.
            // For now, we will use this same image for all directions.
            // Later, you can add separate images or rotate this one.
            System.out.println(getClass().getResource("tank.png"));
            java.net.URL imgURL = getClass().getResource("tank.png");
            if (imgURL != null) {
                up = ImageIO.read(imgURL);
                down = up;
                left = up;
                right = up;
            } else {
                System.err.println("Could not find tank.png at /tank1990/entity/resources/tank.png");
            }
        } catch (IOException e) {
            System.err.println("Could not load player image!");
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
        BufferedImage image = null;

        switch (direction) {
            case "UP":    image = up;    break;
            case "DOWN":  image = down;  break;
            case "LEFT":  image = left;  break;
            case "RIGHT": image = right; break;
        }

        if (image != null) {
            g.drawImage(image, x, y, 32, 32, null);
        } else {
            // Fallback: If image is null, draw green square
            g.setColor(Color.GREEN);
            g.fillRect(x, y, 32, 32);
        }
    }
}
