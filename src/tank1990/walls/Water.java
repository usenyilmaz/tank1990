package tank1990.walls;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import tank1990.entity.Entity;
import tank1990.entity.Bullet;
import tank1990.entity.AbstractTank;

public class Water extends AbstractWall implements Obstacle {

    public Water(int x, int y) {
        super(x, y);
        loadWaterImage();
    }

    private void loadWaterImage() {
        try {
            java.net.URL imgURL = getClass().getResource("water.png");
            if (imgURL != null) {
                setImage(ImageIO.read(imgURL));
            } else {
                System.err.println("Could not find water.png");
            }
        } catch (IOException e) {
            System.err.println("Could not load water.png: " + e.getMessage());
        }
    }

    @Override
    public void breakObstacle() {
        // Water cannot be destroyed by bullets
        // It remains intact
    }

    @Override
    public void Explode() {
        // Water doesn't explode
        // It remains intact
    }

    @Override
    public void StumbleEntity(Entity e) {
        // Water blocks tank movement but allows bullets to pass through
        if (this.collidesWith(e.getX(), e.getY(), e.getWidth(), e.getHeight())) {
            if (e instanceof Bullet) {
                // Bullets pass through water - do nothing
                return;
            } else if (e instanceof AbstractTank) {
                // Tanks are blocked by water
            }
        }
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
}
