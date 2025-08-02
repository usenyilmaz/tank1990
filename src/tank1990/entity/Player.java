package tank1990.entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import tank1990.game.GameManager;

public class Player extends AbstractTank {
    String name = "Player";
    int health = 2;
    int pointsgained;
    
    // Explosion variables
    private boolean isExploding = false;
    private long explosionStartTime = 0;
    private BufferedImage explosionImage;
    private static final long EXPLOSION_DURATION = 300; // 0.3 seconds

    public Player(int startX, int startY) {
        super(startX, startY);
        this.speed = 2; // Set the parent's speed field
        loadImages();
        loadExplosionImage();
        pointsgained = 0;
    }

    private void loadImages() {
        loadImageForDirection("UP", "tankup.png");
        loadImageForDirection("DOWN", "tankdown.png");
        loadImageForDirection("LEFT", "tankleft.png");
        loadImageForDirection("RIGHT", "tankright.png");
    }

    private void loadExplosionImage() {
        try {
            java.net.URL imgURL = getClass().getResource("explosion.png");
            if (imgURL != null) {
                explosionImage = javax.imageio.ImageIO.read(imgURL);
            } else {
                System.err.println("Could not find explosion.png");
            }
        } catch (IOException e) {
            System.err.println("Could not load explosion image");
        }
    }

    private void loadImageForDirection(String dir, String resourcePath) {
        try {
            java.net.URL imgURL = getClass().getResource(resourcePath);
            if (imgURL != null) {
                directionToImage.put(dir, javax.imageio.ImageIO.read(imgURL));
            } else {
                System.err.println("Could not find " + resourcePath);
            }
        } catch (IOException e) {
            System.err.println("Could not load image for direction " + dir + ": " + resourcePath);
        }
    }

    @Override
    public String getTankType() {
        return "PLAYER";
    }

    public int getPointsgained() {
        return pointsgained;
    }

    public void IncreasePointsby(int pointsgained) {
        this.pointsgained += pointsgained;
    }

    public void ResetPoints() {
        pointsgained = 0;
    }

    @Override
    public void die() {
        if (health >= 0) {
            // Player will explode and respawn
            isExploding = true;
            explosionStartTime = System.currentTimeMillis();
            
            // Schedule respawn after explosion duration
            new Thread(() -> {
                try {
                    Thread.sleep(EXPLOSION_DURATION);
                    respawn();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        } else {
            // Game over - player has no lives left
            GameManager.getInstance().playerDied();
        }
        this.health--;
    }

    public void respawn() {
        // Respawn at coordinates: x = 48 * 4, y = 48 * 12
        setPosition(48 * 4, 48 * 12);
        isExploding = false;
        explosionStartTime = 0;
    }

    @Override
    public void draw(java.awt.Graphics2D g) {
        if (isExploding) {
            // Draw explosion image
            if (explosionImage != null) {
                g.drawImage(explosionImage, getX(), getY(), getWidth(), getHeight(), null);
            } else {
                // Fallback: draw red square for explosion
                g.setColor(java.awt.Color.RED);
                g.fillRect(getX(), getY(), getWidth(), getHeight());
            }
        } else {
            // Draw normal tank image
            super.draw(g);
        }
    }

    public boolean isExploding() {
        return isExploding;
    }
}
