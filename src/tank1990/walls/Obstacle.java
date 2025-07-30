package tank1990.walls;

import tank1990.entity.Entity;

public interface Obstacle {
    
    void Explode();
    boolean StumbleEntity(Entity e);

}
