package tank1990.walls;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import tank1990.entity.Entity;
import tank1990.entity.Bullet;
import tank1990.entity.AbstractTank;

public class Bush extends AbstractWall implements Obstacle {

    public Bush(int x, int y) {
        super(x, y);
        loadBushImage();
    }

    private void loadBushImage() {
        try {
            java.net.URL imgURL = getClass().getResource("bush.png");
            if (imgURL != null) {
                setImage(ImageIO.read(imgURL));
            } else {
                System.err.println("Could not find bush.png");
            }
        } catch (IOException e) {
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
    public void StumbleEntity(Entity e) {
        // Bushes don't block movement for any entity - they're purely decorative
        if (this.collidesWith(e.getX(), e.getY(), e.getWidth(), e.getHeight())) {
            // Do nothing - let all entities pass through
            return;
        }
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