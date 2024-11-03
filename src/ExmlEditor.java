package src;

import java.awt.*;
import java.io.File;

public class ExmlEditor extends Frame {
    static File OpenedFile = null;
    static TextArea textArea = new TextArea();
    static String TextContent = "";

    public ExmlEditor() {
        this.setTitle("Notepad - Untitled");
        this.setSize(800, 600);
        this.setBackground(new Color(234, 234, 234));
        this.addWindowListener(new CustomWindowListener(this));
        this.add(textArea);
        this.setMenuBar(new CustomMenuBar(this));
        this.setVisible(true);
    }

    public void setTextContent(String text) {
        TextContent = text;
    }

    public boolean isFileSaved() {
        if (OpenedFile == null) {
            return textArea.getText().isEmpty();
        } else {
            return textArea.getText().equals(TextContent);
        }
    }

    public static void main(String[] args) {
        new ExmlEditor();
    }
}