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
    private ImageIcon threeLivesIcon;
    private ImageIcon twoLivesIcon;
    private ImageIcon oneLiveIcon;
    private ImageIcon zeroLivesIcon;
    
    // Stage progression variables
    private int waveCount = 0;
    private int totalTanksKilled = 0;
    private static final int TANKS_PER_WAVE = 4;
    private static final int TOTAL_WAVES_PER_STAGE = 3;
    private static final int TOTAL_TANKS_PER_STAGE = TANKS_PER_WAVE * TOTAL_WAVES_PER_STAGE; // 12 tanks
    
    // Pause functionality
    private boolean isPaused = false;
    private JButton pauseButton;

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
        initializePauseButton();
        gameManager.startGame(); // Ensure game starts in PLAYING state
        startGameThread();
        ensurePauseButtonVisible();
    }
    
    private void ensurePauseButtonVisible() {
        // Ensure the pause button is always visible and on top
        if (pauseButton != null) {
            pauseButton.setVisible(true);
            this.setComponentZOrder(pauseButton, 0);
            pauseButton.repaint();
            this.repaint();
        }
    }
    
    private void initializePauseButton() {
        pauseButton = new JButton("PAUSE");
        pauseButton.setBounds(700, 10, 80, 30); // Fixed position instead of using getWidth()
        pauseButton.setOpaque(true);
        pauseButton.setBackground(Color.RED);
        pauseButton.setForeground(Color.WHITE);
        pauseButton.setFont(new Font("Arial", Font.BOLD, 12));
        pauseButton.addActionListener(e -> togglePause());
        pauseButton.setFocusPainted(false); // Don't show focus border
        pauseButton.setBorderPainted(false); // Don't show border
        pauseButton.setContentAreaFilled(true);
        pauseButton.setBorder(null);
        
        // Set the panel to use absolute positioning
        this.setLayout(null);
        this.add(pauseButton);
        
        // Ensure button is visible and on top
        pauseButton.setVisible(true);
        pauseButton.repaint();
        
        // Force the button to be on top of all other components
        this.setComponentZOrder(pauseButton, 0);
        pauseButton.requestFocusInWindow();
    }
    
    private void togglePause() {
        if (isPaused) {
            resumeGame();
        } else {
            pauseGame();
        }
    }
    
    private void pauseGame() {
        isPaused = true;
        pauseButton.setText("RESUME");
        saveGameState();
        gameManager.pauseGame();
    }
    
    private void resumeGame() {
        isPaused = false;
        pauseButton.setText("PAUSE");
        // Don't load game state on resume - just continue from current state
        gameManager.resumeGame();
        // Ensure the game continues properly
        this.requestFocusInWindow();
        ensureGameFocus();
    }
    
    private void ensureGameFocus() {
        // Ensure the game panel has focus for key input
        this.setFocusable(true);
        this.requestFocusInWindow();
        if (keyH != null) {
            this.addKeyListener(keyH);
        }
    }
    
    private void saveGameState() {
        try {
            java.io.FileWriter writer = new java.io.FileWriter("game_save.txt");
            writer.write("Stage:" + currentStageNumber + "\n");
            writer.write("Wave:" + waveCount + "\n");
            writer.write("TanksKilled:" + totalTanksKilled + "\n");
            writer.write("PlayerPoints:" + player.getPointsgained() + "\n");
            writer.write("PlayerLives:" + gameManager.getPlayerLives() + "\n");
            writer.write("PlayerX:" + player.getX() + "\n");
            writer.write("PlayerY:" + player.getY() + "\n");
            
            // Save enemy tanks
            writer.write("BasicTanks:" + basicTanks.size() + "\n");
            for (BasicTank tank : basicTanks) {
                if (!tank.isExploding()) {
                    writer.write("BasicTank:" + tank.getX() + "," + tank.getY() + "\n");
                }
            }
            
            writer.write("FastTanks:" + fastTanks.size() + "\n");
            for (FastTank tank : fastTanks) {
                if (!tank.isExploding()) {
                    writer.write("FastTank:" + tank.getX() + "," + tank.getY() + "\n");
                }
            }
            
            writer.write("PowerTanks:" + powerTanks.size() + "\n");
            for (PowerTank tank : powerTanks) {
                if (!tank.isExploding()) {
                    writer.write("PowerTank:" + tank.getX() + "," + tank.getY() + "\n");
                }
            }
            
            writer.write("ArmorTanks:" + armorTanks.size() + "\n");
            for (ArmorTank tank : armorTanks) {
                if (!tank.isExploding()) {
                    writer.write("ArmorTank:" + tank.getX() + "," + tank.getY() + "\n");
                }
            }
            
            writer.close();
        } catch (Exception e) {
            System.err.println("Error saving game state: " + e.getMessage());
        }
    }
    
    private void loadGameState() {
        try {
            java.io.File file = new java.io.File("game_save.txt");
            if (!file.exists()) return;
            
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file));
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length < 2) continue;
                
                switch (parts[0]) {
                    case "Stage":
                        currentStageNumber = Integer.parseInt(parts[1]);
                        break;
                    case "Wave":
                        waveCount = Integer.parseInt(parts[1]);
                        break;
                    case "TanksKilled":
                        totalTanksKilled = Integer.parseInt(parts[1]);
                        break;
                    case "PlayerPoints":
                        player.ResetPoints();
                        player.IncreasePointsby(Integer.parseInt(parts[1]));
                        break;
                    case "PlayerX":
                        int playerX = Integer.parseInt(parts[1]);
                        int playerY = Integer.parseInt(reader.readLine().split(":")[1]);
                        player.setPosition(playerX, playerY);
                        break;
                }
            }
            reader.close();
        } catch (Exception e) {
            System.err.println("Error loading game state: " + e.getMessage());
        }
    }

    private void initializeEnemyTanks() {
        // Initialize first wave
        waveCount = 1;
        totalTanksKilled = 0;
        
        // Spawn initial wave of tanks
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
            // Try to load threelives.png, fallback to twolives.png if not found
            java.net.URL threeLivesURL = getClass().getResource("threelives.png");
            if (threeLivesURL != null) {
                threeLivesIcon = new ImageIcon(threeLivesURL);
            } else {
                // Use twolives.png as fallback for 3 lives
                threeLivesIcon = new ImageIcon(getClass().getResource("twolives.png"));
            }
            
            twoLivesIcon = new ImageIcon(getClass().getResource("twolives.png"));
            oneLiveIcon = new ImageIcon(getClass().getResource("onelive.png"));
            zeroLivesIcon = new ImageIcon(getClass().getResource("zerolives.png"));
        } catch (Exception e) {
            System.err.println("Error loading lives images: " + e.getMessage());
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
        // Check if game is paused
        if (isPaused) {
            return; // Don't update game logic when paused
        }
        
        // Check game state first
        if (gameManager.isGameOver()) {
            // Game over - stop updating
            return;
        }
        
        // Ensure key handler is working
        if (keyH == null) {
            keyH = new KeyHandler();
            this.addKeyListener(keyH);
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
                    totalTanksKilled++; // Track killed tank
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
                    totalTanksKilled++; // Track killed tank
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
                    totalTanksKilled++; // Track killed tank
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
                    totalTanksKilled++; // Track killed tank
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
        
        // Check if all tanks are dead and handle stage progression
        if (basicTanks.isEmpty() && fastTanks.isEmpty() && powerTanks.isEmpty() && armorTanks.isEmpty()) {
            if (totalTanksKilled >= TOTAL_TANKS_PER_STAGE) {
                // All 12 tanks killed - advance to next stage
                advanceToNextStage();
            } else if (waveCount < TOTAL_WAVES_PER_STAGE) {
                // Spawn next wave
                respawnEnemyTanks();
            }
        }
    }
    
    private void respawnEnemyTanks() {
        // Increment wave count
        waveCount++;
        
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
    
    private void advanceToNextStage() {
        // Increment stage number
        currentStageNumber++;
        
        // Reset wave and tank counters for new stage
        waveCount = 0;
        totalTanksKilled = 0;
        
        // Clear all bullets
        bullets.clear();
        enemyBullets.clear();
        
        // Clear all walls and regenerate stage
        walls.clear();
        
        // Generate new stage layout using StageGenerator
        StageGenerator stageGen = new StageGenerator();
        walls = stageGen.generateStage(currentStageNumber);
        
        // Spawn first wave of new stage
        respawnEnemyTanks();
        
        // Reset player position for new stage
        player.setPosition(48 * 4, 48 * 12);
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
        
        // Draw grey rectangle for the gap between game map and right border FIRST
        g2.setColor(Color.GRAY);
        int gapX = 13 * 48;
        int gapY = 0;
        int gapWidth = getWidth() - gapX;
        int gapHeight = 13 * 48;
        // Don't cover the top area where the pause button is
        g2.fillRect(gapX, gapY + 50, gapWidth, gapHeight - 50);
        
        // Draw non-bush walls first (background)
        for (AbstractWall wall : walls) {
            if (!wall.isDestroyed() && !(wall instanceof Bush)) {
            wall.draw(g2);
            }
        }
        
        // Draw player
        player.draw(g2);
        
        // Draw enemy tanks
        for (BasicTank tank : basicTanks) {
            if (!tank.isExploding()) {
                tank.draw(g2);
            }
        }
        for (FastTank tank : fastTanks) {
            if (!tank.isExploding()) {
                tank.draw(g2);
            }
        }
        for (PowerTank tank : powerTanks) {
            if (!tank.isExploding()) {
                tank.draw(g2);
            }
        }
        for (ArmorTank tank : armorTanks) {
            if (!tank.isExploding()) {
                tank.draw(g2);
            }
        }
        
        // Draw bushes (on top of everything else)
        for (AbstractWall wall : walls) {
            if (!wall.isDestroyed() && wall instanceof Bush) {
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
        
        // Draw UI elements
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Draw stage information
        g2.drawString("Stage: " + currentStageNumber, 10, 20);
        g2.drawString("Wave: " + waveCount + "/" + TOTAL_WAVES_PER_STAGE, 10, 40);
        g2.drawString("Tanks Killed: " + totalTanksKilled + "/" + TOTAL_TANKS_PER_STAGE, 10, 60);
        
        // Draw player points
        g2.drawString("Points: " + player.getPointsgained(), 10, 80);
        
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
            
            // Draw total points under GAME OVER
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 24));
            String pointsText = "Total Points: " + player.getPointsgained();
            int pointsWidth = g2.getFontMetrics().stringWidth(pointsText);
            g2.drawString(pointsText, getWidth()/2 - pointsWidth/2, getHeight()/2 + 50);
        }
        
        // Draw pause screen
        if (isPaused) {
            g2.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black overlay
            g2.fillRect(0, 0, getWidth(), getHeight());
            
            g2.setColor(Color.YELLOW);
            g2.setFont(new Font("Arial", Font.BOLD, 48));
            String pauseText = "PAUSED";
            int pauseWidth = g2.getFontMetrics().stringWidth(pauseText);
            g2.drawString(pauseText, getWidth()/2 - pauseWidth/2, getHeight()/2 - 30);
            
            g2.setFont(new Font("Arial", Font.BOLD, 24));
            String continueText = "Click RESUME to Continue";
            int continueWidth = g2.getFontMetrics().stringWidth(continueText);
            g2.drawString(continueText, getWidth()/2 - continueWidth/2, getHeight()/2 + 30);
        }
        
        g2.dispose();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }
}
