package com.jra.app.UI.views;

import com.jra.app.Main;
import com.jra.app.Project;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class TopMenu extends JMenuBar {
    private JMenu menuFile = new JMenu("File");
    private JMenuItem fileNew = new JMenuItem(new NewProjectAction());
    private JMenuItem fileOpen = new JMenuItem(new LoadMapAction());
    private JMenuItem fileSave = new JMenuItem(new SaveMapAction());
    private JMenuItem fileSaveImage = new JMenuItem(new SaveImageAction());
    private JMenu menuView = new JMenu("View");
    private JMenu viewMapView = new JMenu("Map View");
    public Project currentProject;

    public TopMenu() {

        menuFile.add(fileNew);
        menuFile.add(fileOpen);
        menuFile.add(fileSave);
        menuFile.add(fileSaveImage);


        JMenuItem viewMapColorMap = new JMenuItem("Color Map");
        viewMapColorMap.addActionListener((a) -> {
            Main.instance.world.setMapView(0);
        });

        JMenuItem viewMapNoiseMap = new JMenuItem("Noise Map");
        viewMapNoiseMap.addActionListener((a) -> {
            Main.instance.world.setMapView(1);
        });
        viewMapView.add(viewMapColorMap);
        viewMapView.add(viewMapNoiseMap);
        menuView.add(viewMapView);


        this.add(menuFile);
        this.add(menuView);

        setBorderPainted(false);
        setOpaque(true);
        currentProject = Main.instance.currentProject;
    }

    public void newProject() {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                //Create new project window
                JFrame frame = new JFrame("Create new project");
                frame.setLayout(new GridLayout());
                frame.setSize(500,500);

                //Components
                Label titleLabel = new Label("Project Title");
                TextField titleField = new TextField();
                Label descriptionLabel = new Label("Project Description");
                TextArea descriptionArea = new TextArea();
                Label label = new Label("Choose a base image from the options below:");
                Button perlinButton = new Button("Perlin Noise");
                Button imageButton = new Button("Image");

                //Add components to frame
                frame.add(titleLabel,0);
                frame.add(titleField,0);
                frame.add(descriptionLabel,1);
                frame.add(descriptionArea,1);
                frame.add(label,2);
                frame.add(perlinButton,3);
                frame.add(imageButton,3);

                frame.setVisible(true);
            }
        });
    }

    public void loadNewPerlinScene(){
        Main.instance.world.generateMap();
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
        if (r == JFileChooser.APPROVE_OPTION) {
            File saveFile;

            if (!chooser.getSelectedFile().toString().contains(".txt")) {
                saveFile = new File((chooser.getSelectedFile() + ".txt"));
            } else {
                saveFile = new File(chooser.getSelectedFile().toURI());
            }


            //Write information into file
            FileWriter fw = new FileWriter(saveFile);
            currentProject.setProjectName(saveFile.getName().split(".txt")[0]);

            fw.write("P:" + currentProject.getPerlinSeed() + "\n");
            fw.write("N:" + currentProject.getProjectName());
            fw.close();
        }
    }

    //Opens map save location and displays the map contained in selected file
    public void loadMap() throws IOException {
        //Create save folder if not already present
        Files.createDirectories(Paths.get("Saves"));
        JFileChooser chooser = new JFileChooser("Saves");

        //Add an extension filter
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
        chooser.addChoosableFileFilter(filter);

        //Open load explorer
        int r = chooser.showOpenDialog(null);

        //Load Save file
        if (r == JFileChooser.APPROVE_OPTION) {
            Scanner scanner = new Scanner(chooser.getSelectedFile());

            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();

                if (currentLine.contains("P:")) {
                    String newSeed = currentLine.replace("P:", "");
                    Main.instance.currentProject.setPerlinSeedLoad(Integer.parseInt(newSeed));
                } else if (currentLine.contains("N:")) {
                    String newName = currentLine.replace("N:", "");
                    Main.instance.currentProject.setProjectName(newName);
                }
            }
            scanner.close();
        }
    }
}

class SaveMapAction extends AbstractAction {
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

class LoadMapAction extends AbstractAction {

    public LoadMapAction() {
        super("Open Project");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            new TopMenu().loadMap();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}

class NewProjectAction extends AbstractAction {
    public NewProjectAction() {
        super("New Project");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new TopMenu().newProject();
    }
}

class SaveImageAction extends AbstractAction{
    public SaveImageAction() {
        super("Save current view as Image");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        new TopMenu().saveImage();
    }
}