package tank1990.entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class FastTank extends AbstractTank implements Runnable {
    String name = "FastTank";
    int health = 1;
    int points = 200;

    private Random random = new Random();
    private long lastAIShotTime = 0;
    private long aiFireRate = 1500; // 1.5 seconds between shots (faster than BasicTank)
    
    // Thread management
    private Thread tankThread;
    private boolean running = false;
    private int panelWidth = 624;
    private int panelHeight = 624;
    
    // Explosion variables
    private boolean isExploding = false;
    private long explosionStartTime = 0;
    private BufferedImage explosionImage;
    private static final long EXPLOSION_DURATION = 300; // 0.3 seconds
    
    // Player reference for points
    private Player player;
    
    // Spawn coordinates
    private static final int[][] SPAWN_COORDINATES = {
        {0, 0},                    // x = 0, y = 0
        {6 * 48, 0},              // x = 6 * 48, y = 0
        {12 * 48, 0}              // x = 12 * 48, y = 0
    };

    public FastTank(int startX, int startY, Player player) {
        super(startX, startY);
        this.player = player;
        this.speed = 4; // Set the parent's speed field
        loadImages();
        loadExplosionImage();
        initializeThread();
    }

    // Static spawn method
    public static FastTank spawn(Player player) {
        Random spawnRandom = new Random();
        int[] coordinates = SPAWN_COORDINATES[spawnRandom.nextInt(SPAWN_COORDINATES.length)];
        return new FastTank(coordinates[0], coordinates[1], player);
    }

    private void initializeThread() {
        tankThread = new Thread(this, "FastTank-" + System.currentTimeMillis());
        running = true;
        tankThread.start();
    }

    private void loadImages() {
        loadImageForDirection("UP", "fastup.png");
        loadImageForDirection("DOWN", "fastdown.png");
        loadImageForDirection("LEFT", "fastleft.png");
        loadImageForDirection("RIGHT", "fastright.png");
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
        return "FAST";
    }

    // AI shooting method - FastTank shoots more frequently
    public Bullet aiShoot() {
        long now = System.currentTimeMillis();
        if (now - lastAIShotTime < aiFireRate) {
            return null; // Too soon to shoot again
        }
        
        // Random chance to shoot (40% chance - higher than BasicTank)
        if (random.nextInt(100) < 40) {
            lastAIShotTime = now;
            return shoot(); // Use the inherited shoot method
        }
        
        return null;
    }

    // Method to handle being hit by a bullet
    public boolean getHit(Bullet bullet) {
        if (isExploding) {
            return false; // Already exploding, can't be hit
        }
        
        // Check collision between bullet and tank
        if (bullet.getX() < getX() + getWidth() &&
            bullet.getX() + bullet.getWidth() > getX() &&
            bullet.getY() < getY() + getHeight() &&
            bullet.getY() + bullet.getHeight() > getY()) {
            
            // Collision detected
            bullet.active = false; // Stop the bullet
            health--; // Decrease health
            
            if (health <= 0) {
                die(); // Tank dies
            }
            
            return true; // Hit successful
        }
        
        return false; // No collision
    }

    @Override
    public void die() {
        if (health < 0) {
            // Give points to player
            if (player != null) {
                player.IncreasePointsby(points);
            }
            
            // Tank will explode
            isExploding = true;
            explosionStartTime = System.currentTimeMillis();
            running = false; // Stop the tank thread
            
            // Schedule removal after explosion duration
            new Thread(() -> {
                try {
                    Thread.sleep(EXPLOSION_DURATION);
                    // Tank will be removed by the game logic
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }

    // Thread run method
    @Override
    public void run() {
        while (running && !isExploding) {
            try {
                // AI movement logic - less frequent direction changes for smoother movement
                if (random.nextInt(100) < 8) { // 8% chance to change direction (reduced from 30%)
                    String[] directions = {"UP", "DOWN", "LEFT", "RIGHT"};
                    direction = directions[random.nextInt(directions.length)];
                }
                
                // Movement is now handled in the main game loop
                // No need to call move() here anymore
                
                // Sleep for a much shorter time to make movement more continuous
                Thread.sleep(12); // Even faster for FastTank (80+ FPS)
                
            } catch (InterruptedException e) {
                running = false;
                break;
            }
        }
    }

    // Method to stop the tank thread
    public void stopTank() {
        running = false;
        if (tankThread != null && tankThread.isAlive()) {
            tankThread.interrupt();
        }
    }

    // Method to set panel dimensions
    public void setPanelDimensions(int width, int height) {
        this.panelWidth = width;
        this.panelHeight = height;
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
    
    @Override
    public long getExplosionStartTime() {
        return explosionStartTime;
    }
} 