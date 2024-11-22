package src;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.font.*;
import java.awt.print.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.time.Instant;
import java.util.*;

public class ExmlEditor extends Frame {
    File OpenedFile = null;
    TextArea textArea = new TextArea();
    String TextContent = "";
    public Stack<String> undoStack = new Stack<>();
    public Stack<String> redoStack = new Stack<>();
    public String[] days = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    public String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

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
                if (!text.toString().isEmpty()) {
                    textArea.setText(text.substring(0, text.toString().length() - 1));
                }
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
        AppendText();
    }

    public void Paste() {
        try {
            textArea.insert((String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor), textArea.getCaretPosition());
            AppendText();
        } catch (UnsupportedFlavorException | IOException e) {
            e.fillInStackTrace();
        }

    }

    public void Delete() {
        AppendText();
        if (!textArea.getSelectedText().isEmpty()) {
            textArea.setText(textArea.getText().substring(0, textArea.getSelectionStart()) + textArea.getText().substring(textArea.getSelectionEnd()));
        } else {
            textArea.setText("");
        }
    }

    public void Insert(String text) {
        int pos = textArea.getCaretPosition();
        textArea.insert(text, pos);
        AppendText();
        textArea.setCaretPosition(pos);
    }

    public String getKey(String key) {
        switch (key) {
            case "File name":
                try {
                    return OpenedFile.getName();
                } catch (Exception e) {
                    return "Unavailable";
                }
            case "File size":
                return String.valueOf(textArea.getText().getBytes().length);
            case "File path":
                try {
                    return OpenedFile.getAbsolutePath();
                } catch (Exception e) {
                    return "Unavailable";
                }
            case "Epoch":
                return String.valueOf(Instant.now().getEpochSecond());
            case "Time stamp":
                return String.valueOf(Instant.now()).replace('T', ' ').replace('Z', (char) 0);
            case "Year":
                return String.valueOf(new GregorianCalendar().get(GregorianCalendar.YEAR));
            case "Month":
                return months[new GregorianCalendar().get(GregorianCalendar.MONTH)];
            case "Week":
                return String.valueOf(new GregorianCalendar().get(GregorianCalendar.WEEK_OF_YEAR));
            case "Day":
                return String.valueOf(new GregorianCalendar().get(GregorianCalendar.DAY_OF_MONTH));
            case "Day of the week":
                return days[new GregorianCalendar().get(GregorianCalendar.DAY_OF_WEEK)] + ", " + new GregorianCalendar().get(GregorianCalendar.DAY_OF_MONTH);
            case "Hour":
                return String.valueOf(new GregorianCalendar().get(GregorianCalendar.HOUR_OF_DAY));
            case "Minute":
                return String.valueOf(new GregorianCalendar().get(GregorianCalendar.MINUTE));
            case "Second":
                return String.valueOf(new GregorianCalendar().get(GregorianCalendar.SECOND));
            case "Random char":
                return String.valueOf((char) new Random().nextInt(0, 127));
            case "Username" :
                return System.getenv("USERNAME");
            case "Ipv4":
                try {
                    URL url = new URI("https://api.ipify.org").toURL();
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    in.close();

                    return response.toString();

                } catch (Exception e) {
                    e.fillInStackTrace();
                }

            case "Ipv6":
                try {
                    URL url = new URI("https://api6.ipify.org").toURL();
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    in.close();

                    return response.toString();

                } catch (Exception e) {
                    e.fillInStackTrace();
                }

        }
        return null;
    }

    public void Replace(String oldSequence, String newSequence) {
        textArea.setText(textArea.getText().replace(oldSequence, newSequence));
    }

    public void SelectAll() {
        textArea.setSelectionStart(0);
        textArea.setSelectionEnd(textArea.getText().length());
    }

    public void CopyLineUp() {
        int start = textArea.getText().lastIndexOf("\n", textArea.getCaretPosition() - 1) + 1;
        int end = textArea.getText().indexOf("\n", textArea.getCaretPosition());
        if (end == -1) end = textArea.getText().length();
        if (!textArea.getText().substring(start, end).isEmpty()) {
            textArea.insert(textArea.getText().substring(start, end) + "\n", start);
        }
    }

    public void CopyLineDown() {
        int start = textArea.getText().lastIndexOf("\n", textArea.getCaretPosition() - 1) + 1;
        int end = textArea.getText().indexOf("\n", textArea.getCaretPosition());
        if (end == -1) end = textArea.getText().length();
        if (!textArea.getText().substring(start, end).isEmpty()) {
            textArea.insert("\n" + textArea.getText().substring(start, end), end);
        }
    }

    public void DuplicateLine() {
        int start = textArea.getText().lastIndexOf("\n", textArea.getCaretPosition() - 1) + 1;
        int end = textArea.getText().indexOf("\n", textArea.getCaretPosition());
        if (end == -1) end = textArea.getText().length();
        if (!textArea.getText().substring(start, end).isEmpty()) {
            textArea.insert("\n" + textArea.getText().substring(start, end), end);
        }
    }

    public static void main(String[] args) {
        new ExmlEditor();
    }
}