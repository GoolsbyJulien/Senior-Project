package com.jra.app.UI.views;

import com.jra.app.Main;
import com.jra.app.Project;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TopMenu extends JMenuBar {
    private JMenu menuFile = new JMenu("File");
    private JMenuItem fileNew = new JMenuItem("New Project");
    private JMenuItem fileOpen = new JMenuItem("Open Project");
    private JMenuItem fileSave = new JMenuItem(new SaveMapAction());
    private JMenuItem fileSaveImage = new JMenuItem("Save current view as Image");
    private JMenu menuView = new JMenu("View");
    private JMenuItem temp = new JMenuItem("Temp");
    public Project currentProject = Main.currentProject;

    public TopMenu() {
    
        menuFile.add(fileNew);
        menuFile.add(fileOpen);
        menuFile.add(fileSave);
        menuFile.add(fileSaveImage);

        menuView.add(temp);

        setBorderPainted(false);
        setOpaque(true);
        this.add(menuFile);
        this.add(menuView);
    }

    public void newProject(){

    }

    public void saveImage() {

    }

    //Saves map to json file
    public void saveMap() throws IOException {
        //Create save folder if not already present
        Files.createDirectories(Paths.get("Saves"));
        JFileChooser chooser = new JFileChooser("Saves");

        //Add an extension filter
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
        chooser.addChoosableFileFilter(filter);

        //Open save explorer
        int r = chooser.showSaveDialog(null);

        //Create Save file
        if(r == JFileChooser.APPROVE_OPTION){
            File saveFile = new File(chooser.getSelectedFile().toURI());

            //Write information into file
            FileWriter fw = new FileWriter(saveFile);
            fw.write(currentProject.getPerlinSeed() + "\n");
            fw.write(currentProject.getProjectName());
            fw.close();
        }
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
