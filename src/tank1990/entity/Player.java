package tank1990.entity;

import java.io.IOException;

public class Player extends AbstractTank {
    public Player(int startX, int startY) {
        super(startX, startY);
        loadImages();
    }

    private void loadImages() {
        loadImageForDirection("UP", "tankup.png");
        loadImageForDirection("DOWN", "tankdown.png");
        loadImageForDirection("LEFT", "tankleft.png");
        loadImageForDirection("RIGHT", "tankright.png");
    }

    private void loadImageForDirection(String dir, String resourcePath) {
        try {
            java.net.URL imgURL = getClass().getResource(resourcePath);
            if (imgURL != null) {
                directionToImage.put(dir, javax.imageio.ImageIO.read(imgURL));
            } else {
                System.err.println("Could not find " + resourcePath);
            }
        } catch (IOException e) {
            System.err.println("Could not load image for direction " + dir + ": " + resourcePath);
        }
    }

    @Override
    public String getTankType() {
        return "PLAYER";
    }
    
}
