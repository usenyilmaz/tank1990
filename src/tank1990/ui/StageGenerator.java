package tank1990.ui;

import java.util.ArrayList;
import java.util.List;
import tank1990.walls.AbstractWall;
import tank1990.walls.Brick;
import tank1990.walls.Bush;
import tank1990.walls.Eagle;
import tank1990.walls.Ice;
import tank1990.walls.SmallBrick;
import tank1990.walls.Steel;
import tank1990.walls.Water;

public class StageGenerator {
    private int stageNumber;

    public StageGenerator (){
        stageNumber = 1;
    }

    public List<AbstractWall> generateStage(int stageNumber) {
        List<AbstractWall> walls = new ArrayList<>();
        AbstractWall[][] map = new AbstractWall[13][13];
        
        if (stageNumber == 1) {
            // Use the original hardcoded layout for stage 1
            return generateStage1();
        } else {
            // Generate random layout for stage 2+
            return generateRandomStage(stageNumber);
        }
    }
    
    private List<AbstractWall> generateStage1() {
        List<AbstractWall> walls = new ArrayList<>();
        AbstractWall[][] map = new AbstractWall[13][13];
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map.length; j++){
                //eagle
                if(j == 12 && i == 6){
                    map[i][j] = new Eagle(48 * i, 48 * j);
                }
                //eagle
                
                //bushes************************************************
                if(j == 1 && i == 0){
                    map[i][j] = new Bush(48 * i, 48 * j);
                }
                if(j == 2 && i == 0){
                    map[i][j] = new Bush(48 * i, 48 * j);
                }
                if(j == 3 && i == 0){
                    map[i][j] = new Bush(48 * i, 48 * j);
                }
                if(j == 4 && i == 0){
                    map[i][j] = new Bush(48 * i, 48 * j);
                }
                if(j == 5 && i == 0){
                    map[i][j] = new Bush(48 * i, 48 * j);
                }
                if(j == 5 && i == 1){
                    map[i][j] = new Bush(48 * i, 48 * j);
                }
                if(j == 2 && i == 3){
                    map[i][j] = new Bush(48 * i, 48 * j);
                }
                if(j == 2 && i == 4){
                    map[i][j] = new Bush(48 * i, 48 * j);
                }
                if(j == 2 && i == 5){
                    map[i][j] = new Bush(48 * i, 48 * j);
                }
                if(j == 6 && i == 5){
                    map[i][j] = new Bush(48 * i, 48 * j);
                }
                if(j == 6 && i == 6){
                    map[i][j] = new Bush(48 * i, 48 * j);
                }
                if(j == 7 && i == 4){
                    map[i][j] = new Bush(48 * i, 48 * j);
                }
                if(j == 10 && i == 0){
                    map[i][j] = new Bush(48 * i, 48 * j);
                }
                if(j == 4 && i == 10){
                    map[i][j] = new Bush(48 * i, 48 * j);
                }
                if(j == 5 && i == 10){
                    map[i][j] = new Bush(48 * i, 48 * j);
                }
                if(j == 6 && i == 10){
                    map[i][j] = new Bush(48 * i, 48 * j);
                }
                //bushes************************************************


                //roger waters************************************************
                if(i == 0 && j == 9){
                    map[i][j] = new Water(48 * i, 48 * j);
                }
                if(i == 2 && j == 8){
                    map[i][j] = new Water(48 * i, 48 * j);
                }
                if(i == 9 && j == 8){
                    map[i][j] = new Water(48 * i, 48 * j);
                }
                if(i == 10 && j == 8){
                    map[i][j] = new Water(48 * i, 48 * j);
                }
                if(i == 12 && j == 8){
                    map[i][j] = new Water(48 * i, 48 * j);
                }
                if(i == 12 && j == 9){
                    map[i][j] = new Water(48 * i, 48 * j);
                }
                if(i == 12 && j == 10){
                    map[i][j] = new Water(48 * i, 48 * j);
                }
                if(i == 12 && j == 11){
                    map[i][j] = new Water(48 * i, 48 * j);
                }

                //roger waters************************************************
                if(j == 0 && i == 3){
                    map[i][j] = new Steel(48 * i, 48 * j);
                }
                if(j == 0 && i == 7){
                    map[i][j] = new Steel(48 * i, 48 * j);
                }
                
                if(j == 1 && i == 1){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 1 && i == 3){
                    map[i][j] = new Steel(48 * i, 48 * j);
                }
                if(j == 1 && i == 7){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 1 && i == 9){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 1 && i == 11){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                //bush here
                if(j == 2 && i == 1){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                //3 bushes here
                if(j == 2 && i == 6){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 2 && i == 7){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 2 && i == 9){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 2 && i == 10){
                    map[i][j] = new Steel(48 * i, 48 * j);
                }
                if(j == 2 && i == 11){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                //bush here
                if(j == 3 && i == 3){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 3 && i == 6){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 3 && i == 7){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 3 && i == 9){
                    map[i][j] = new Steel(48 * i, 48 * j);
                }
                if(j == 3 && i == 12){
                    map[i][j] = new Ice(48 * i, 48 * j);
                }
                //bush here
                if(j == 4 && i == 3){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 4 && i == 5){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 4 && i == 6){
                    map[i][j] = new Steel(48 * i, 48 * j);
                }
                if(j == 4 && i == 9){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                //bush here
                if(j == 4 && i == 11){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 4 && i == 12){
                    map[i][j] = new Steel(48 * i, 48 * j);
                }
                //2 bushes here
                if(j == 5 && i == 3){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 5 && i == 4){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 5 && i == 5){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 5 && i == 8){
                    map[i][j] = new Steel(48 * i, 48 * j);
                }
                if(j == 5 && i == 9){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                //bush here
                if(j == 6 && i == 0){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 6 && i == 1){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 6 && i == 2){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 6 && i == 3){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 6 && i == 4){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                //2 bushes here
                if(j == 6 && i == 7){
                    map[i][j] = new Steel(48 * i, 48 * j);
                }
                if(j == 6 && i == 8){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 6 && i == 9){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                //bush here
                if(j == 6 && i == 10){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 7 && i == 3){
                    map[i][j] = new Steel(48 * i, 48 * j);
                }
                //bush here
                if(j == 7 && i == 5){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 7 && i == 7){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 7 && i == 9){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 7 && i == 11){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 8 && i == 0){
                    map[i][j] = new Steel(48 * i, 48 * j);
                }
                if(j == 8 && i == 1){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                //water here
                if(j == 8 && i == 3){
                    map[i][j] = new Steel(48 * i, 48 * j);
                }
                if(j == 8 && i == 5){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 8 && i == 7){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                //2 waters here
                if(j == 8 && i == 11){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                //water here
                //water here
                if(j == 9 && i == 1){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 9 && i == 3){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 9 && i == 5){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 9 && i == 6){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 9 && i == 7){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 9 && i == 9){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 9 && i == 10){
                    map[i][j] = new Steel(48 * i, 48 * j);
                }
                if(j == 9 && i == 11){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                //water here
                //bush here
                if(j == 10 && i == 1){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 10 && i == 3){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 10 && i == 5){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 10 && i == 6){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 10 && i == 7){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                //water here
                //water here
                if(j == 11 && i == 1){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 11 && i == 9){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 11 && i == 11){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                // water here
                //water here
                if(j == 12 && i == 1){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 12 && i == 3){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }

                if(j == 12 && i == 9){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 12 && i == 10){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
                if(j == 12 && i == 11){
                    map[i][j] = new Brick(48 * i, 48 * j);
                }
            // INSERT_YOUR_CODE
            



            }
            
        }
        

        for (int i = 0; i < map.length; i++) {

            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] != null) {
                    walls.add(map[i][j]);
                }
            }
        }

        walls.add(new SmallBrick(48 * 6 - 24, 48 * 12));
        walls.add(new SmallBrick(48 * 6 - 24, 48 * 12 - 24));
        walls.add(new SmallBrick(48 * 6 - 24, 48 * 12 + 24));

        walls.add(new SmallBrick(48 * 6, 48 * 12 - 24));
        walls.add(new SmallBrick(48 * 6 + 24, 48 * 12 - 24));

        walls.add(new SmallBrick(48 * 6 + 48, 48 * 12));
        walls.add(new SmallBrick(48 * 6 + 48, 48 * 12 - 24));
        walls.add(new SmallBrick(48 * 6 + 48, 48 * 12 + 24));

        return walls;
    }
    
    private List<AbstractWall> generateRandomStage(int stageNumber) {
        List<AbstractWall> walls = new ArrayList<>();
        AbstractWall[][] map = new AbstractWall[13][13];
        java.util.Random random = new java.util.Random(stageNumber); // Use stage number as seed for consistent random generation
        
        // Fixed positions that must remain the same:
        // 1. Player spawn position: (4, 12) - must be empty
        // 2. Tank spawn positions: (0, 0), (6, 0), (12, 0) - must be empty
        // 3. Eagle position: (6, 12) - must have eagle
        // 4. Eagle surrounding walls: 8 small brick walls around (6, 12)
        
        // Place eagle at fixed position
        map[6][12] = new Eagle(48 * 6, 48 * 12);
        
        // Generate random walls for the rest of the map
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                // Skip fixed positions
                if ((i == 4 && j == 12) || // Player spawn
                    (i == 0 && j == 0) || // Tank spawn 1
                    (i == 6 && j == 0) || // Tank spawn 2  
                    (i == 12 && j == 0) || // Tank spawn 3
                    (i == 6 && j == 12)) { // Eagle position
                    continue;
                }
                
                // Random chance to place a wall (adjust probability as needed)
                if (random.nextDouble() < 0.3) { // 30% chance for each position
                    int wallType = random.nextInt(4); // 0=Brick, 1=Steel, 2=Ice, 3=Bush
                    
                    switch (wallType) {
                        case 0:
                            map[i][j] = new Brick(48 * i, 48 * j);
                            break;
                        case 1:
                            map[i][j] = new Steel(48 * i, 48 * j);
                            break;
                        case 2:
                            map[i][j] = new Ice(48 * i, 48 * j);
                            break;
                        case 3:
                            map[i][j] = new Bush(48 * i, 48 * j);
                            break;
                    }
                }
            }
        }
        
        // Add walls to list
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] != null) {
                    walls.add(map[i][j]);
                }
            }
        }

        // Add eagle's surrounding small brick walls (same as stage 1)
        walls.add(new SmallBrick(48 * 6 - 24, 48 * 12));
        walls.add(new SmallBrick(48 * 6 - 24, 48 * 12 - 24));
        walls.add(new SmallBrick(48 * 6 - 24, 48 * 12 + 24));

        walls.add(new SmallBrick(48 * 6, 48 * 12 - 24));
        walls.add(new SmallBrick(48 * 6 + 24, 48 * 12 - 24));

        walls.add(new SmallBrick(48 * 6 + 48, 48 * 12));
        walls.add(new SmallBrick(48 * 6 + 48, 48 * 12 - 24));
        walls.add(new SmallBrick(48 * 6 + 48, 48 * 12 + 24));

        return walls;
    }

    public int getstageNumber(){
        return this.stageNumber;
    }
    public void NextStage(){
        this.stageNumber++;
    }
}
