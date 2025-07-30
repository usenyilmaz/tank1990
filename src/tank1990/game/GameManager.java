package tank1990.game;

public class GameManager {
    private static GameManager instance;
    private GameState currentState;
    private int playerLives;
    private int currentStage;
    private int totalStages;
    private boolean eagleDestroyed;
    
    // Singleton pattern
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }
    
    private GameManager() {
        resetGame();
    }
    
    public void resetGame() {
        playerLives = 2;
        currentStage = 1;
        totalStages = 10; // Adjust as needed
        eagleDestroyed = false;
        currentState = GameState.MENU;
    }
    
    public void startGame() {
        currentState = GameState.PLAYING;
        playerLives = 2;
        currentStage = 1;
        eagleDestroyed = false;
    }
    
    public void playerDied() {
        playerLives--;
        if (playerLives <= 0) {
            gameOver();
        } else {
            // Reset player position but keep stage progress
            currentState = GameState.PLAYING;
        }
    }
    
    public void eagleDestroyed() {
        eagleDestroyed = true;
        gameOver();
    }
    
    public void stageComplete() {
        currentStage++;
        if (currentStage > totalStages) {
            currentState = GameState.VICTORY;
        } else {
            currentState = GameState.STAGE_COMPLETE;
        }
    }
    
    public void nextStage() {
        currentState = GameState.PLAYING;
    }
    
    private void gameOver() {
        currentState = GameState.GAME_OVER;
    }
    
    public void pauseGame() {
        if (currentState == GameState.PLAYING) {
            currentState = GameState.PAUSED;
        }
    }
    
    public void resumeGame() {
        if (currentState == GameState.PAUSED) {
            currentState = GameState.PLAYING;
        }
    }
    
    // Getters
    public GameState getCurrentState() { return currentState; }
    public int getPlayerLives() { return playerLives; }
    public int getCurrentStage() { return currentStage; }
    public int getTotalStages() { return totalStages; }
    public boolean isEagleDestroyed() { return eagleDestroyed; }
    public boolean isGameOver() { return currentState == GameState.GAME_OVER; }
    public boolean isPlaying() { return currentState == GameState.PLAYING; }
    public boolean isPaused() { return currentState == GameState.PAUSED; }
    public boolean isVictory() { return currentState == GameState.VICTORY; }
}
