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
                System.err.println("Could not find brick.jpg");
            }
        } catch (IOException e) {
            System.err.println("Could not load brick.jpg: " + e.getMessage());
        }
    }

    @Override
    public void Explode() {
        super.Explode();
    }

    @Override
    public void StumbleEntity(Entity e) {
        super.StumbleEntity(e);
    }

    @Override
    public boolean isDestructible() {
        return true;
    }

    @Override
    public void hit() {
        destroy();
    }
}
