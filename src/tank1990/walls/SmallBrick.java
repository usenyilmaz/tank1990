package tank1990.walls;

import javax.imageio.ImageIO;
import tank1990.entity.Entity;

public class SmallBrick extends AbstractWall implements Obstacle {

    public SmallBrick(int x, int y) {
        super(x, y);
        width = 24;
        height = 24;
        loadSmallBrickImage();
    }

    private void loadSmallBrickImage() {
        try {
            // Try multiple approaches to load the image
            java.net.URL imgURL = getClass().getResource("small_brick.jpg");
            if (imgURL == null) {
                // Try with absolute path
                imgURL = getClass().getResource("/tank1990/walls/small_brick.jpg");
            }
            if (imgURL == null) {
                // Try with File
                java.io.File file = new java.io.File("src/tank1990/walls/small_brick.jpg");
                if (file.exists()) {
                    imgURL = file.toURI().toURL();
                }
            }
            
            if (imgURL != null) {
                setImage(ImageIO.read(imgURL));
            } else {
                System.err.println("Could not find small_brick.jpg in any location");
            }
        } catch (Exception e) {
            System.err.println("Could not load small_brick.jpg: " + e.getMessage());
        }
    }

    @Override
    public void Explode() {
        try {
            java.net.URL imgURL = getClass().getResource("24x24explosion.png");
            if (imgURL == null) {
                // Try with absolute path
                imgURL = getClass().getResource("/tank1990/walls/24x24explosion.png");
            }
            if (imgURL == null) {
                // Try with File
                java.io.File file = new java.io.File("src/tank1990/walls/24x24explosion.png");
                if (file.exists()) {
                    imgURL = file.toURI().toURL();
                }
            }
            
            if (imgURL != null) {
                explosionImage = ImageIO.read(imgURL);
            } else {
                System.err.println("Could not find 24x24explosion.png in any location");
            }
        } catch (Exception e) {
            System.err.println("Could not load 24x24explosion.png: " + e.getMessage());
        }
        exploding = true;
        explosionStartTime = System.currentTimeMillis();
    }

    @Override
    public void breakObstacle() {
        destroyed = true;
    }

    @Override
    public boolean StumbleEntity(Entity e) {
        return super.StumbleEntity(e);
    }

    @Override
    public boolean isDestructible() {
        return true;
    }

    @Override
    public void hit() {
        breakObstacle();
    }
}
