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
            java.net.URL imgURL = getClass().getResource("steel.jpg");
            if (imgURL != null) {
                setImage(ImageIO.read(imgURL));
            } else {
                System.err.println("Could not find steel.jpg");
            }
        } catch (IOException e) {
            System.err.println("Could not load steel.jpg: " + e.getMessage());
        }
    }


    @Override
    public void Explode() {
        // Steel might be harder to explode
        destroyed = true;
    }

    @Override
    public void StumbleEntity(Entity e) {
        if (!destroyed && checkCollision(e)) {
            System.out.println("Entity stumbled on steel at (" + x + ", " + y + ")");
        }
    }



}
