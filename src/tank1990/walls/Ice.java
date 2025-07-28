package tank1990.walls;

import java.io.IOException;
import javax.imageio.ImageIO;
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
                System.err.println("Could not find ice.jpg");
            }
        } catch (IOException e) {
            System.err.println("Could not load ice.jpg: " + e.getMessage());
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
}
