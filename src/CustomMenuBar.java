package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomMenuBar extends MenuBar {
    public CustomMenuBar(ExmlEditor parent) {
        Menu filemenu = new Menu("File");
        MenuComponent[] filemenuitems = {new MenuItem("New"), new MenuItem("New window"), new MenuSpace(),new MenuItem("Open"), new MenuItem("Save"), new MenuItem("Save as"), new MenuItem("Print"), new MenuItem("Close"), new MenuItem("Close window"), new MenuSpace(), new MenuItem("Exit")};
        Menu editmenu = new Menu("Edit");
        Menu selectionmenu = new Menu("Selection");
        Menu appearancemenu = new Menu("Appearance");
        Menu securitymenu = new Menu("Security");
        Menu toolsmenu = new Menu("Tools");
        Menu helpmenu = new Menu("Help");
        for (MenuComponent menuComponent : filemenuitems) {
            if (menuComponent instanceof MenuSpace) {
                filemenu.addSeparator();
            } else { //Avoiding a ClassCastException
                filemenu.add((MenuItem) menuComponent);
                ((MenuItem) menuComponent).addActionListener(new CustomActionListener(((MenuItem) menuComponent).getLabel(), parent));
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
    String name = "";

    public CustomActionListener(String name, ExmlEditor parent) {
        this.name = name;
        this.parent = parent;
    }

    public void actionPerformed(ActionEvent e) {
        switch (name) {
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
                parent.TextContent = "";
                break;
            case "Open":
                if (!parent.isFileSaved()) {
                    switch (JOptionPane.showOptionDialog(parent, "The current file isn't saved, would save it ?", "Save ?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null)) {
                        case JOptionPane.YES_OPTION: parent.SaveAs(); parent.Open(); break;
                        case JOptionPane.NO_OPTION: parent.textArea.setText(""); parent.OpenedFile = null; parent.TextContent = ""; break;
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
            case "Print": parent.printText(); break;
            case "New window": new ExmlEditor(); break;
            case "Close window": parent.dispose(); break;
            case "Exit": System.exit(0);
            default: throw new IllegalArgumentException("You might have forget to update this block");
        }
    }
}