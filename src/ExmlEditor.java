package src;

import java.awt.*;

public class ExmlEditor extends Frame {
    public ExmlEditor() {
        this.setTitle("Notepad - Untitled");
        this.setSize(800, 600);
        this.setBackground(new Color(234, 234, 234));
        this.addWindowListener(new CustomWindowListener());
        TextArea textArea = new TextArea();
        this.add(textArea);
        this.setMenuBar(new CustomMenuBar());
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new ExmlEditor();
    }
}