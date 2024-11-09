package src;

import javax.swing.*;
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
        boolean CancelPressed = false;

        if (!window.isFileSaved()) {
            switch (JOptionPane.showOptionDialog(window, "The current file isn't saved, would save it ?", "Save ?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null)) {
                case JOptionPane.YES_OPTION: window.SaveAs(); window.textArea.setText(""); window.OpenedFile = null; window.TextContent = ""; break;
                case JOptionPane.NO_OPTION: window.textArea.setText(""); window.OpenedFile = null; window.TextContent = ""; break;
                case JOptionPane.CANCEL_OPTION: CancelPressed = true; break;
            }
        }

        if (!CancelPressed) {
            window.dispose();
        } else {
            CancelPressed = false;
        }
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
