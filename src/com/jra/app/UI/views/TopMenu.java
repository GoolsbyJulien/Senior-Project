package com.jra.app.UI.views;

import com.jra.FormDesigner.SettingsForm;
import com.jra.api.core.MapObject;
import com.jra.api.core.Scene;
import com.jra.api.render.MapRenderer;
import com.jra.api.util.LoadProject;
import com.jra.api.util.SaveProject;
import com.jra.api.util.Util;
import com.jra.api.util.Vector;
import com.jra.app.Main;
import com.jra.app.MapObjects.*;
import com.jra.app.UI.components.Settings;

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
    private JMenuItem fileNew = new JMenuItem("New Project");
    private JMenuItem fileOpen = new JMenuItem("Open Project");
    private JMenuItem fileSaveAs = new JMenuItem("Save as");

    private JMenuItem fileSave = new JMenuItem("Save");

    private JMenuItem fileSaveImage = new JMenuItem("Save current view as Image");
    private JMenuItem fileSettings = new JMenuItem("Settings");
    private JMenu menuView = new JMenu("View");
    private JMenu viewMapView = new JMenu("Perlin Map View");
    private JMenu viewMapOverlay = new JMenu("Map Overlay");
    private JMenu viewLocationView = new JMenu("Location View");


    public TopMenu() {
        //File menu
        menuFile.add(fileNew);
        menuFile.add(fileOpen);
        menuFile.add(fileSave);
        menuFile.add(fileSaveAs);
        menuFile.add(fileSaveImage);
        menuFile.add(fileSettings);

        fileSave.addActionListener(e -> {
            try {
                SaveProject.quickSave("Saves");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        //View Menu
        //Perlin map view
        JMenuItem viewMapColorMap = new JMenuItem("Color Map");
        viewMapColorMap.addActionListener((a) -> {
            Main.instance.mapRenderer.setBackgroundColor(new Color(7, 0, 161));
            Main.instance.world.setMapView(0);
        });

        JMenuItem viewMapNoiseMap = new JMenuItem("Noise Map");
        viewMapNoiseMap.addActionListener((a) -> {
            Main.instance.mapRenderer.setBackgroundColor(new Color(0, 0, 0));
            Main.instance.world.setMapView(1);
        });

        viewMapView.add(viewMapColorMap);
        viewMapView.add(viewMapNoiseMap);
        menuView.add(viewMapView);

        //Map Overlay
        JCheckBoxMenuItem viewPrecipitation = new JCheckBoxMenuItem("View Precipitation");
        JCheckBoxMenuItem viewTemperature = new JCheckBoxMenuItem("View Temperature");

        viewPrecipitation.addActionListener((a) -> {

        });
        viewTemperature.addActionListener((a) -> {

        });

        viewMapOverlay.add(viewPrecipitation);
        viewMapOverlay.add(viewTemperature);
        menuView.add(viewMapOverlay);

        //Location View
        JMenuItem viewPoliticalView = new JMenuItem("Political View");
        JMenuItem viewGeographyView = new JMenuItem("Geography View");

        viewLocationView.add(viewPoliticalView);
        viewLocationView.add(viewGeographyView);
        menuView.add(viewLocationView);

        viewPoliticalView.addActionListener((a) -> {
            politicalView();
        });
        viewGeographyView.addActionListener((a) -> {
            geographyView();
        });

        //Tooltips
        JCheckBoxMenuItem viewTooltips = new JCheckBoxMenuItem("View Tooltips");
        viewTooltips.setState(true);
        viewTooltips.addActionListener((a) -> {
            Main.instance.mapRenderer.toggleTooltips();
        });
        menuView.add(viewTooltips);

        fileSettings.addActionListener((a) -> new Settings());
        fileSaveAs.addActionListener((a) -> new TopMenu().saveImage());
        fileNew.addActionListener((a) -> new TopMenu().newProject());
        fileOpen.addActionListener((a) -> {
            try {
                new LoadProject();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        fileSaveAs.addActionListener((a) -> {
            try {
                SaveProject.saveAs();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

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
                frame.setLocationRelativeTo(Main.instance.frame);

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
                        Main.instance.deleteAllSelectableObjects();

                        //Create image project only if user has an image selected
                        if (Main.instance.currentProject.getNewProjectType() == 1) {

                            Main.instance.mapScene.goManager.gameObjects.forEach((n) -> {
                                if (n.getClass() == ImageWorld.class) {
                                    Main.instance.mapScene.removeGameObject(n);
                                } else if (n.getClass() == World.class) {
                                    Main.instance.mapScene.removeGameObject(n);
                                }
                            });

                            Main.instance.currentProject.setProjectType(1);
                            Main.instance.addComponent(new ImageWorld());
                        } else {
                            Main.instance.mapScene.goManager.gameObjects.forEach((n) -> {
                                if (n.getClass() == ImageWorld.class) {
                                    Main.instance.mapScene.removeGameObject(n);
                                } else if (n.getClass() == World.class) {
                                    Main.instance.mapScene.removeGameObject(n);
                                }
                            });

                            Main.instance.currentProject.setProjectType(0);
                            Main.instance.mapScene.addGameobject(Main.instance.world);
                            Main.instance.world.generateMap(Integer.parseInt(seedField.getText()));
                        }

                        mapRenderer.setRunning(false);
                        frame.dispose();

                    }
                });

                chooseImage.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                        } catch (ClassNotFoundException er) {
                            throw new RuntimeException(er);
                        } catch (InstantiationException er) {
                            throw new RuntimeException(er);
                        } catch (IllegalAccessException er) {
                            throw new RuntimeException(er);
                        } catch (UnsupportedLookAndFeelException er) {
                            throw new RuntimeException(er);
                        }

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

                            try {
                                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                            } catch (ClassNotFoundException er) {
                                throw new RuntimeException(er);
                            } catch (InstantiationException er) {
                                throw new RuntimeException(er);
                            } catch (IllegalAccessException er) {
                                throw new RuntimeException(er);
                            } catch (UnsupportedLookAndFeelException er) {
                                throw new RuntimeException(er);
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

        Main.instance.world.saveToImg();

    }

    public void politicalView(){
        for(MapObject o : Main.instance.mapScene.goManager.gameObjects){
            if(o.getClass() == SelectableObject.class){
                if(((SelectableObject) o).getLocationType() == LocationType.SETTLEMENT ||
                        ((SelectableObject) o).getLocationType() == LocationType.POINT_OF_INTEREST){
                    o.visibility = true;
                }
            } else if (o.getClass() == Road.class) {
                o.visibility = true;
            }
        }
    }

    public void geographyView(){
        for(MapObject o : Main.instance.mapScene.goManager.gameObjects){
            if(o.getClass() == SelectableObject.class){
                if(((SelectableObject) o).getLocationType() == LocationType.SETTLEMENT ||
                        ((SelectableObject) o).getLocationType() == LocationType.POINT_OF_INTEREST){
                    o.visibility = false;
                }
            } else if (o.getClass() == Road.class) {
                o.visibility = false;
            }
        }
    }
}