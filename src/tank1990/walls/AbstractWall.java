package tank1990.walls;

import java.awt.image.BufferedImage;
import tank1990.entity.Entity;

public abstract class AbstractWall {
    
    protected int x, y;
    protected int width, height;
    protected boolean destroyed = false;
    BufferedImage image;

    public AbstractWall(int x, int y) {
        this.x = x;
        this.y = y;
        width = 48;
        height = 48;
    }

    public void setImage(BufferedImage newimage){
        this.image = newimage;
    }
    
    public abstract void Explode();

    public abstract void StumbleEntity(Entity e);
    
    
    protected boolean checkCollision(Entity e) {
        return x < e.getX() + e.getWidth() &&
               x + width > e.getX() &&
               y < e.getY() + e.getHeight() &&
               y + height > e.getY();
    }
    
    public boolean isDestroyed() {
        return destroyed;
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    
    public void draw(java.awt.Graphics2D g) {
        if (!destroyed) {
            if (image != null) {
                g.drawImage(image, x, y, width, height, null);
            } else {
                // Fallback: draw a gray rectangle if image is not loaded
                g.setColor(java.awt.Color.GRAY);
                g.fillRect(x, y, width, height);
            }
        }
    }
}
