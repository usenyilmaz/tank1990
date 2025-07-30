package tank1990.game;

public enum GameState {
    MENU,           // Start menu
    PLAYING,        // Game is active
    PAUSED,         // Game is paused
    GAME_OVER,      // Player lost (no lives left or eagle destroyed)
    STAGE_COMPLETE, // Stage finished, ready for next
    VICTORY         // All stages completed
}
