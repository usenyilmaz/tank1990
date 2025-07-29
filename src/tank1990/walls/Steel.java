package tank1990.walls;

import java.io.IOException;
import javax.imageio.ImageIO;
import tank1990.entity.AbstractTank;
import tank1990.entity.Bullet;
import tank1990.entity.Entity;

public class Steel extends AbstractWall implements Obstacle {

    public Steel(int x, int y) {
        super(x, y);
        loadSteelImage();
    }

    private void loadSteelImage() {
        try {
            java.net.URL imgURL = getClass().getResource("steel.png");
            if (imgURL != null) {
                setImage(ImageIO.read(imgURL));
            } else {
                System.err.println("Could not find steel.png");
            }
        } catch (IOException e) {
            System.err.println("Could not load steel.png: " + e.getMessage());
        }
    }

    @Override
    public void breakObstacle() {
        // Steel cannot be destroyed by bullets
        // It remains intact
    }

    @Override
    public void Explode() {
        // Steel doesn't explode
        // It remains intact
    }

    @Override
    public void StumbleEntity(Entity e) {
        // Steel blocks both tank movement and bullets
        if (this.collidesWith(e.getX(), e.getY(), e.getWidth(), e.getHeight())) {
            if (e instanceof Bullet) {
                // Bullets are destroyed when hitting steel
                Bullet bullet = (Bullet) e;
                bullet.active = false; // Make the bullet disappear
            } else if (e instanceof AbstractTank) {
                // Tanks are blocked by steel
                // The tank position will be reset in GamePanel collision logic
            }
        }
    }

    @Override
    public boolean isDestructible() {
        return false; // Steel cannot be destroyed
    }

    @Override
    public void hit() {
        // Steel doesn't react to being hit
        // It remains intact
    }
}
