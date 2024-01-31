package com.jra.app.UI.views;

import com.jra.app.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class TopMenu extends JMenuBar {
    private JMenu menuFile = new JMenu("File");
    private JMenuItem fileNew = new JMenuItem("New Project");
    private JMenuItem fileOpen = new JMenuItem("Open Project");
    private JMenuItem fileSave = new JMenuItem(new SaveMapAction());
    private JMenuItem fileSaveImage = new JMenuItem("Save current view as Image");
    private JMenu menuView = new JMenu("View");
    private JMenuItem temp = new JMenuItem("Temp");
    private String savePath = System.getProperty("user.dir");
    public Desktop desktop = Desktop.getDesktop();
    public Project currentProject = new Project();

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

    public void newProject(){

    }

    public void saveImage() {

    }

    //Saves map to json file
    public void saveMap() throws IOException {
        //Save file
        File saveFile = new File(savePath + "\\test.txt");

        if(saveFile.createNewFile()){
            System.out.println("File created");
        }
        else {
            saveFile.delete();
            saveFile.createNewFile();
            System.out.println("File override");
        }

        //desktop.print(saveFile);

        //Write information into file
        FileWriter fw = new FileWriter(saveFile);
        fw.write(currentProject.getPerlinSeed() + "\n");
        fw.write(currentProject.getProjectName());
        fw.close();
    }

    //Opens map save location and displays the map contained in selected file
    public void loadMap() throws IOException{

    }

    //Opens explorer to save file location
    public void openExplorer(){

    }
}

class SaveMapAction extends AbstractAction{
    public SaveMapAction() {
        super("Save Map");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            new TopMenu().saveMap();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
