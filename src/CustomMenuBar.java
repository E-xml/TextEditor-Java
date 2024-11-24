package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class CustomMenuBar extends MenuBar {
    public CustomMenuBar(ExmlEditor parent) {
        Menu filemenu = new Menu("File");
        MenuComponent[] filemenuitems = {new MenuItem("New", new MenuShortcut(KeyEvent.VK_N, false)), new MenuItem("New window"), new MenuSpace(),new MenuItem("Open", new MenuShortcut(KeyEvent.VK_O, false)), new MenuItem("Save", new MenuShortcut(KeyEvent.VK_S, false)), new MenuItem("Save as", new MenuShortcut(KeyEvent.VK_S, true)), new MenuItem("Print"), new MenuItem("Close window", new MenuShortcut(KeyEvent.VK_F4, true)), new MenuSpace(), new MenuItem("Exit", new MenuShortcut(KeyEvent.VK_F4, false))};
        Menu editmenu = new Menu("Edit");
        MenuComponent[] editmenuitems = {new MenuItem("Undo", new MenuShortcut(KeyEvent.VK_Z, false)), new MenuItem("Redo", new MenuShortcut(KeyEvent.VK_Y, false)), new MenuSpace(),new MenuItem("Copy", new MenuShortcut(KeyEvent.VK_C, false)), new MenuItem("Cut", new MenuShortcut(KeyEvent.VK_X, false)), new MenuItem("Paste", new MenuShortcut(KeyEvent.VK_V, false)), new MenuItem("Delete", new MenuShortcut(KeyEvent.VK_DELETE, false)), new MenuSpace(), new MenuItem("Insert here"), new MenuItem("Insert values"), new MenuSpace(), new MenuItem("Replace", new MenuShortcut(KeyEvent.VK_R, false))};
        Menu selectionmenu = new Menu("Selection");
        MenuComponent[] selectionmenuitems = {new MenuItem("Select All", new MenuShortcut(KeyEvent.VK_A, false)), new MenuSpace(), new MenuItem("Copy line up"), new MenuItem("Copy line down"), new MenuItem("Duplicate line")};
        Menu appearancemenu = new Menu("Appearance");
        MenuComponent[] appearancemenuitems = {new MenuItem("Background color"), new MenuItem("Font color"), new CheckboxMenuItem("Title bar", true)};
        Menu helpmenu = new Menu("Help");
        MenuComponent[] helpmenuitems = {new MenuItem("About creator"), new MenuItem("About the project"), new MenuItem("Report issues")};
        for (MenuComponent menuComponent : filemenuitems) {
            if (menuComponent instanceof MenuSpace) {
                filemenu.addSeparator();
            } else if (menuComponent instanceof MenuItem) {
                filemenu.add((MenuItem) menuComponent);
                ((MenuItem) menuComponent).addActionListener(new CustomActionListener<MenuItem>(((MenuItem) menuComponent), parent));
            }
        }

        for (MenuComponent menuComponent : editmenuitems) {
            if (menuComponent instanceof MenuSpace) {
                editmenu.addSeparator();
            } else if (menuComponent instanceof MenuItem) {
                editmenu.add((MenuItem) menuComponent);
                ((MenuItem) menuComponent).addActionListener(new CustomActionListener<MenuItem>(((MenuItem) menuComponent), parent));
            }
        }

        for (MenuComponent menuComponent : selectionmenuitems) {
            if (menuComponent instanceof MenuSpace) {
                selectionmenu.addSeparator();
            } else if (menuComponent instanceof MenuItem) {
                selectionmenu.add((MenuItem) menuComponent);
                ((MenuItem) menuComponent).addActionListener(new CustomActionListener<MenuItem>(((MenuItem) menuComponent), parent));
            }
        }

        for (MenuComponent menuComponent : appearancemenuitems) {
            if (menuComponent instanceof MenuSpace) {
                appearancemenu.addSeparator();
            } else if (menuComponent instanceof CheckboxMenuItem) {
                appearancemenu.add((CheckboxMenuItem) menuComponent);
                ((CheckboxMenuItem) menuComponent).addActionListener(new CustomActionListener<CheckboxMenuItem>(((CheckboxMenuItem) menuComponent), parent));
            } else if (menuComponent instanceof MenuItem) {
                appearancemenu.add((MenuItem) menuComponent);
                ((MenuItem) menuComponent).addActionListener(new CustomActionListener<MenuItem>(((MenuItem) menuComponent), parent));
            }
        }

        for (MenuComponent menuComponent : helpmenuitems) {
            helpmenu.add((MenuItem) menuComponent);
            ((MenuItem) menuComponent).addActionListener(new CustomActionListener<MenuItem>(((MenuItem) menuComponent), parent));
        }

        this.add(filemenu);
        this.add(editmenu);
        this.add(selectionmenu);
        this.add(appearancemenu);
        this.add(helpmenu);
    }
}

class MenuSpace extends MenuComponent {
    public MenuSpace() {}
}

class CustomActionListener<T> implements ActionListener {
    ExmlEditor parent = null;
    T menuItem = null;

    public CustomActionListener(T menuItem, ExmlEditor parent) {
        this.menuItem = menuItem;
        this.parent = parent;
    }

    public void actionPerformed(ActionEvent e) {
        if (menuItem instanceof CheckboxMenuItem) {
            switch (((CheckboxMenuItem) menuItem).getLabel()) {
                case "Title bar":
                    parent.showTitleBar(((CheckboxMenuItem) menuItem).getState());
            }
        } else if (menuItem instanceof MenuItem) {
            switch (((MenuItem) menuItem).getLabel()) {
                case "New":
                    if (!parent.isFileSaved()) {
                        switch (JOptionPane.showOptionDialog(parent, "The current file isn't saved, would save it ?", "Save ?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null)) {
                            case JOptionPane.YES_OPTION:
                                parent.SaveAs();
                                parent.textArea.setText("");
                                parent.OpenedFile = null;
                                parent.TextContent = "";
                                break;
                            case JOptionPane.NO_OPTION:
                                parent.textArea.setText("");
                                parent.OpenedFile = null;
                                parent.TextContent = "";
                                break;
                            case JOptionPane.CANCEL_OPTION:
                                break;
                        }
                    } else {
                        parent.textArea.setText("");
                    }
                    parent.setTitle("Notepad - Untitled");
                    parent.TextContent = "";
                    break;
                case "New window":
                    new ExmlEditor();
                    break;
                case "Open":
                    if (!parent.isFileSaved()) {
                        switch (JOptionPane.showOptionDialog(parent, "The current file isn't saved, would save it ?", "Save ?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null)) {
                            case JOptionPane.YES_OPTION:
                                parent.SaveAs();
                                parent.Open();
                                break;
                            case JOptionPane.NO_OPTION:
                                parent.textArea.setText("");
                                parent.OpenedFile = null;
                                parent.TextContent = "";
                                parent.Open();
                                break;
                            case JOptionPane.CANCEL_OPTION:
                                break;
                        }
                    } else {
                        parent.Open();
                    }

                    break;
                case "Save":
                    if (parent.OpenedFile == null) {
                        parent.SaveAs();
                    } else {
                        parent.Save();
                    }
                    break;
                case "Save as":
                    parent.SaveAs();
                    break;
                case "Print":
                    if (parent.OpenedFile == null) {
                        parent.SaveAs();
                    } else {
                        parent.Save();
                    }
                    parent.print();
                    break;
                case "Close window":
                    if (!parent.isFileSaved()) {
                        switch (JOptionPane.showOptionDialog(parent, "The current file isn't saved, would save it ?", "Save ?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null)) {
                            case JOptionPane.YES_OPTION:
                                parent.SaveAs();
                                break;
                            case JOptionPane.NO_OPTION:
                                parent.dispose();
                                break;
                            case JOptionPane.CANCEL_OPTION:
                                break;
                        }
                    } else {
                        parent.dispose();
                    }
                    break;
                case "Exit":
                    if (!parent.isFileSaved()) {
                        switch (JOptionPane.showOptionDialog(parent, "The current file isn't saved, would save it ?", "Save ?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null)) {
                            case JOptionPane.YES_OPTION:
                                parent.SaveAs();
                                System.exit(0);
                                break;
                            case JOptionPane.NO_OPTION:
                                System.exit(0);
                                break;
                            case JOptionPane.CANCEL_OPTION:
                                break;
                        }
                    } else {
                        System.exit(0);
                    }

                case "Undo":
                    parent.Undo();
                    break;
                case "Redo":
                    parent.Redo();
                    break;
                case "Copy":
                    parent.Copy();
                    break;
                case "Cut":
                    parent.Cut();
                    break;
                case "Paste":
                    parent.Paste();
                    break;
                case "Delete":
                    parent.Delete();
                    break;
                case "Insert here":
                    String res = JOptionPane.showInputDialog(parent, "What would you insert ?");
                    if (res == null) {
                        break;
                    } else {
                        parent.Insert(res);
                    }
                    break;
                case "Insert values":
                    JPanel p = new JPanel();
                    p.setLayout(new GridLayout(1, 1));
                    String[] options = {"File name", "File size", "File path", "Epoch", "Time stamp", "Year", "Month", "Week", "Day", "Day of the week", "Hour", "Minute", "Second", "Random char", "Username", "Ipv4", "Ipv6"};
                    JComboBox<String> comboBox = new JComboBox<>(options);
                    p.add(comboBox);

                    if (JOptionPane.showConfirmDialog(null, p, "Select a value to insert", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
                        parent.Insert(parent.getKey((String) Objects.requireNonNull(comboBox.getSelectedItem())));
                    }


                    break;

                case "Replace":
                    JPanel p2 = new JPanel();
                    p2.setLayout(new GridLayout(2, 1, 20, 40));
                    JTextField oldSequence = new JTextField();
                    p2.add(oldSequence);
                    JTextField newSequence = new JTextField();
                    p2.add(newSequence);
                    if (JOptionPane.showConfirmDialog(null, p2, "Replace", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
                        parent.Replace(oldSequence.getText(), newSequence.getText());
                    }
                    break;
                case "Select All":
                    parent.SelectAll();
                    break;
                case "Copy line up":
                    parent.CopyLineUp();
                    break;
                case "Copy line down":
                    parent.CopyLineDown();
                    break;
                case "Duplicate line":
                    parent.DuplicateLine();
                    break;
                case "Background color":
                    parent.BGColor();
                    break;
                case "Font color":
                    parent.FGColor();
                    break;
                case "About creator":
                    Frame aboutcframe = new Frame();
                    aboutcframe.setSize(450, 225);
                    aboutcframe.setResizable(false);
                    aboutcframe.setLocationRelativeTo(parent);
                    aboutcframe.setLayout(new GridLayout(4, 1));
                    aboutcframe.add(new Label("Created by ZCW-Java91. (https://www.github.com/ZCW-Java91)"));
                    aboutcframe.add(new Label("Forked by E-xml. (https://www.github.com/E-xml)"));
                    aboutcframe.add(new Label("Mail : azertypatata8@gmail.com"));
                    aboutcframe.add(new Label("Youtube : https://www.youtube.com/@TheCpuguy239"));
                    aboutcframe.setTitle("About creator");
                    aboutcframe.addWindowListener(new WindowListener() {
                        @Override
                        public void windowOpened(WindowEvent e) {

                        }

                        @Override
                        public void windowClosing(WindowEvent e) {
                            aboutcframe.dispose();
                        }

                        @Override
                        public void windowClosed(WindowEvent e) {

                        }

                        @Override
                        public void windowIconified(WindowEvent e) {

                        }

                        @Override
                        public void windowDeiconified(WindowEvent e) {

                        }

                        @Override
                        public void windowActivated(WindowEvent e) {

                        }

                        @Override
                        public void windowDeactivated(WindowEvent e) {

                        }
                    });
                    aboutcframe.setVisible(true);
                    break;
                case "About the project":
                    Frame aboutpframe = new Frame();
                    aboutpframe.setSize(450, 225);
                    aboutpframe.setResizable(false);
                    aboutpframe.setLocationRelativeTo(parent);
                    aboutpframe.setLayout(new GridLayout(4, 1));
                    aboutpframe.add(new Label("Project by ZCW-Java91. (https://www.github.com/ZCW-Java91/TextEditor-Java)"));
                    aboutpframe.add(new Label("Remake by E-xml (https://www.github.com/E-xml/TextEditor-Java)"));
                    aboutpframe.add(new Label("Notepad V1.0.0"));
                    aboutpframe.add(new Label("JDK 21 needed for work on this code"));
                    aboutpframe.setTitle("About the project");
                    aboutpframe.addWindowListener(new WindowListener() {
                        @Override
                        public void windowOpened(WindowEvent e) {

                        }

                        @Override
                        public void windowClosing(WindowEvent e) {
                            aboutpframe.dispose();
                        }

                        @Override
                        public void windowClosed(WindowEvent e) {

                        }

                        @Override
                        public void windowIconified(WindowEvent e) {

                        }

                        @Override
                        public void windowDeiconified(WindowEvent e) {

                        }

                        @Override
                        public void windowActivated(WindowEvent e) {

                        }

                        @Override
                        public void windowDeactivated(WindowEvent e) {

                        }
                    });
                    aboutpframe.setVisible(true);
                    break;
                case "Report issues":
                    Frame report = new Frame();
                    report.setSize(450, 225);
                    report.setResizable(false);
                    report.setLocationRelativeTo(parent);
                    report.setLayout(new GridLayout(4, 1));
                    report.add(new Label("Issues detected ?"));
                    report.add(new Label("Original project : https://github.com/ZCW-Java91/TextEditor-Java/issues"));
                    report.add(new Label("Remake project : https://github.com/E-xml/TextEditor-Java/issues"));
                    report.setTitle("About the project");
                    report.addWindowListener(new WindowListener() {
                        @Override
                        public void windowOpened(WindowEvent e) {

                        }

                        @Override
                        public void windowClosing(WindowEvent e) {
                            report.dispose();
                        }

                        @Override
                        public void windowClosed(WindowEvent e) {

                        }

                        @Override
                        public void windowIconified(WindowEvent e) {

                        }

                        @Override
                        public void windowDeiconified(WindowEvent e) {

                        }

                        @Override
                        public void windowActivated(WindowEvent e) {

                        }

                        @Override
                        public void windowDeactivated(WindowEvent e) {

                        }
                    });
                    report.setVisible(true);
                    break;

                default:
                    throw new IllegalArgumentException("You might have forget to update this block");
            }
        }
    }
}