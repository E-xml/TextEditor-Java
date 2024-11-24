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
        MenuComponent[] apperancemenuitems = {new MenuItem("Select All", new MenuShortcut(KeyEvent.VK_A, false)), new MenuSpace(), new MenuItem("Copy line up"), new MenuItem("Copy line down"), new MenuItem("Duplicate line")};
        Menu securitymenu = new Menu("Security");
        Menu toolsmenu = new Menu("Tools");
        Menu helpmenu = new Menu("Help");
        for (MenuComponent menuComponent : filemenuitems) {
            if (menuComponent instanceof MenuSpace) {
                filemenu.addSeparator();
            } else if (menuComponent instanceof MenuItem) {
                filemenu.add((MenuItem) menuComponent);
                ((MenuItem) menuComponent).addActionListener(new CustomActionListener(((MenuItem) menuComponent), parent));
            }
        }

        for (MenuComponent menuComponent : editmenuitems) {
            if (menuComponent instanceof MenuSpace) {
                editmenu.addSeparator();
            } else if (menuComponent instanceof MenuItem) {
                editmenu.add((MenuItem) menuComponent);
                ((MenuItem) menuComponent).addActionListener(new CustomActionListener(((MenuItem) menuComponent), parent));
            }
        }

        for (MenuComponent menuComponent : selectionmenuitems) {
            if (menuComponent instanceof MenuSpace) {
                selectionmenu.addSeparator();
            } else if (menuComponent instanceof MenuItem) {
                selectionmenu.add((MenuItem) menuComponent);
                ((MenuItem) menuComponent).addActionListener(new CustomActionListener(((MenuItem) menuComponent), parent));
            }
        }

        for (MenuComponent menuComponent : apperancemenuitems) {
            if (menuComponent instanceof MenuSpace) {
                appearancemenu.addSeparator();
            } else if (menuComponent instanceof MenuItem) {
                appearancemenu.add((MenuItem) menuComponent);
                ((MenuItem) menuComponent).addActionListener(new CustomActionListener(((MenuItem) menuComponent), parent));
            }
        }

        this.add(filemenu);
        this.add(editmenu);
        this.add(selectionmenu);
        this.add(appearancemenu);
        this.add(securitymenu);
        this.add(toolsmenu);
        this.add(helpmenu);
    }
}

class MenuSpace extends MenuComponent {
    public MenuSpace() {}
}

class CustomActionListener implements ActionListener {
    ExmlEditor parent = null;
    MenuItem menuItem = null;

    public CustomActionListener(MenuItem menuItem, ExmlEditor parent) {
        this.menuItem = menuItem;
        this.parent = parent;
    }

    public void actionPerformed(ActionEvent e) {
        switch (menuItem.getLabel()) {
            case "New":
                if (!parent.isFileSaved()) {
                    switch (JOptionPane.showOptionDialog(parent, "The current file isn't saved, would save it ?", "Save ?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null)) {
                        case JOptionPane.YES_OPTION: parent.SaveAs(); parent.textArea.setText(""); parent.OpenedFile = null; parent.TextContent = ""; break;
                        case JOptionPane.NO_OPTION: parent.textArea.setText(""); parent.OpenedFile = null; parent.TextContent = ""; break;
                        case JOptionPane.CANCEL_OPTION: break;
                    }
                } else {
                    parent.textArea.setText("");
                }
                parent.setTitle("Notepad - Untitled");
                parent.TextContent = "";
                break;
            case "New window": new ExmlEditor(); break;
            case "Open":
                if (!parent.isFileSaved()) {
                    switch (JOptionPane.showOptionDialog(parent, "The current file isn't saved, would save it ?", "Save ?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null)) {
                        case JOptionPane.YES_OPTION: parent.SaveAs(); parent.Open(); break;
                        case JOptionPane.NO_OPTION: parent.textArea.setText(""); parent.OpenedFile = null; parent.TextContent = ""; parent.Open(); break;
                        case JOptionPane.CANCEL_OPTION: break;
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
            case "Save as": parent.SaveAs(); break;
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
                        case JOptionPane.YES_OPTION: parent.SaveAs(); break;
                        case JOptionPane.NO_OPTION: parent.dispose(); break;
                        case JOptionPane.CANCEL_OPTION: break;
                    }
                } else {
                    parent.dispose();
                }
                break;
            case "Exit":
                if (!parent.isFileSaved()) {
                    switch (JOptionPane.showOptionDialog(parent, "The current file isn't saved, would save it ?", "Save ?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null)) {
                        case JOptionPane.YES_OPTION: parent.SaveAs(); System.exit(0); break;
                        case JOptionPane.NO_OPTION: System.exit(0); break;
                        case JOptionPane.CANCEL_OPTION: break;
                    }
                } else {
                    System.exit(0);
                }

            case "Undo": parent.Undo(); break;
            case "Redo": parent.Redo(); break;
            case "Copy" : parent.Copy(); break;
            case "Cut" : parent.Cut(); break;
            case "Paste" : parent.Paste(); break;
            case "Delete" : parent.Delete(); break;
            case "Insert here" :
                String res = JOptionPane.showInputDialog(parent, "What would you insert ?");
                if (res == null) {
                    break;
                } else {
                    parent.Insert(res);
                }
                break;
            case "Insert values" :
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
                if(JOptionPane.showConfirmDialog(null, p2, "Replace", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
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
            default: throw new IllegalArgumentException("You might have forget to update this block");
        }
    }
}