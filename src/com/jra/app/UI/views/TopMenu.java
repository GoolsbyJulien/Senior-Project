package com.jra.app.UI.views;

import com.jra.api.core.Scene;
import com.jra.api.render.MapRenderer;
import com.jra.api.util.Util;
import com.jra.api.util.Vector;
import com.jra.app.Main;
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

public class TopMenu extends JMenuBar {
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
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //Create new project window
                JFrame frame = new JFrame("Create new project");
                frame.setSize(750, 750);

                //Content panes
                ScrollPane scrollPane = new ScrollPane();
                JPanel panel = new JPanel();
                panel.setLayout(new GridBagLayout());
                scrollPane.add(panel);
                frame.add(scrollPane);

                //Grid bag constraints
                GridBagConstraints c = new GridBagConstraints();
                c.fill=GridBagConstraints.BOTH;
                c.anchor=GridBagConstraints.CENTER;
                c.insets = new Insets(2, 2, 2, 2);
                c.gridx = 0;
                c.gridy = 0;
                c.ipadx = 15;
                c.ipady = 50;
                c.weightx=1;

                //Components
                Label titleLabel = new Label("Project Title");
                TextField titleField = new TextField();

                Label descriptionLabel = new Label("Project Description");
                TextArea descriptionArea = new TextArea();
                Label label = new Label("Choose a base image from the options below:");
                Button perlinButton = new Button("Perlin Noise");
                Button imageButton = new Button("Image");

                //Add components to frame
                panel.add(titleLabel, c);
                c.gridx = 1;
                c.ipadx = 200;
                c.ipady = 10;
                panel.add(titleField, c);
                c.gridx = 0;
                c.gridy = 1;
                panel.add(descriptionLabel, c);
                c.gridx = 1;
                c.gridy = 1;
                c.ipadx = 15;
                panel.add(descriptionArea, c);
                c.gridx = 0;
                c.gridy = 2;
                panel.add(label, c);
                c.gridx = 0;
                c.gridy = 3;
                panel.add(perlinButton, c);
                c.gridx = 1;
                c.gridy = 3;
                panel.add(imageButton, c);

                JPanel mapPanel = new JPanel();
                mapPanel.setSize(500, 250);
                Label seedLabel = new Label("Seed");
                seedLabel.setAlignment(Label.RIGHT);
                TextField seedField = new TextField();
                Button createButton = new Button("Create Project");
                c.gridx = 1;
                c.gridy = 6;
                panel.add(createButton,c);

                //Map preview
                Scene mapScene = new Scene();
                final MapRenderer mapRenderer = new MapRenderer(mapScene);
                frame.setVisible(true);

                perlinButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //Add Seed field
                        c.gridx = 0;
                        c.gridy = 5;
                        c.ipadx = 5;
                        panel.add(seedLabel, c);
                        c.gridx = 1;
                        c.gridy = 5;
                        panel.add(seedField, c);

                        //Add map preview
                        mapRenderer.setBackgroundColor(Color.red);
                        mapRenderer.setSize(100, 100);
                        mapRenderer.cameraZoom = 0.1f;
                        mapRenderer.cameraPosition = new Vector(300, 600);
                        mapRenderer.disposeOnRender = false;
                        World world = new World();
                        mapScene.addGameobject(world);
                        mapPanel.add(mapRenderer);
                        mapRenderer.changeScene(mapScene);

                        c.gridx = 0;
                        c.gridy = 6;
                        panel.add(mapPanel,c);
                        mapRenderer.startUpdateThread();

                        int seed = Util.RandomRange(0, 100000);
                        seedField.setText(String.valueOf(seed));
                        world.generateMap(seed);

                        //Resize window to fit contents
                        frame.setSize(751, 751);
                        frame.setSize(750, 750);
                    }
                });

                imageButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //Expand area below
                    }
                });

                createButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Main.instance.currentProject.setProjectName(titleField.getText());
                        frame.setVisible(false);
                        Main.instance.world.generateMap(Integer.parseInt(seedField.getText()));
                    }
                });
            }
        });
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
    public void openSettings() {
        //Create new project window
        JFrame frame = new JFrame("Settings");
        frame.setLayout(new GridBagLayout());
        frame.setSize(750, 750);

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
        frame.add(titleLabel, c);
        c.gridx = 1;
        c.ipadx = 200;
        c.ipady = 10;
        frame.add(titleField, c);
        c.gridx = 2;
        frame.add(setTitleButton, c);

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

class SaveImageAction extends AbstractAction {
    public SaveImageAction() {
        super("Save current view as Image");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new TopMenu().saveImage();
    }
}

class OpenSettingsAction extends AbstractAction {
    public OpenSettingsAction() {
        super("Settings");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new TopMenu().openSettings();
    }
}