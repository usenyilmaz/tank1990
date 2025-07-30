package tank1990.walls;

import java.io.IOException;
import javax.imageio.ImageIO;
import tank1990.entity.Entity;

public class Brick extends AbstractWall implements Obstacle{
    
    public Brick(int x, int y) {
        super(x, y);
        loadBrickImage();
    }

    private void loadBrickImage() {
        try {
            java.net.URL imgURL = getClass().getResource("brick.png");
            if (imgURL != null) {
                setImage(ImageIO.read(imgURL));
            } else {
                System.err.println("Could not find brick.png");
            }
        } catch (IOException e) {
            System.err.println("Could not load brick.png: " + e.getMessage());
        }
    }

    @Override
    public void breakObstacle() {
        destroyed = true;
    }

    @Override
    public void Explode() {
        super.Explode();
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
