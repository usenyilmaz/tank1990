package tank1990.ui;

import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    public GamePanel() {
        setBackground(Color.BLACK);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("Game Panel (Stub)", 200, 300);
    }
}
