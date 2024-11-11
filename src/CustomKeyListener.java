package src;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CustomKeyListener implements KeyListener {
    private final ExmlEditor window;
    private boolean flagA = false;

    public CustomKeyListener(ExmlEditor window) {
        this.window = window;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (!window.isFileSaved()) {
            if (!flagA) {
                window.setTitle(window.getTitle() + "*");
                flagA = true;
            }
        } else {
            if (window.getOpenedFile() == null) {
                window.setTitle("Notepad - Untitled");
            } else {
                window.setTitle("Notepad - " + window.getOpenedFile().getName());
            }
            flagA = false;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!window.isFileSaved()) {
            if (!flagA) {
                window.setTitle(window.getTitle() + "*");
                flagA = true;
            }
        } else {
            if (window.getOpenedFile() == null) {
                window.setTitle("Notepad - Untitled");
            } else {
                window.setTitle("Notepad - " + window.getOpenedFile().getName());
            }
            flagA = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_COMMA || e.getKeyCode() == KeyEvent.VK_PERIOD) {
            window.AppendText();
        }
    }
}
