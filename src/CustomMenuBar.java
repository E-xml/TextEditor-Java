package src;

import java.awt.*;

public class CustomMenuBar extends MenuBar {
    public CustomMenuBar() {
        Menu filemenu = new Menu("File");
        MenuComponent[] filemenuitems = {new MenuItem("New"), new MenuItem("New window"), new MenuSpace(),new MenuItem("Open"), new MenuItem("Save"), new MenuItem("Save as"), new MenuItem("Print")};
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