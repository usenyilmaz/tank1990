package tank1990.walls;

import java.io.IOException;
import javax.imageio.ImageIO;
import tank1990.entity.AbstractTank;
import tank1990.entity.Bullet;
import tank1990.entity.Entity;

public class Ice extends AbstractWall implements Obstacle {

    public Ice(int x, int y) {
        super(x, y);
        loadIceImage();
    }

    private void loadIceImage() {
        try {
            java.net.URL imgURL = getClass().getResource("ice.png");
            if (imgURL != null) {
                setImage(ImageIO.read(imgURL));
            } else {
                System.err.println("Could not find ice.png");
            }
        } catch (IOException e) {
            System.err.println("Could not load ice.png: " + e.getMessage());
        }
    }

    @Override
    public void breakObstacle() {
        // Ice cannot be destroyed by bullets
        // It remains intact
    }

    @Override
    public void Explode() {
        // Ice doesn't explode
        // It remains intact
    }

    @Override
    public boolean StumbleEntity(Entity e) {
        // Ice blocks tank movement but allows bullets to pass through
        if (this.collidesWith(e.getX(), e.getY(), e.getWidth(), e.getHeight())) {
            if (e instanceof Bullet) {
                // Bullets pass through ice - return false (no collision)
                return false;
            } else if (e instanceof AbstractTank) {
                // Tanks slide on ice - set sliding state
                AbstractTank tank = (AbstractTank) e;
                tank.setSliding(true);
                tank.setLastIcePosition(x, y);
                return true; // Collision detected
            }
        }
        return false; // No collision
    }

    @Override
    public boolean isDestructible() {
        return false; // Ice cannot be destroyed
    }

    @Override
    public void hit() {
        // Ice doesn't react to being hit
        // It remains intact
    }
}
