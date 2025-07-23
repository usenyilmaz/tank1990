package tank1990.entity;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean wPressed, aPressed, sPressed, dPressed;

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: wPressed = true; break;
            case KeyEvent.VK_A: aPressed = true; break;
            case KeyEvent.VK_S: sPressed = true; break;
            case KeyEvent.VK_D: dPressed = true; break;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: wPressed = false; break;
            case KeyEvent.VK_A: aPressed = false; break;
            case KeyEvent.VK_S: sPressed = false; break;
            case KeyEvent.VK_D: dPressed = false; break;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
}
