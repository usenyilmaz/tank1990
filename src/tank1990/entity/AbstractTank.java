package tank1990.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractTank implements Entity {
    protected int x, y;
    protected int speed;
    public String direction = "UP"; // "UP", "DOWN", "LEFT", "RIGHT"
    protected Map<String, BufferedImage> directionToImage = new HashMap<>();
    protected long lastShotTime = 0;
    protected long fireRate = 300; // milliseconds
    protected int prevX, prevY;
    protected boolean isSliding = false;
    protected int lastIceX, lastIceY;
    protected int slideDistance = 0;
    protected final int maxSlideDistance = 96; // 2 tiles worth of sliding

    public AbstractTank(int startX, int startY, int speed) {
        this.x = startX;
        this.y = startY;
        this.prevX = startX;
        this.prevY = startY;
        this.speed = speed;
    }

    public void setSliding(boolean sliding) {
        this.isSliding = sliding;
        if (sliding) {
            slideDistance = 0;
        }
    }

    public void setLastIcePosition(int iceX, int iceY) {
        this.lastIceX = iceX;
        this.lastIceY = iceY;
    }

    public boolean isSliding() {
        return isSliding;
    }

    public void move(int panelWidth, int panelHeight) {
        int newX = x, newY = y;
        
        // If sliding, continue movement in the same direction
        if (isSliding && slideDistance < maxSlideDistance) {
            switch (direction) {
                case "UP":    newY -= speed; break;
                case "DOWN":  newY += speed; break;
                case "LEFT":  newX -= speed; break;
                case "RIGHT": newX += speed; break;
            }
            slideDistance += speed;
            
            // Stop sliding if we've slid too far or hit a boundary
            if (slideDistance >= maxSlideDistance || 
                newX < 0 || newX > panelWidth - getWidth() || 
                newY < 0 || newY > panelHeight - getHeight()) {
                isSliding = false;
                slideDistance = 0;
            }
        } else {
            // Normal movement (when not sliding)
            switch (direction) {
                case "UP":    newY -= speed; break;
                case "DOWN":  newY += speed; break;
                case "LEFT":  newX -= speed; break;
                case "RIGHT": newX += speed; break;
            }
        }
        
        // Clamp to boundaries (default 48x48 tank)
        if (newX >= 0 && newX <= panelWidth - getWidth() && newY >= 0 && newY <= panelHeight - getHeight()) {
            if (newX != x || newY != y) {
                prevX = x;
                prevY = y;
                x = newX;
                y = newY;
            }
        }
    }

    public Bullet shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShotTime < fireRate) {
            return null; // Too soon to shoot again
        }
        lastShotTime = now;
        int bulletX = x + getWidth() / 2 - 4;
        int bulletY = y + getHeight() / 2 - 4;
        switch (direction) {
            case "UP":    bulletX = x + getWidth() / 2 - 4; bulletY = y; break;
            case "DOWN":  bulletX = x + getWidth() / 2 - 4; bulletY = y + getHeight() - 8; break;
            case "LEFT":  bulletX = x;      bulletY = y + getHeight() / 2 - 4; break;
            case "RIGHT": bulletX = x + getWidth() - 8; bulletY = y + getHeight() / 2 - 4; break;
        }
        return new Bullet(bulletX, bulletY, direction);
    }

    public void draw(Graphics2D g) {
        BufferedImage image = directionToImage.get(direction);
        if (image != null) {
            g.drawImage(image, x, y, getWidth(), getHeight(), null);
        } else {
            // Fallback: If image is null, draw green square
            g.setColor(Color.GREEN);
            g.fillRect(x, y, getWidth(), getHeight());
        }
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

    // For future: distinguish between player and enemy tanks
    public abstract String getTankType();

    public int getPrevX(){
        return prevX;
    }

    public int getPrevY(){
        return prevY;
    }

    public void setprevX(int newprevX){
        prevX = newprevX;
    }
    public void setprevY(int newprevY){
        prevY = newprevY;
    }
}
