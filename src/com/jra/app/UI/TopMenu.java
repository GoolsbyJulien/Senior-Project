package com.jra.app.UI;

import javax.swing.*;

public class TopMenu {
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu("File");
    private JMenuItem fileNew = new JMenuItem("New Project");
    private JMenuItem fileOpen = new JMenuItem("Open Project");
    private JMenuItem fileSave = new JMenuItem("Save");
    private JMenuItem fileSaveImage = new JMenuItem("Save current view as Image");
    private JMenu menuView = new JMenu("View");
    private JMenuItem temp = new JMenuItem("Temp");

    public TopMenu(){
        menuFile.add(fileNew);
        menuFile.add(fileOpen);
        menuFile.add(fileSave);
        menuFile.add(fileSaveImage);
        menuBar.add(menuFile);
        menuView.add(temp);
        menuBar.add(menuView);
    }

    public JMenuBar getMenu(){
        return menuBar;
    }
}
