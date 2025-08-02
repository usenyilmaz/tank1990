package tank1990.walls;

import javax.imageio.ImageIO;
import tank1990.entity.Entity;
import tank1990.game.GameManager;

public class Eagle extends AbstractWall implements Obstacle {

    public Eagle(int x, int y) {
        super(x, y);
        loadEagleImage();
    }

    private void loadEagleImage() {
        try {
            // Try multiple approaches to load the image
            java.net.URL imgURL = getClass().getResource("eagle.png");
            if (imgURL == null) {
                // Try with absolute path
                imgURL = getClass().getResource("/tank1990/walls/eagle.png");
            }
            if (imgURL == null) {
                // Try with File
                java.io.File file = new java.io.File("src/tank1990/walls/eagle.png");
                if (file.exists()) {
                    imgURL = file.toURI().toURL();
                }
            }
            
            if (imgURL != null) {
                setImage(ImageIO.read(imgURL));
            } else {
                System.err.println("Could not find eagle.png in any location");
            }
        } catch (Exception e) {
            System.err.println("Could not load eagle.png: " + e.getMessage());
        }
    }

    @Override
    public void Explode() {
        // Eagle explosion triggers game over
        GameManager.getInstance().eagleDestroyed();
        // Call the parent Explode method
        exploding = true;
        explosionStartTime = System.currentTimeMillis();
    }

    @Override
    public void breakObstacle() {
        // Eagle cannot be broken normally
        // Only explodes when hit by bullets
    }

    @Override
    public boolean StumbleEntity(Entity e) {
        // Eagle blocks both tanks and bullets
        return this.collidesWith(e.getX(), e.getY(), e.getWidth(), e.getHeight());
    }

    @Override
    public boolean isDestructible() {
        return true; // Eagle can be destroyed by bullets
    }

    @Override
    public void hit() {
        // When eagle is hit, it explodes and triggers game over
        Explode();
    }
}
