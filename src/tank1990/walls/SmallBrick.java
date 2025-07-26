package tank1990.walls;

import tank1990.entity.Entity;

public class SmallBrick extends AbstractWall implements Obstacle{


    
    public SmallBrick(int x, int y) {
        super(x, y);
        width = 24;
        height = 24;
    }

    @Override
    public void breakObstacle() {
        destroyed = true;
    }

    @Override
    public void Explode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void StumbleEntity(Entity e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    
}
