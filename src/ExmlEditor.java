package src;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;

public class ExmlEditor extends Frame {
    File OpenedFile = null;
    TextArea textArea = new TextArea();
    String TextContent = "";

    public ExmlEditor() {
        this.setTitle("Notepad - Untitled");
        this.setSize(800, 600);
        this.setBackground(new Color(234, 234, 234));
        this.addWindowListener(new CustomWindowListener(this));
        textArea.addKeyListener(new CustomKeyListener(this));
        this.add(textArea);
        this.setMenuBar(new CustomMenuBar(this));
        this.setVisible(true);
    }

    public boolean isFileSaved() {
        if (OpenedFile == null) {
            return textArea.getText().isEmpty();
        } else {
            return textArea.getText().equals(TextContent);
        }
    }

    public void SaveAs() {
        FileDialog fileDialog = new FileDialog(this, "Save", FileDialog.SAVE);
        fileDialog.setVisible(true);
        OpenedFile = new File(fileDialog.getDirectory()+ "\\" + fileDialog.getFile());
        this.setTitle("Notepad - " + OpenedFile.getName());
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(OpenedFile));
            writer.write(textArea.getText());
            writer.close();
            TextContent = textArea.getText();
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    public void Save() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(OpenedFile));
            writer.write(textArea.getText());
            writer.close();
            TextContent = textArea.getText();
            this.setTitle("Notepad - " + OpenedFile.getName());
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    public void Open() {
        FileDialog fileDialog = new FileDialog(this, "Open", FileDialog.LOAD);
        fileDialog.setVisible(true);
        OpenedFile = new File(fileDialog.getDirectory()+ "\\" + fileDialog.getFile());
        this.setTitle("Notepad - " + OpenedFile.getName());
        try {
            StringBuilder text = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(OpenedFile));
            String line = reader.readLine();
            while (line != null) {
                text.append(line).append("\n");
                line = reader.readLine();
            }

            reader.close();
            textArea.setText(text.substring(0, text.toString().length()-1));
            TextContent = textArea.getText();
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    public String getTextContent() {
        return TextContent;
    }

    public File getOpenedFile() {
        return OpenedFile;
    }

    public static void main(String[] args) {
        new ExmlEditor();
    }
}