package tank1990;

import tank1990.ui.GameWindow;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new GameWindow();
        });
    }
} 