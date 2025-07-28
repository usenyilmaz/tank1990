package tank1990.ui;

import java.util.List;
import java.util.ArrayList;
import tank1990.walls.AbstractWall;
import tank1990.walls.Brick;
import tank1990.walls.Steel;

public class StageGenerator {
    private int stageNumber;

    public StageGenerator (){
        stageNumber = 1;
    }

    public List<AbstractWall> generateStage(int stageNumber) {
        List<AbstractWall> walls = new ArrayList<>();
        // Example: Stage 1 hardcoded
        if (stageNumber == 1) {
            // Add some bricks and steel walls
            walls.add(new Brick(200, 100));
            walls.add(new Steel(200, 100));
            walls.add(new Brick(300, 200));
            // ...add more as needed
        }
        // You can add more stages with else if (stageNumber == 2) { ... }
        // Or call a random generator for higher stages
        return walls;
    }

    public int getstageNumber(){
        return this.stageNumber;
    }
    public void NextStage(){
        this.stageNumber++;
    }
}
