package tank1990.ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import tank1990.entity.AbstractTank;
import tank1990.entity.ArmorTank;
import tank1990.entity.BasicTank;
import tank1990.entity.Bullet;
import tank1990.entity.FastTank;
import tank1990.entity.KeyHandler;
import tank1990.entity.Player;
import tank1990.entity.PowerTank;
import tank1990.game.GameManager;
import tank1990.walls.AbstractWall;
import tank1990.walls.Bush;
import tank1990.walls.Ice;
import tank1990.walls.Water;

public class GamePanel extends JPanel implements Runnable {
    Player player;
    KeyHandler keyH;
    Thread gameThread;
    List<Bullet> bullets = new ArrayList<>();
    List<Bullet> enemyBullets = new ArrayList<>();
    List<AbstractWall> walls = new ArrayList<>();
    
    // Enemy tank lists
    List<BasicTank> basicTanks = new ArrayList<>();
    List<FastTank> fastTanks = new ArrayList<>();
    List<PowerTank> powerTanks = new ArrayList<>();
    List<ArmorTank> armorTanks = new ArrayList<>();
    
    // Tank management
    private static final int MAX_BASIC_TANKS = 1;
    private static final int MAX_FAST_TANKS = 1;
    private static final int MAX_POWER_TANKS = 1;
    private static final int MAX_ARMOR_TANKS = 1;
    
    private int fps = 0;
    private int currentStageNumber;
    private GameManager gameManager;
    private ImageIcon flagIcon;
    private ImageIcon twoLivesIcon;
    private ImageIcon oneLiveIcon;
    private ImageIcon zeroLivesIcon;

    public GamePanel(int currentStageNumber) {
        this.currentStageNumber = currentStageNumber;
        gameManager = GameManager.getInstance();
        keyH = new KeyHandler();
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(keyH);
        this.setBackground(Color.BLACK);
        player = new Player(48 * 4, 48 * 12);
        StageGenerator generator = new StageGenerator();
        walls = generator.generateStage(currentStageNumber);
        loadFlagImage();
        loadLivesImages();
        initializeEnemyTanks();
        gameManager.startGame();
        startGameThread();
    }

    private void initializeEnemyTanks() {
        // Spawn initial enemy tanks
        for (int i = 0; i < MAX_BASIC_TANKS; i++) {
            basicTanks.add(BasicTank.spawn(player));
        }
        for (int i = 0; i < MAX_FAST_TANKS; i++) {
            fastTanks.add(FastTank.spawn(player));
        }
        for (int i = 0; i < MAX_POWER_TANKS; i++) {
            powerTanks.add(PowerTank.spawn(player));
        }
        for (int i = 0; i < MAX_ARMOR_TANKS; i++) {
            armorTanks.add(ArmorTank.spawn(player));
        }
        
        // Set panel dimensions for all tanks
        setTankPanelDimensions();
    }
    
    private void setTankPanelDimensions() {
        int gameAreaWidth = 13 * 48;
        int gameAreaHeight = 13 * 48;
        
        for (BasicTank tank : basicTanks) {
            tank.setPanelDimensions(gameAreaWidth, gameAreaHeight);
        }
        for (FastTank tank : fastTanks) {
            tank.setPanelDimensions(gameAreaWidth, gameAreaHeight);
        }
        for (PowerTank tank : powerTanks) {
            tank.setPanelDimensions(gameAreaWidth, gameAreaHeight);
        }
        for (ArmorTank tank : armorTanks) {
            tank.setPanelDimensions(gameAreaWidth, gameAreaHeight);
        }
    }

    private void loadFlagImage() {
        try {
            // Try multiple approaches to load the flag image
            java.net.URL imgURL = getClass().getResource("flag.png");
            if (imgURL == null) {
                // Try with absolute path
                imgURL = getClass().getResource("/tank1990/ui/flag.png");
            }
            if (imgURL == null) {
                // Try with File
                java.io.File file = new java.io.File("src/tank1990/ui/flag.png");
                if (file.exists()) {
                    imgURL = file.toURI().toURL();
                }
            }
            
            if (imgURL != null) {
                flagIcon = new ImageIcon(imgURL);
            } else {
                System.err.println("Could not find flag.png in any location");
            }
        } catch (Exception e) {
            System.err.println("Could not load flag.png: " + e.getMessage());
        }
    }

    private void loadLivesImages() {
        try {
            // Try multiple approaches to load the lives images
            java.net.URL imgURL = getClass().getResource("twolives.png");
            if (imgURL == null) {
                // Try with absolute path
                imgURL = getClass().getResource("/tank1990/ui/twolives.png");
            }
            if (imgURL == null) {
                // Try with File
                java.io.File file = new java.io.File("src/tank1990/ui/twolives.png");
                if (file.exists()) {
                    imgURL = file.toURI().toURL();
                }
            }
            if (imgURL != null) {
                twoLivesIcon = new ImageIcon(imgURL);
            } else {
                System.err.println("Could not find twolives.png in any location");
            }

            imgURL = getClass().getResource("onelive.png");
            if (imgURL == null) {
                // Try with absolute path
                imgURL = getClass().getResource("/tank1990/ui/onelive.png");
            }
            if (imgURL == null) {
                // Try with File
                java.io.File file = new java.io.File("src/tank1990/ui/onelive.png");
                if (file.exists()) {
                    imgURL = file.toURI().toURL();
                }
            }
            if (imgURL != null) {
                oneLiveIcon = new ImageIcon(imgURL);
            } else {
                System.err.println("Could not find onelive.png in any location");
            }

            imgURL = getClass().getResource("zerolives.png");
            if (imgURL == null) {
                // Try with absolute path
                imgURL = getClass().getResource("/tank1990/ui/zerolives.png");
            }
            if (imgURL == null) {
                // Try with File
                java.io.File file = new java.io.File("src/tank1990/ui/zerolives.png");
                if (file.exists()) {
                    imgURL = file.toURI().toURL();
                }
            }
            if (imgURL != null) {
                zeroLivesIcon = new ImageIcon(imgURL);
            } else {
                System.err.println("Could not find zerolives.png in any location");
            }
        } catch (Exception e) {
            System.err.println("Could not load lives images: " + e.getMessage());
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        long lastTime = System.currentTimeMillis();
        int frames = 0;
        while (true) {
            update();
            repaint();
            frames++;
            long now = System.currentTimeMillis();
            if (now - lastTime >= 1000) {
                fps = frames;
                frames = 0;
                lastTime = now;
            }
            try { Thread.sleep(16); } catch (InterruptedException e) {}
        }
    }

    public void update() {
        // Check game state first
        if (gameManager.isGameOver()) {
            // Game over - stop updating
            return;
        }
        
        if (!gameManager.isPlaying()) {
            // Game is paused or in menu - don't update game logic
            return;
        }
        
        // Define game area boundaries
        int gameAreaWidth = 13 * 48;
        int gameAreaHeight = 13 * 48;
        
        // Yön tuşları - use game area boundaries instead of panel boundaries
        if (keyH.upPressed)    { player.direction = "UP"; player.move(gameAreaWidth, gameAreaHeight); }
        else if (keyH.downPressed)  { player.direction = "DOWN"; player.move(gameAreaWidth, gameAreaHeight); }
        else if (keyH.leftPressed)  { player.direction = "LEFT"; player.move(gameAreaWidth, gameAreaHeight); }
        else if (keyH.rightPressed) { player.direction = "RIGHT"; player.move(gameAreaWidth, gameAreaHeight); }
        else {
            // No movement keys pressed - check if sliding on ice
            if (player.isSliding()) {
                player.move(gameAreaWidth, gameAreaHeight);
            }
        }
        // Shoot with Z
        if (keyH.zPressed) {
            Bullet b = player.shoot();
            if (b != null) {
                bullets.add(b);
            }
            keyH.zPressed = false;
        }
        
        // Update enemy tanks and their AI shooting
        updateEnemyTanks();
        
        // Update walls
        for (AbstractWall wall : walls) {
            wall.update();
        }
        
        // Update player bullets
        updatePlayerBullets(gameAreaWidth, gameAreaHeight);
        
        // Update enemy bullets
        updateEnemyBullets(gameAreaWidth, gameAreaHeight);
        
        // Player-wall collision
        for (AbstractWall wall : walls) {
            if (!wall.isDestroyed() && wall.collidesWith(player.getX(), player.getY(), player.getWidth(), player.getHeight())) {
                wall.StumbleEntity(player); // Call StumbleEntity for player
                // Use public getter methods instead of direct field access
                if (!(wall instanceof Bush) && !(wall instanceof Ice)) {
                    player.setPosition(player.getPrevX(), player.getPrevY());
                    break;
                }
            }
        }
        
        // Check for game over conditions
        if (gameManager.isGameOver()) {
            // Handle game over - could show game over screen
            System.out.println("Game Over! Lives: " + gameManager.getPlayerLives() + 
                             ", Eagle Destroyed: " + gameManager.isEagleDestroyed());
        }
    }
    
    private void updateEnemyTanks() {
        // Update BasicTanks
        for (int i = basicTanks.size() - 1; i >= 0; i--) {
            BasicTank tank = basicTanks.get(i);
            if (tank.isExploding()) {
                // Check if explosion is finished
                long explosionTime = System.currentTimeMillis() - tank.getExplosionStartTime();
                if (explosionTime >= 300) { // 0.3 seconds
                    basicTanks.remove(i);
                }
            } else {
                // Move the tank in the main game loop
                tank.move(13 * 48, 13 * 48);
                
                Bullet enemyBullet = tank.aiShoot();
                if (enemyBullet != null) {
                    enemyBullets.add(enemyBullet);
                }
                // Check wall collision for BasicTanks
                checkEnemyTankWallCollision(tank);
            }
        }
        
        // Update FastTanks
        for (int i = fastTanks.size() - 1; i >= 0; i--) {
            FastTank tank = fastTanks.get(i);
            if (tank.isExploding()) {
                // Check if explosion is finished
                long explosionTime = System.currentTimeMillis() - tank.getExplosionStartTime();
                if (explosionTime >= 300) { // 0.3 seconds
                    fastTanks.remove(i);
                }
            } else {
                // Move the tank in the main game loop
                tank.move(13 * 48, 13 * 48);
                
                Bullet enemyBullet = tank.aiShoot();
                if (enemyBullet != null) {
                    enemyBullets.add(enemyBullet);
                }
                // Check wall collision for FastTanks
                checkEnemyTankWallCollision(tank);
            }
        }
        
        // Update PowerTanks
        for (int i = powerTanks.size() - 1; i >= 0; i--) {
            PowerTank tank = powerTanks.get(i);
            if (tank.isExploding()) {
                // Check if explosion is finished
                long explosionTime = System.currentTimeMillis() - tank.getExplosionStartTime();
                if (explosionTime >= 300) { // 0.3 seconds
                    powerTanks.remove(i);
                }
            } else {
                // Move the tank in the main game loop
                tank.move(13 * 48, 13 * 48);
                
                Bullet enemyBullet = tank.aiShoot();
                if (enemyBullet != null) {
                    enemyBullets.add(enemyBullet);
                }
                // Check wall collision for PowerTanks
                checkEnemyTankWallCollision(tank);
            }
        }
        
        // Update ArmorTanks
        for (int i = armorTanks.size() - 1; i >= 0; i--) {
            ArmorTank tank = armorTanks.get(i);
            if (tank.isExploding()) {
                // Check if explosion is finished
                long explosionTime = System.currentTimeMillis() - tank.getExplosionStartTime();
                if (explosionTime >= 300) { // 0.3 seconds
                    armorTanks.remove(i);
                }
            } else {
                // Move the tank in the main game loop
                tank.move(13 * 48, 13 * 48);
                
                Bullet enemyBullet = tank.aiShoot();
                if (enemyBullet != null) {
                    enemyBullets.add(enemyBullet);
                }
                // Check wall collision for ArmorTanks
                checkEnemyTankWallCollision(tank);
            }
        }
        
        // Respawn tanks if all are dead
        if (basicTanks.isEmpty() && fastTanks.isEmpty() && powerTanks.isEmpty() && armorTanks.isEmpty()) {
            respawnEnemyTanks();
        }
    }
    
    private void respawnEnemyTanks() {
        // Spawn new enemy tanks
        for (int i = 0; i < MAX_BASIC_TANKS; i++) {
            basicTanks.add(BasicTank.spawn(player));
        }
        for (int i = 0; i < MAX_FAST_TANKS; i++) {
            fastTanks.add(FastTank.spawn(player));
        }
        for (int i = 0; i < MAX_POWER_TANKS; i++) {
            powerTanks.add(PowerTank.spawn(player));
        }
        for (int i = 0; i < MAX_ARMOR_TANKS; i++) {
            armorTanks.add(ArmorTank.spawn(player));
        }
        
        // Set panel dimensions for all tanks
        setTankPanelDimensions();
    }
    
    private void checkEnemyTankWallCollision(AbstractTank tank) {
        for (AbstractWall wall : walls) {
            if (!wall.isDestroyed() && wall.collidesWith(tank.getX(), tank.getY(), tank.getWidth(), tank.getHeight())) {
                wall.StumbleEntity(tank); // Call StumbleEntity for enemy tank
                // Use public getter methods instead of direct field access
                if (!(wall instanceof Bush) && !(wall instanceof Ice)) {
                    tank.setPosition(tank.getPrevX(), tank.getPrevY());
                    break;
                }
            }
        }
    }
    
    private void updatePlayerBullets(int gameAreaWidth, int gameAreaHeight) {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            if(b != null){
                if (b.active) {
                    b.move();
                    b.disappear(gameAreaWidth, gameAreaHeight);
                    
                    // Bullet-wall collision
                    for (AbstractWall wall : walls) {
                        if (!wall.isDestroyed() && wall.collidesWith(b.x, b.y, 8, 8)) {
                            // Check if wall should be affected by bullets
                            if (wall.isDestructible()) {
                                wall.Explode();
                            }
                            if (wall instanceof Bush || wall instanceof Water) {
                                // Bullet passes through bush or water, no interaction
                                continue;
                            }
                            b.active = false;
                            break;
                        }
                    }
                    
                    // Player bullet vs enemy tank collisions
                    checkPlayerBulletEnemyTankCollisions(b);
                    
                } else {
                    bullets.remove(i);
                    i--;
                }
            }
        }
    }
    
    private void updateEnemyBullets(int gameAreaWidth, int gameAreaHeight) {
        for (int i = 0; i < enemyBullets.size(); i++) {
            Bullet b = enemyBullets.get(i);
            if(b != null){
                if (b.active) {
                    b.move();
                    b.disappear(gameAreaWidth, gameAreaHeight);
                    
                    // Enemy bullet vs wall collision
                    for (AbstractWall wall : walls) {
                        if (!wall.isDestroyed() && wall.collidesWith(b.x, b.y, 8, 8)) {
                            if (wall instanceof Bush || wall instanceof Water) {
                                // Bullet passes through bush or water
                                continue;
                            }
                            b.active = false;
                            break;
                        }
                    }
                    
                    // Enemy bullet vs player collision
                    if (b.active && playerCollidesWithBullet(b)) {
                        player.die();
                        b.active = false;
                    }
                    
                } else {
                    enemyBullets.remove(i);
                    i--;
                }
            }
        }
    }
    
    private void checkPlayerBulletEnemyTankCollisions(Bullet bullet) {
        // Check BasicTanks
        for (BasicTank tank : basicTanks) {
            if (tank.getHit(bullet)) {
                bullet.active = false;
                break;
            }
        }
        
        // Check FastTanks
        for (FastTank tank : fastTanks) {
            if (tank.getHit(bullet)) {
                bullet.active = false;
                break;
            }
        }
        
        // Check PowerTanks
        for (PowerTank tank : powerTanks) {
            if (tank.getHit(bullet)) {
                bullet.active = false;
                break;
            }
        }
        
        // Check ArmorTanks
        for (ArmorTank tank : armorTanks) {
            if (tank.getHit(bullet)) {
                bullet.active = false;
                break;
            }
        }
    }
    
    private boolean playerCollidesWithBullet(Bullet bullet) {
        return bullet.getX() < player.getX() + player.getWidth() &&
               bullet.getX() + bullet.getWidth() > player.getX() &&
               bullet.getY() < player.getY() + player.getHeight() &&
               bullet.getY() + bullet.getHeight() > player.getY();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        // Draw non-bush walls first
        for (AbstractWall wall : walls) {
            if (!(wall instanceof Bush)) {
                wall.draw(g2);
            }
        }
        
        // Draw player
        player.draw(g2);
        
        // Draw enemy tanks
        for (BasicTank tank : basicTanks) {
            tank.draw(g2);
        }
        for (FastTank tank : fastTanks) {
            tank.draw(g2);
        }
        for (PowerTank tank : powerTanks) {
            tank.draw(g2);
        }
        for (ArmorTank tank : armorTanks) {
            tank.draw(g2);
        }
        
        // Draw bushes after player and tanks so they appear on top
        for (AbstractWall wall : walls) {
            if (wall instanceof Bush) {
                wall.draw(g2);
            }
        }
        
        // Draw bullets
        for (Bullet b : bullets) {
            if (b.active) b.draw(g2);
        }
        for (Bullet b : enemyBullets) {
            if (b.active) b.draw(g2);
        }
        
        // Draw grey rectangle for the gap between game map and right border
        g2.setColor(Color.GRAY);
        int gapX = 13 * 48;
        int gapY = 0;
        int gapWidth = getWidth() - gapX;
        int gapHeight = 13 * 48;
        g2.fillRect(gapX, gapY, gapWidth, gapHeight);
        
        // Draw FPS at top right
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        String fpsText = "FPS: " + fps;
        int textWidth = g2.getFontMetrics().stringWidth(fpsText);
        g2.drawString(fpsText, getWidth() - textWidth - 10, 20);
        
        // Draw game info
        String livesText = "Lives: " + gameManager.getPlayerLives();
        g2.drawString(livesText, 10, 20);
        
        String stageText = "Stage: " + gameManager.getCurrentStage();
        g2.drawString(stageText, 10, 40);
        
        String pointsText = "Points: " + player.getPointsgained();
        g2.drawString(pointsText, 10, 60);
        
        // Draw flag and stage number at bottom right
        if (flagIcon != null) {
            int flagX = 48 * 14;
            int flagY = 48 * 11;
            g2.drawImage(flagIcon.getImage(), flagX, flagY, null);
            
            // Draw stage number under the flag
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            String stageNumberText = "STAGE " + currentStageNumber;
            int stageTextWidth = g2.getFontMetrics().stringWidth(stageNumberText);
            g2.drawString(stageNumberText, flagX + (flagIcon.getIconWidth() - stageTextWidth) / 2, 
                         flagY + flagIcon.getIconHeight() + 25);
        }
        
        // Draw lives image at top right
        int livesX = 48 * 14;
        int livesY = 48 * 8;
        int playerLives = gameManager.getPlayerLives();
        
        if (playerLives >= 2 && twoLivesIcon != null) {
            g2.drawImage(twoLivesIcon.getImage(), livesX, livesY, null);
        } else if (playerLives == 1 && oneLiveIcon != null) {
            g2.drawImage(oneLiveIcon.getImage(), livesX, livesY, null);
        } else if (playerLives == 0 && zeroLivesIcon != null) {
            g2.drawImage(zeroLivesIcon.getImage(), livesX, livesY, null);
        }
        
        // Draw game over message
        if (gameManager.isGameOver()) {
            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 48));
            String gameOverText = "GAME OVER";
            int gameOverWidth = g2.getFontMetrics().stringWidth(gameOverText);
            g2.drawString(gameOverText, getWidth()/2 - gameOverWidth/2, getHeight()/2);
        }
        
        g2.dispose();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }
}
