package com.jra.app.UI.views;

import com.jra.api.core.Scene;
import com.jra.api.render.MapRenderer;
import com.jra.app.Main;
import com.jra.app.MapObjects.Camera;
import com.jra.app.MapObjects.World;
import com.jra.app.Project;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class TopMenu extends JMenuBar{
    private JMenu menuFile = new JMenu("File");
    private JMenuItem fileNew = new JMenuItem(new NewProjectAction());
    private JMenuItem fileOpen = new JMenuItem(new LoadMapAction());
    private JMenuItem fileSave = new JMenuItem(new SaveMapAction());
    private JMenuItem fileSaveImage = new JMenuItem(new SaveImageAction());
    private JMenuItem fileSettings = new JMenuItem(new OpenSettingsAction());
    private JMenu menuView = new JMenu("View");
    private JMenu viewMapView = new JMenu("Map View");
    public Project currentProject;

    public TopMenu() {

        menuFile.add(fileNew);
        menuFile.add(fileOpen);
        menuFile.add(fileSave);
        menuFile.add(fileSaveImage);
        menuFile.add(fileSettings);


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
                frame.setLayout(new GridBagLayout());
                frame.setSize(750,750);

                //Grid bag constraints
                GridBagConstraints c = new GridBagConstraints();
                c.insets = new Insets(2, 2, 2, 2);
                c.gridx = 0;
                c.gridy = 0;
                c.ipadx = 15;
                c.ipady = 50;


                //Components
                Label titleLabel = new Label("Project Title");
                TextField titleField = new TextField();

                Label descriptionLabel = new Label("Project Description");
                TextArea descriptionArea = new TextArea();
                Label label = new Label("Choose a base image from the options below:");
                Button perlinButton = new Button("Perlin Noise");
                Button imageButton = new Button("Image");

                //Add components to frame
                frame.add(titleLabel,c);
                c.gridx = 1; c.ipadx = 200; c.ipady = 10;frame.add(titleField,c);
                c.gridx = 0; c.gridy = 1; frame.add(descriptionLabel,c);
                c.gridx = 1; c.gridy = 1; c.ipadx = 15; frame.add(descriptionArea,c);
                c.gridx = 0; c.gridy = 2; frame.add(label,c);
                c.gridx = 0; c.gridy = 3; frame.add(perlinButton,c);
                c.gridx = 1; c.gridy = 3; frame.add(imageButton,c);

                //Lower panel
                JScrollPane panel = new JScrollPane();
                panel.setSize(750,300);
                c.gridx = 0; c.gridy = 4; frame.add(panel,c);

                JPanel mapPanel = new JPanel();
                mapPanel.setSize(500,250);
                Label seedLabel = new Label("Seed");
                TextField seedField = new TextField();
                Button createButton = new Button("Create Project");
                panel.add(mapPanel);

                //Map preview
                Scene mapScene = new Scene();
                final MapRenderer[] mapRenderer = new MapRenderer[1];
                World world = new World();

                frame.setVisible(true);

                perlinButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //Create preview of map with an option to change the seed
                        panel.add(mapRenderer[0] = new MapRenderer(mapPanel,mapScene));
                        mapRenderer[0].setBackgroundColor(new Color(7, 0, 161));
                        mapScene.addGameobject(new Camera(mapRenderer[0]));
                        mapScene.addGameobject(world);

                        //Main.instance.currentProject.setProjectName(titleField.getText());
                        //loadNewPerlinScene();
                        //frame.setVisible(false);
                    }
                });

                imageButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //Expand area below
                    }
                });
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

    //Opens settings menu
    public void openSettings(){
        //Create new project window
        JFrame frame = new JFrame("Settings");
        frame.setLayout(new GridBagLayout());
        frame.setSize(750,750);

        //Grid bag constraints
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(2, 2, 2, 2);
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 15;
        c.ipady = 50;

        //Components
        Label titleLabel = new Label("Project Title:");
        TextField titleField = new TextField(Main.instance.currentProject.getProjectName());
        Button setTitleButton = new Button("Set New Title");

        //Add components to frame
        frame.add(titleLabel,c);
        c.gridx = 1; c.ipadx = 200; c.ipady = 10; frame.add(titleField,c);
        c.gridx = 2; frame.add(setTitleButton,c);

        frame.setVisible(true);

        setTitleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.instance.currentProject.setProjectName(titleField.getText());
            }
        });
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

class OpenSettingsAction extends AbstractAction{
    public OpenSettingsAction(){ super("Settings");}
    @Override
    public void actionPerformed(ActionEvent e) {
        new TopMenu().openSettings();
    }
}