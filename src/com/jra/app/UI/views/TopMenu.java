package com.jra.app.UI.views;

import javax.swing.*;

public class TopMenu extends JMenuBar {
    private JMenu menuFile = new JMenu("File");
    private JMenuItem fileNew = new JMenuItem("New Project");
    private JMenuItem fileOpen = new JMenuItem("Open Project");
    private JMenuItem fileSave = new JMenuItem("Save");
    private JMenuItem fileSaveImage = new JMenuItem("Save current view as Image");
    private JMenu menuView = new JMenu("View");
    private JMenuItem temp = new JMenuItem("Temp");

    public TopMenu() {
    
        menuFile.add(fileNew);
        menuFile.add(fileOpen);
        menuFile.add(fileSave);
        menuFile.add(fileSaveImage);

        menuView.add(temp);

        setBorderPainted(false);
        setOpaque(true);
        this.add(menuView);
        this.add(menuFile);

    }

}
