package tank1990.walls;

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
            java.net.URL imgURL = getClass().getResource("small_brick.jpg");
            if (imgURL != null) {
                setImage(javax.imageio.ImageIO.read(imgURL));
            } else {
                System.err.println("Could not find small_brick.jpg");
            }
        } catch (Exception e) {
            System.err.println("Could not load small_brick.jpg: " + e.getMessage());
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
