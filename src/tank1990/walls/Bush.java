package tank1990.walls;

import javax.imageio.ImageIO;
import tank1990.entity.Entity;

public class Bush extends AbstractWall implements Obstacle {

    public Bush(int x, int y) {
        super(x, y);
        loadBushImage();
    }

    private void loadBushImage() {
        try {
            // Try multiple approaches to load the image
            java.net.URL imgURL = getClass().getResource("bush.png");
            if (imgURL == null) {
                // Try with absolute path
                imgURL = getClass().getResource("/tank1990/walls/bush.png");
            }
            if (imgURL == null) {
                // Try with File
                java.io.File file = new java.io.File("src/tank1990/walls/bush.png");
                if (file.exists()) {
                    imgURL = file.toURI().toURL();
                }
            }
            
            if (imgURL != null) {
                setImage(ImageIO.read(imgURL));
            } else {
                System.err.println("Could not find bush.png in any location");
            }
        } catch (Exception e) {
            System.err.println("Could not load bush.png: " + e.getMessage());
        }
    }

    @Override
    public void breakObstacle() {
        // Bushes cannot be destroyed by bullets
        // They remain intact
    }

    @Override
    public void Explode() {
        // Bushes don't explode
        // They remain intact
    }

    @Override
    public boolean StumbleEntity(Entity e) {
        // Bushes don't block movement for any entity - they're purely decorative
        if (this.collidesWith(e.getX(), e.getY(), e.getWidth(), e.getHeight())) {
            // Do nothing - let all entities pass through
            return false; // No collision
        }
        return false; // No collision
    }

    @Override
    public boolean isDestructible() {
        return false; // Bushes cannot be destroyed
    }

    @Override
    public void hit() {
        // Bushes don't react to being hit
        // They remain intact
    }
} 