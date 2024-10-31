package src;

import java.awt.*;

public class CustomMenuBar extends MenuBar {
    public CustomMenuBar() {
        Menu filemenu = new Menu("File");
        MenuComponent[] filemenuitems = {new MenuItem("New"), new MenuItem("New window"), new MenuSpace(),new MenuItem("Open"), new MenuItem("Save"), new MenuItem("Save as"), new MenuItem("Print"), new MenuItem("Close"), new MenuItem("Close Window"), new MenuSpace(), new MenuItem("Exit")};
        Menu editmenu = new Menu("Edit");
        MenuComponent[] editmenuitems = {new MenuItem("Undo"), new MenuItem("Redo"), new MenuSpace()
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