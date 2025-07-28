package tank1990.walls;

import java.io.IOException;
import javax.imageio.ImageIO;
import tank1990.entity.Entity;

public class Steel extends AbstractWall implements Obstacle{

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
        // Steel is harder to break - maybe require multiple hits
        destroyed = true;
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
        breakObstacle();
    }
}
