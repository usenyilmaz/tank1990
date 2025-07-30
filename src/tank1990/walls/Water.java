package tank1990.walls;

import javax.imageio.ImageIO;
import tank1990.entity.AbstractTank;
import tank1990.entity.Bullet;
import tank1990.entity.Entity;

public class Water extends AbstractWall implements Obstacle {

    public Water(int x, int y) {
        super(x, y);
        loadWaterImage();
    }

    private void loadWaterImage() {
        try {
            // Try multiple approaches to load the image
            java.net.URL imgURL = getClass().getResource("water.png");
            if (imgURL == null) {
                // Try with absolute path
                imgURL = getClass().getResource("/tank1990/walls/water.png");
            }
            if (imgURL == null) {
                // Try with File
                java.io.File file = new java.io.File("src/tank1990/walls/water.png");
                if (file.exists()) {
                    imgURL = file.toURI().toURL();
                }
            }
            
            if (imgURL != null) {
                setImage(ImageIO.read(imgURL));
            } else {
                System.err.println("Could not find water.png in any location");
            }
        } catch (Exception e) {
            System.err.println("Could not load water.png: " + e.getMessage());
        }
    }

    @Override
    public void Explode() {
        // Water doesn't explode
        // It remains intact
    }

    @Override
    public boolean StumbleEntity(Entity e) {
        // Water blocks tank movement but allows bullets to pass through
        if (this.collidesWith(e.getX(), e.getY(), e.getWidth(), e.getHeight())) {
            if (e instanceof Bullet) {
                // Bullets pass through water - return false (no collision)
                return false;
            } else if (e instanceof AbstractTank) {
                // Tanks are blocked by water - return true (collision detected)
                return true;
            }
        }
        return false; // No collision
    }

    @Override
    public boolean isDestructible() {
        return false; // Water cannot be destroyed
    }

    @Override
    public void hit() {
        // Water doesn't react to being hit
        // It remains intact
    }

    @Override
    public void breakObstacle() {
        // Water cannot be broken
        // It remains intact
    }
}
