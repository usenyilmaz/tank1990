package tank1990.walls;

import java.awt.image.BufferedImage;
import tank1990.entity.Entity;

public abstract class AbstractWall {
    
    protected int x, y;
    protected int width, height;
    protected boolean destroyed = false;
    BufferedImage image;
    protected boolean exploding = false;
    protected long explosionStartTime = 0;
    protected BufferedImage explosionImage;

    public AbstractWall(int x, int y) {
        this.x = x;
        this.y = y;
        width = 48;
        height = 48;
    }

    public void setImage(BufferedImage newimage){
        this.image = newimage;
    }
    
    public void Explode() {
        try {
            java.net.URL imgURL = getClass().getResource("explosion.png");
            if (imgURL != null) {
                explosionImage = javax.imageio.ImageIO.read(imgURL);
            }
        } catch (Exception e) {
            System.err.println("Could not load explosion.png: " + e.getMessage());
        }
        exploding = true;
        explosionStartTime = System.currentTimeMillis();
    }

    public boolean StumbleEntity(Entity e) {
        return this.collidesWith(e.getX(), e.getY(), e.getWidth(), e.getHeight());
    }
    
    // Public collision check for bullets/entities
    public boolean collidesWith(int bx, int by, int bwidth, int bheight) {
        return x < bx + bwidth &&
               x + width > bx &&
               y < by + bheight &&
               y + height > by;
    }

    // Optional: update method for explosion timing
    public void update() {
        if (exploding && System.currentTimeMillis() - explosionStartTime >= 500) {
            destroyed = true;
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public boolean isVisible() {
        return !destroyed;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    
    public void draw(java.awt.Graphics2D g) {
        if (destroyed) return;
        if (exploding) {
            long now = System.currentTimeMillis();
            if (now - explosionStartTime < 500) {
                if (explosionImage != null) {
                    g.drawImage(explosionImage, x, y, width, height, null);
                } else {
                    g.setColor(java.awt.Color.ORANGE);
                    g.fillRect(x, y, width, height);
                }
            } else {
                destroyed = true;
            }
            return;
        }
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        } else {
            g.setColor(java.awt.Color.GRAY);
            g.fillRect(x, y, width, height);
        }
    }

    public void destroy() {
        destroyed = true;
    }

    public abstract boolean isDestructible();
    public abstract void hit();
}
