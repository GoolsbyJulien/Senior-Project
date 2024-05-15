package com.jra.app.UI.components;

import com.jra.api.core.Scene;
import com.jra.api.render.MapRenderer;
import com.jra.api.util.Util;
import com.jra.api.util.Vector;
import com.jra.app.Main;
import com.jra.app.MapObjects.ImageWorld;
import com.jra.app.MapObjects.World;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class NewProject extends JFrame{
    public NewProject(){
        //Map preview
        mapScene = new Scene();
        mapRenderer = new MapRenderer(mapScene);

        initComponents();
    }

    private void initComponents(){
        scrollPane = new JScrollPane();
        panel = new JPanel();
        titleLabel = new JLabel();
        titleField = new JTextField();
        descriptionLabel = new JLabel();
        descriptionSP = new JScrollPane();
        descriptionArea = new JTextArea();
        typeLabel = new JLabel();
        perlinButton = new JButton();
        imageButton = new JButton();
        perlinPanel = new JPanel();
        seedLabel = new JLabel();
        seedField = new JTextField();
        mapPanel = new JPanel();
        createButton = new JButton();
        imagePanelParent = new JPanel();
        chooseImage = new JButton();
        imagePanel = new JPanel();
        createButton1 = new JButton();

        //======== this ========
        setResizable(false);
        setTitle("New Project");
        setIconImage(new ImageIcon("./assets/Icon.png").getImage());
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== scrollPane ========
        {
            scrollPane.setBorder(null);

            //======== panel ========
            {
                panel.setLayout(new MigLayout(
                        "hidemode 3",
                        // columns
                        "[fill]" +
                                "[fill]",
                        // rows
                        "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]"));

                //---- titleLabel ----
                titleLabel.setText("Project Title");
                panel.add(titleLabel, "cell 0 0,alignx right,growx 0");
                panel.add(titleField, "cell 1 0,width 200:200:200,height 20:20:20");

                //---- descriptionLabel ----
                descriptionLabel.setText("Project Description");
                panel.add(descriptionLabel, "cell 0 1,alignx right,growx 0");

                //======== descriptionSP ========
                {

                    //---- descriptionArea ----
                    descriptionArea.setLineWrap(true);
                    descriptionSP.setViewportView(descriptionArea);
                }
                panel.add(descriptionSP, "cell 1 1,width 400:400:400,height 100:100:100");

                //---- typeLabel ----
                typeLabel.setText("Choose a base image from the options below:");
                panel.add(typeLabel, "cell 0 2 2 1");

                //---- perlinButton ----
                perlinButton.setText("Perlin Noise");
                perlinButton.setToolTipText("Randomly generated map");
                perlinButton.addActionListener(e -> perlinButton(e));
                panel.add(perlinButton, "cell 0 3,width 100:100:100");

                //---- imageButton ----
                imageButton.setText("Image");
                imageButton.setToolTipText("Custom user provided image");
                imageButton.addActionListener(e -> imageButton(e));
                panel.add(imageButton, "cell 1 3,width 100:100:100");

                //======== perlinPanel ========
                {
                    perlinPanel.setVisible(false);
                    perlinPanel.setLayout(new MigLayout(
                            "hidemode 3",
                            // columns
                            "[fill]" +
                                    "[fill]" +
                                    "[fill]",
                            // rows
                            "[]" +
                                    "[]" +
                                    "[]" +
                                    "[]"));

                    //---- seedLabel ----
                    seedLabel.setText("Seed");
                    seedLabel.setToolTipText("Press the Perlin Noise button to regenerate map");
                    perlinPanel.add(seedLabel, "cell 0 0,alignx right,growx 0");

                    //---- seedField ----
                    seedField.setToolTipText("Press the Perlin Noise button to regenerate map");
                    seedField.addActionListener(e -> seedField(e));
                    perlinPanel.add(seedField, "cell 1 0,width 100:100:100");

                    //======== mapPanel ========
                    {
                        mapPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
                        mapPanel.setLayout(null);
                        {
                            // compute preferred size
                            Dimension preferredSize = new Dimension();
                            for(int i = 0; i < mapPanel.getComponentCount(); i++) {
                                Rectangle bounds = mapPanel.getComponent(i).getBounds();
                                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                            }
                            Insets insets = mapPanel.getInsets();
                            preferredSize.width += insets.right;
                            preferredSize.height += insets.bottom;
                            mapPanel.setMinimumSize(preferredSize);
                            mapPanel.setPreferredSize(preferredSize);
                        }
                    }
                    perlinPanel.add(mapPanel, "cell 0 1,width 100:100:100,height 100:100:100");

                    //---- createButton ----
                    createButton.setText("Create Project");
                    createButton.addActionListener(e -> createButton(e));
                    perlinPanel.add(createButton, "cell 2 3,alignx right,growx 0,width 250:150:150");
                }
                panel.add(perlinPanel, "cell 0 4 2 1");

                //======== imagePanelParent ========
                {
                    imagePanelParent.setVisible(false);
                    imagePanelParent.setLayout(new MigLayout(
                            "hidemode 3",
                            // columns
                            "[fill]" +
                                    "[fill]",
                            // rows
                            "[]" +
                                    "[]"));

                    //---- chooseImage ----
                    chooseImage.setText("Choose Image");
                    chooseImage.addActionListener(e -> chooseImage(e));
                    imagePanelParent.add(chooseImage, "cell 0 0");

                    //======== imagePanel ========
                    {
                        imagePanel.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
                        imagePanel.setLayout(null);

                        {
                            // compute preferred size
                            Dimension preferredSize = new Dimension();
                            for(int i = 0; i < imagePanel.getComponentCount(); i++) {
                                Rectangle bounds = imagePanel.getComponent(i).getBounds();
                                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                            }
                            Insets insets = imagePanel.getInsets();
                            preferredSize.width += insets.right;
                            preferredSize.height += insets.bottom;
                            imagePanel.setMinimumSize(preferredSize);
                            imagePanel.setPreferredSize(preferredSize);
                        }
                    }
                    imagePanelParent.add(imagePanel, "cell 0 1,width 100:100:100,height 100:100:100");

                    //---- createButton1 ----
                    createButton1.setText("Create Project");
                    createButton1.addActionListener(e -> createButton(e));
                    imagePanelParent.add(createButton1, "cell 1 1");
                }
                panel.add(imagePanelParent, "cell 0 5 2 1");
            }
            scrollPane.setViewportView(panel);
        }
        contentPane.add(scrollPane, BorderLayout.CENTER);
        setSize(750, 750);
        setLocationRelativeTo(Main.instance.mapRenderer);
    }

    private void perlinButton(ActionEvent e) {
        //Reset threads
        mapRenderer.setRunning(false);

        //Set panel visibility
        imagePanelParent.setVisible(false);
        perlinPanel.setVisible(true);

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

        mapRenderer.startUpdateThread();

        //Add seed
        int seed = Util.RandomRange(0, 100000);
        seedField.setText(String.valueOf(seed));
        world.generateMap(seed);

        Main.instance.currentProject.setNewProjectType(0);
    }

    private void imageButton(ActionEvent e) {
        perlinPanel.setVisible(false);
        imagePanelParent.setVisible(true);
    }

    private void createButton(ActionEvent e) {
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
        dispose();
    }

    private void chooseImage(ActionEvent e) {
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
        }
    }

    private void seedField(ActionEvent e) {
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

            mapRenderer.startUpdateThread();

            world.generateMap(Integer.parseInt(seedField.getText()));
        }
    }

    //Variables
    private Scene mapScene;
    private final MapRenderer mapRenderer;
    private JScrollPane scrollPane;
    private JPanel panel;
    private JLabel titleLabel;
    private JTextField titleField;
    private JLabel descriptionLabel;
    private JScrollPane descriptionSP;
    private JTextArea descriptionArea;
    private JLabel typeLabel;
    private JButton perlinButton;
    private JButton imageButton;
    private JPanel perlinPanel;
    private JLabel seedLabel;
    private JTextField seedField;
    private JPanel mapPanel;
    private JButton createButton;
    private JPanel imagePanelParent;
    private JButton chooseImage;
    private JPanel imagePanel;
    private JButton createButton1;
}
