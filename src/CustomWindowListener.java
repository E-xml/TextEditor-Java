package src;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class CustomWindowListener implements WindowListener {
    private ExmlEditor window = null;

    public CustomWindowListener(ExmlEditor window) {
        this.window = window;
    }

    public void windowOpened(WindowEvent e) {

    }

    public void windowClosing(WindowEvent e) {
        if (!window.isFileSaved()) {
            System.out.println("Not saved");
        }

        window.dispose();
    }

    public void windowClosed(WindowEvent e) {

    }

    public void windowIconified(WindowEvent e) {

    }

    public void windowDeiconified(WindowEvent e) {

    }

    public void windowActivated(WindowEvent e) {

    }

    public void windowDeactivated(WindowEvent e) {

    }
}
