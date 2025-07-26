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
            java.net.URL imgURL = getClass().getResource("brick.jpg");
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
        destroyed = true;
    }

    @Override
    public void StumbleEntity(Entity e) {
        if (!destroyed && checkCollision(e)) {
            System.out.println("Entity stumbled on brick at (" + x + ", " + y + ")");
        }
    }

}
