package src;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.font.*;
import java.awt.print.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class ExmlEditor extends Frame {
    File OpenedFile = null;
    TextArea textArea = new TextArea();
    String TextContent = "";
    public Stack<String> undoStack = new Stack<>();
    public Stack<String> redoStack = new Stack<>();

    public ExmlEditor() {
        this.setTitle("Notepad - Untitled");
        this.setSize(800, 600);
        this.setBackground(new Color(234, 234, 234));
        this.addWindowListener(new CustomWindowListener(this));
        textArea.addKeyListener(new CustomKeyListener(this));
        this.add(textArea);
        this.setMenuBar(new CustomMenuBar(this));
        AppendText();
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
        if (!(fileDialog.getDirectory() == null || fileDialog.getFile() == null)) {
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
        if (!(fileDialog.getDirectory() == null || fileDialog.getFile() == null)) {
            OpenedFile = new File(fileDialog.getDirectory() + "\\" + fileDialog.getFile());
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
                textArea.setText(text.substring(0, text.toString().length() - 1));
                TextContent = textArea.getText();
            } catch (IOException e) {
                e.fillInStackTrace();
            }
        }
    }

    public String getTextContent() {
        return TextContent;
    }

    public File getOpenedFile() {
        return OpenedFile;
    }

    public void print() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new Printable() {
            @Override
            public int print(Graphics g, PageFormat pf, int page) {
                if (page > 0) {
                    return NO_SUCH_PAGE;
                }

                Graphics2D g2d = (Graphics2D) g;
                g2d.translate(pf.getImageableX(), pf.getImageableY());

                String text = TextContent;
                Font font = new Font("Serif", Font.PLAIN, 12);
                g2d.setFont(font);

                AttributedString attributedText = new AttributedString(text);
                attributedText.addAttribute(TextAttribute.FONT, font);
                AttributedCharacterIterator iterator = attributedText.getIterator();

                int y = 0;
                int lineHeight = g2d.getFontMetrics().getHeight();
                LineBreakMeasurer measurer = new LineBreakMeasurer(iterator, g2d.getFontRenderContext());

                float wrappingWidth = (float) pf.getImageableWidth();

                while (measurer.getPosition() < iterator.getEndIndex()) {
                    TextLayout layout = measurer.nextLayout(wrappingWidth);
                    y += (int) layout.getAscent();
                    layout.draw(g2d, 0, y);
                    y += (int) (layout.getDescent() + layout.getLeading());
                }

                return PAGE_EXISTS;
            }
        });

        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
            } catch (PrinterException e) {
                e.fillInStackTrace();
            }
        }
    }

    public void AppendText() {
        undoStack.push(textArea.getText());
        redoStack.clear();
    }

    public void Undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(textArea.getText());
            textArea.setText(undoStack.pop());
        }
    }

    public void Redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(textArea.getText());
            textArea.setText(redoStack.pop());
        }
    }

    public void Copy() {
        if (!textArea.getSelectedText().isEmpty()) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(textArea.getSelectedText()), null);
        } else {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(textArea.getText()), null);
        }
    }

    public void Cut() {
        if (!textArea.getSelectedText().isEmpty()) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(textArea.getSelectedText()), null);
            textArea.setText(textArea.getText().substring(0, textArea.getSelectionStart()) + textArea.getText().substring(textArea.getSelectionEnd()));
        } else {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(textArea.getText()), null);
            textArea.setText("");
        }
    }

    public void Paste() {
        try {
            textArea.insert((String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor), textArea.getCaretPosition());
        } catch (UnsupportedFlavorException | IOException e) {
            e.fillInStackTrace();
        }
    }

    public static void main(String[] args) {
        new ExmlEditor();
    }

    public void Delete() {
        if (!textArea.getSelectedText().isEmpty()) {
            textArea.setText(textArea.getText().substring(0, textArea.getSelectionStart()) + textArea.getText().substring(textArea.getSelectionEnd()));
        } else {
            textArea.setText("");
        }
    }
}