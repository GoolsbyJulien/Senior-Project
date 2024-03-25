package com.jra.app.UI.views;

import com.jra.api.core.Scene;
import com.jra.api.render.MapRenderer;
import com.jra.api.util.LoadProject;
import com.jra.api.util.SaveProject;
import com.jra.api.util.Util;
import com.jra.api.util.Vector;
import com.jra.app.Main;
import com.jra.app.MapObjects.ImageWorld;
import com.jra.app.MapObjects.World;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    }

    public void newProject() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //Create new project window
                JFrame frame = new JFrame("Create new project");
                frame.setSize(750, 750);

                frame.setLocationRelativeTo(null);
                //Content panes
                ScrollPane scrollPane = new ScrollPane();
                JPanel panel = new JPanel();
                panel.setLayout(new GridBagLayout());
                scrollPane.add(panel);
                frame.add(scrollPane);

                //Grid bag constraints
                GridBagConstraints c = new GridBagConstraints();
                c.fill = GridBagConstraints.BOTH;
                c.anchor = GridBagConstraints.CENTER;
                c.insets = new Insets(2, 2, 2, 2);
                c.gridx = 0;
                c.gridy = 0;
                c.ipadx = 15;
                c.ipady = 50;
                c.weightx = 1;

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

                //Perlin
                JPanel mapPanel = new JPanel();
                mapPanel.setSize(500, 250);
                Label seedLabel = new Label("Seed");
                seedLabel.setAlignment(Label.RIGHT);
                TextField seedField = new TextField();
                Button createButton = new Button("Create Project");
                c.gridx = 1;
                c.gridy = 6;
                panel.add(createButton, c);

                //Custom image
                Button chooseImage = new Button("Choose Image");
                JPanel imagePanel = new JPanel();

                //Map preview
                Scene mapScene = new Scene();
                final MapRenderer mapRenderer = new MapRenderer(mapScene);
                frame.setVisible(true);


                perlinButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //Reset threads
                        mapRenderer.setRunning(false);

                        //Remove components from Image section
                        panel.remove(chooseImage);
                        panel.remove(imagePanel);

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
                        panel.add(mapPanel, c);
                        mapRenderer.startUpdateThread();

                        int seed = Util.RandomRange(0, 100000);
                        seedField.setText(String.valueOf(seed));
                        world.generateMap(seed);

                        Main.instance.currentProject.setNewProjectType(0);

                        //Resize window to fit contents
                        frame.setSize(751, 751);
                        frame.setSize(750, 750);
                    }
                });

                imageButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //Remove components from the perlin section
                        panel.remove(seedLabel);
                        panel.remove(seedField);
                        panel.remove(mapPanel);

                        //Add components
                        c.gridx = 0;
                        c.gridy = 5;
                        panel.add(chooseImage, c);
                        c.gridx = 0;
                        c.gridy = 6;
                        panel.add(imagePanel, c);

                        //Resize window to fit contents
                        frame.setSize(751, 751);
                        frame.setSize(750, 750);
                    }
                });

                createButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Main.instance.currentProject.setProjectName(titleField.getText());
                        Main.instance.currentProject.setProjectDescription(descriptionArea.getText());

                        frame.dispose();
                        //Create image project only if user has an image selected
                        if (Main.instance.currentProject.getNewProjectType() == 1) {

                            Main.instance.mapScene.goManager.gameObjects.forEach((n) -> {
                                if (n.getClass() == ImageWorld.class) {
                                    Main.instance.mapScene.removeGameObject(n);
                                }
                                else if(n.getClass() == World.class){
                                    Main.instance.mapScene.removeGameObject(n);
                                }
                            });

                            Main.instance.currentProject.setProjectType(1);
                            Main.instance.addComponent(new ImageWorld());
                        } else {
                            Main.instance.mapScene.goManager.gameObjects.forEach((n) -> {
                                if (n.getClass() == ImageWorld.class) {
                                    Main.instance.mapScene.removeGameObject(n);
                                }
                                else if(n.getClass() == World.class){
                                    Main.instance.mapScene.removeGameObject(n);
                                }
                            });

                            Main.instance.currentProject.setProjectType(0);
                            Main.instance.mapScene.addGameobject(Main.instance.world);
                            Main.instance.world.generateMap(Integer.parseInt(seedField.getText()));
                        }

                        mapRenderer.setRunning(false);
                    }
                });

                chooseImage.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser chooser = new JFileChooser();

                        //Add extension filters
                        chooser.setAcceptAllFileFilterUsed(false);
                        FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter("JPEG", "jpg", "jpeg");
                        FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("PNG", "png");
                        chooser.addChoosableFileFilter(jpgFilter);
                        chooser.addChoosableFileFilter(pngFilter);

                        //Open load explorer
                        int r = chooser.showOpenDialog(null);

                        if (r == JFileChooser.APPROVE_OPTION) {
                            Scanner scanner = null;
                            try {
                                scanner = new Scanner(chooser.getSelectedFile());

                                BufferedImage img = ImageIO.read(chooser.getSelectedFile());
                                JLabel imageLabel = new JLabel(new ImageIcon(img.getScaledInstance(-1, 50, Image.SCALE_SMOOTH)));
                                imagePanel.add(imageLabel);

                                //Temp, set background as image instead of perlin
                                Main.instance.currentProject.setNewProjectType(1);
                                Main.instance.currentProject.setImage(ImageIO.read(chooser.getSelectedFile()));

                                scanner.close();
                            } catch (FileNotFoundException ex) {
                                throw new RuntimeException(ex);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }

                            //Resize window to fit contents
                            frame.setSize(751, 751);
                            frame.setSize(750, 750);
                        }
                    }
                });

                seedField.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (seedField.getText() != null) {
                            //Reset threads
                            mapRenderer.setRunning(false);

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
                            panel.add(mapPanel, c);
                            mapRenderer.startUpdateThread();

                            world.generateMap(Integer.parseInt(seedField.getText()));

                            //Resize window to fit contents
                            frame.setSize(751, 751);
                            frame.setSize(750, 750);
                        }
                    }
                });
            }
        });
    }

    public void saveImage() {

    }

    //Opens settings menu
    public void openSettings() {
        //Create new project window
        JFrame frame = new JFrame("Settings");
        frame.setSize(900, 750);
        frame.setLayout(new GridBagLayout());

        //Grid bag constraints
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(2, 2, 2, 2);
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 15;
        c.ipady = 10;

        //Components
        Label titleLabel = new Label("Project Title:");
        TextField titleField = new TextField(Main.instance.currentProject.getProjectName());
        Button setTitleButton = new Button("Set New Title");
        Label descriptionLabel = new Label("Description:");
        TextArea descriptionArea = new TextArea(Main.instance.currentProject.getProjectDescription());
        Button setDescriptionButton = new Button("Set New Description");

        //Add components to frame
        frame.add(titleLabel, c);
        c.ipadx = 75;
        c.ipady = 8;
        c.gridx = 1;
        frame.add(titleField, c);
        c.gridx = 2;
        frame.add(setTitleButton, c);
        c.gridx = 0;
        c.gridy = 1;
        frame.add(descriptionLabel, c);
        c.gridx = 1;
        frame.add(descriptionArea, c);
        c.gridx = 2;
        frame.add(setDescriptionButton, c);


        frame.setVisible(true);

        setTitleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.instance.currentProject.setProjectName(titleField.getText());
            }
        });

        setDescriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.instance.currentProject.setProjectDescription(descriptionArea.getText());
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
            new SaveProject();
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
            new LoadProject();
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