package com.jra.app.UI.components;

import com.jra.app.Main;
import com.jra.app.MapObjects.Biomes;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Settings extends JFrame{
    public Settings() {
        tabbedPane1 = new JTabbedPane();
        generalSP = new JScrollPane();
        generalPanel = new JPanel();
        titleLabel = new JLabel();
        titleField = new JTextField();
        setTitleButton = new JButton();
        descriptionLabel = new JLabel();
        descriptionSP = new JScrollPane();
        descriptionArea = new JTextArea();
        setDescriptionButton = new JButton();
        savePathLabel = new JLabel();
        savePathField = new JTextField();
        seedLabel = new JLabel();
        seedField = new JTextField();
        colorSP = new JScrollPane();
        colorPanel = new JPanel();
        biomesLabel = new JLabel();
        deepWaterLabel = new JLabel();
        deepWaterButton = new JButton();
        shallowWaterLabel = new JLabel();
        shallowWaterButton = new JButton();
        plainsLabel = new JLabel();
        plainsButton = new JButton();
        coastLabel = new JLabel();
        coastButton = new JButton();
        desertLabel = new JLabel();
        desertButton = new JButton();
        hillsLabel = new JLabel();
        hillsButton = new JButton();
        mountainsLabel = new JLabel();
        mountainsButton = new JButton();
        forestLabel = new JLabel();
        forestButton = new JButton();
        tropicalForestLabel = new JLabel();
        tropicalForestButton = new JButton();
        taigaLabel = new JLabel();
        taigaButton = new JButton();
        tundraLabel = new JLabel();
        tundraButton = new JButton();
        defaultButton = new JButton();
        hotkeysSP = new JScrollPane();
        hotkeysPanel = new JPanel();
        basicLabel = new JLabel();
        moveUpLabel = new JLabel();
        moveUpButton = new JButton();
        moveDownLabel = new JLabel();
        moveDownButton = new JButton();
        moveLeftLabel = new JLabel();
        moveLeftButton = new JButton();
        moveRightLabel = new JLabel();
        moveRightButton = new JButton();
        viewportLabel = new JLabel();
        addLocationLabel = new JLabel();
        addLocationButton = new JButton();
        addRoadLabel = new JLabel();
        addRoadButton = new JButton();
        addMenuLabel = new JLabel();
        addMenuButton = new JButton();

        //======== this ========
        setTitle("Settings");
        setIconImage(new ImageIcon("./assets/Icon.png").getImage());
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(new Color(60, 63, 65));

        //======== tabbedPane1 ========
        {
            tabbedPane1.setBorder(null);
            tabbedPane1.setTabPlacement(SwingConstants.LEFT);
            tabbedPane1.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

            //======== generalSP ========
            {
                generalSP.setBorder(null);

                //======== generalPanel ========
                {
                    generalPanel.setBorder(null);
                    generalPanel.setLayout(new MigLayout(
                            "hidemode 3,align left top",
                            // columns
                            "[fill]" +
                                    "[fill]" +
                                    "[fill]",
                            // rows
                            "[]" +
                                    "[]" +
                                    "[]" +
                                    "[]"));

                    //---- titleLabel ----
                    titleLabel.setText("Project Title");
                    generalPanel.add(titleLabel, "cell 0 0");

                    //---- titleField ----
                    titleField.setText(Main.instance.currentProject.getProjectName());
                    generalPanel.add(titleField, "cell 1 0");

                    //---- setTitleButton ----
                    setTitleButton.setText("Set New Title");
                    setTitleButton.addActionListener(e -> setTitle(e));
                    generalPanel.add(setTitleButton, "cell 2 0");

                    //---- descriptionLabel ----
                    descriptionLabel.setText("Description");
                    generalPanel.add(descriptionLabel, "cell 0 1");

                    //======== descriptionSP ========
                    {

                        //---- descriptionArea ----
                        descriptionArea.setLineWrap(true);
                        descriptionSP.setViewportView(descriptionArea);
                        descriptionArea.setText(Main.instance.currentProject.getProjectDescription());
                    }
                    generalPanel.add(descriptionSP, "cell 1 1,width :200:200,height 50:100:100");

                    //---- setDescriptionButton ----
                    setDescriptionButton.setText("Set New Description");
                    setDescriptionButton.addActionListener(e -> setDescription(e));
                    generalPanel.add(setDescriptionButton, "cell 2 1");

                    //---- savePathLabel ----
                    savePathLabel.setText("Save Path");
                    generalPanel.add(savePathLabel, "cell 0 2");
                    savePathField.setEditable(false);
                    generalPanel.add(savePathField, "cell 1 2");

                    //---- seedLabel ----
                    seedLabel.setText("Seed");
                    generalPanel.add(seedLabel, "cell 0 3,alignx right,growx 0");

                    //---- seedField ----
                    seedField.setEditable(false);
                    seedField.setText(String.valueOf(Main.instance.currentProject.getPerlinSeed()));
                    generalPanel.add(seedField, "cell 1 3,width 100:100:100");
                }
                generalSP.setViewportView(generalPanel);
            }
            tabbedPane1.addTab("General", generalSP);

            //======== colorSP ========
            {
                colorSP.setBorder(null);
                colorSP.setVisible(false);
                //======== colorPanel ========
                {
                    colorPanel.setBorder(null);
                    colorPanel.setLayout(new MigLayout(
                            "hidemode 3",
                            // columns
                            "[fill]" +
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
                                    "[]" +
                                    "[]"));

                    //---- biomesLabel ----
                    biomesLabel.setText("Biomes");
                    biomesLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
                    colorPanel.add(biomesLabel, "cell 0 0 3 1");

                    //---- deepWaterLabel ----
                    deepWaterLabel.setText("Deep water");
                    colorPanel.add(deepWaterLabel, "cell 0 1");

                    //---- deepWaterButton ----
                    deepWaterButton.setBackground(Biomes.deepWater);
                    deepWaterButton.addActionListener(e -> deepWater(e));
                    colorPanel.add(deepWaterButton, "cell 1 1,width 20:20:20,height 20:20:20");

                    //---- shallowWaterLabel ----
                    shallowWaterLabel.setText("Shallow water");
                    colorPanel.add(shallowWaterLabel, "cell 0 2");

                    //---- shallowWaterButton ----
                    shallowWaterButton.setBackground(Biomes.shallowWater);
                    shallowWaterButton.addActionListener(e -> shallowWater(e));
                    colorPanel.add(shallowWaterButton, "cell 1 2,width 20:20:20,height 20:20:20");

                    //---- plainsLabel ----
                    plainsLabel.setText("Plains");
                    colorPanel.add(plainsLabel, "cell 0 3");

                    //---- plainsButton ----
                    plainsButton.setBackground(Biomes.plains);
                    plainsButton.addActionListener(e -> plains(e));
                    colorPanel.add(plainsButton, "cell 1 3,width 20:20:20,height 20:20:20");

                    //---- coastLabel ----
                    coastLabel.setText("Coast");
                    colorPanel.add(coastLabel, "cell 0 4");

                    //---- coastButton ----
                    coastButton.setBackground(Biomes.coast);
                    coastButton.addActionListener(e -> coast(e));
                    colorPanel.add(coastButton, "cell 1 4,width 20:20:20,height 20:20:20");

                    //---- desertLabel ----
                    desertLabel.setText("Desert");
                    colorPanel.add(desertLabel, "cell 0 5");

                    //---- desertButton ----
                    desertButton.setBackground(Biomes.desert);
                    desertButton.addActionListener(e -> desert(e));
                    colorPanel.add(desertButton, "cell 1 5,width 20:20:20,height 20:20:20");

                    //---- hillsLabel ----
                    hillsLabel.setText("Hills");
                    colorPanel.add(hillsLabel, "cell 0 6");

                    //---- hillsButton ----
                    hillsButton.setBackground(Biomes.hills);
                    hillsButton.addActionListener(e -> hills(e));
                    colorPanel.add(hillsButton, "cell 1 6,width 20:20:20,height 20:20:20");

                    //---- mountainsLabel ----
                    mountainsLabel.setText("Mountains");
                    colorPanel.add(mountainsLabel, "cell 0 7");

                    //---- mountainsButton ----
                    mountainsButton.setBackground(Biomes.mountains);
                    mountainsButton.addActionListener(e -> mountains(e));
                    colorPanel.add(mountainsButton, "cell 1 7,width 20:20:20,height 20:20:20");

                    //---- forestLabel ----
                    forestLabel.setText("Forest");
                    colorPanel.add(forestLabel, "cell 0 8");

                    //---- forestButton ----
                    forestButton.setBackground(Biomes.forest);
                    forestButton.addActionListener(e -> forest(e));
                    colorPanel.add(forestButton, "cell 1 8,width 20:20:20,height 20:20:20");

                    //---- tropicalForestLabel ----
                    tropicalForestLabel.setText("Tropical Forest");
                    colorPanel.add(tropicalForestLabel, "cell 0 9");

                    //---- tropicalForestButton ----
                    tropicalForestButton.setBackground(Biomes.tropicalForest);
                    tropicalForestButton.addActionListener(e -> tropicalForest(e));
                    colorPanel.add(tropicalForestButton, "cell 1 9,width 20:20:20,height 20:20:20");

                    //---- taigaLabel ----
                    taigaLabel.setText("Taiga");
                    colorPanel.add(taigaLabel, "cell 0 10");

                    //---- taigaButton ----
                    taigaButton.setBackground(Biomes.taiga);
                    taigaButton.addActionListener(e -> taiga(e));
                    colorPanel.add(taigaButton, "cell 1 10,width 20:20:20,height 20:20:20");

                    //---- tundraLabel ----
                    tundraLabel.setText("Tundra");
                    colorPanel.add(tundraLabel, "cell 0 11");

                    //---- tundraButton ----
                    tundraButton.setBackground(Biomes.tundra);
                    tundraButton.addActionListener(e -> tundra(e));
                    colorPanel.add(tundraButton, "cell 1 11,width 20:20:20,height 20:20:20");

                    //---- defaultButton ----
                    defaultButton.setText("Set to Defaults");
                    defaultButton.addActionListener(e -> colorDefaults(e));
                    colorPanel.add(defaultButton, "cell 0 12 3 1");
                }
                colorSP.setViewportView(colorPanel);
            }
            tabbedPane1.addTab("Color Pallet", colorSP);

            //======== hotkeysSP ========
            {
                hotkeysSP.setBorder(null);
                hotkeysSP.setVisible(false);
                //======== hotkeysPanel ========
                {
                    hotkeysPanel.setBorder(null);
                    hotkeysPanel.setLayout(new MigLayout(
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
                                    "[]"));

                    //---- basicLabel ----
                    basicLabel.setText("Basic Controls");
                    basicLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
                    hotkeysPanel.add(basicLabel, "cell 0 0 2 1");

                    //---- moveUpLabel ----
                    moveUpLabel.setText("Move Up");
                    hotkeysPanel.add(moveUpLabel, "cell 0 1");

                    //---- moveUpButton ----
                    moveUpButton.setText("w");
                    hotkeysPanel.add(moveUpButton, "cell 1 1,width 25:25:25");

                    //---- moveDownLabel ----
                    moveDownLabel.setText("Move Down");
                    hotkeysPanel.add(moveDownLabel, "cell 0 2");

                    //---- moveDownButton ----
                    moveDownButton.setText("s");
                    hotkeysPanel.add(moveDownButton, "cell 1 2,width 25:25:25");

                    //---- moveLeftLabel ----
                    moveLeftLabel.setText("Move Left");
                    hotkeysPanel.add(moveLeftLabel, "cell 0 3");

                    //---- moveLeftButton ----
                    moveLeftButton.setText("a");
                    hotkeysPanel.add(moveLeftButton, "cell 1 3,width 25:25:25");

                    //---- moveRightLabel ----
                    moveRightLabel.setText("Move Right");
                    hotkeysPanel.add(moveRightLabel, "cell 0 4");

                    //---- moveRightButton ----
                    moveRightButton.setText("d");
                    hotkeysPanel.add(moveRightButton, "cell 1 4,width 25:25:25");

                    //---- viewportLabel ----
                    viewportLabel.setText("Viewport");
                    viewportLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
                    hotkeysPanel.add(viewportLabel, "cell 0 5 2 1");

                    //---- addLocationLabel ----
                    addLocationLabel.setText("Add Location");
                    hotkeysPanel.add(addLocationLabel, "cell 0 6");

                    //---- addLocationButton ----
                    addLocationButton.setText("l");
                    addLocationButton.addActionListener(e -> addLocationBinding(e));
                    hotkeysPanel.add(addLocationButton, "cell 1 6,width 25:25:25");

                    //---- addRoadLabel ----
                    addRoadLabel.setText("Add Road");
                    hotkeysPanel.add(addRoadLabel, "cell 0 7");

                    //---- addRoadButton ----
                    addRoadButton.setText("r");
                    addRoadButton.addActionListener(e -> addRoadBinding(e));
                    hotkeysPanel.add(addRoadButton, "cell 1 7,width 25:25:25");

                    //---- addMenuLabel ----
                    addMenuLabel.setText("Show add menu");
                    hotkeysPanel.add(addMenuLabel, "cell 0 8");

                    //---- addMenuButton ----
                    addMenuButton.setText("CTRL");
                    addMenuButton.addActionListener(e -> addMenuBinding(e));
                    hotkeysPanel.add(addMenuButton, "cell 1 8,width 50:60:75");
                }
                hotkeysSP.setViewportView(hotkeysPanel);
            }
            tabbedPane1.addTab("Keybindings", hotkeysSP);
        }
        contentPane.add(tabbedPane1, BorderLayout.CENTER);
        setSize(700, 450);
        setLocationRelativeTo(getOwner());
    }

    private void setTitle(ActionEvent e) {
        Main.instance.currentProject.setProjectName(titleField.getText());
        JOptionPane.showMessageDialog(null, "Title changed");
    }

    private void setDescription(ActionEvent e) {
        Main.instance.currentProject.setProjectDescription(descriptionArea.getText());
        JOptionPane.showMessageDialog(null, "Description changed");
    }

    private void addLocationBinding(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Press any key to change binding");
    }

    private void addRoadBinding(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Press any key to change binding");
    }

    private void addMenuBinding(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Press any key to change binding");
    }

    private void deepWater(ActionEvent e) {
        Color newColor = JColorChooser.showDialog(colorPanel, "Deep water color", Biomes.deepWater);
        Biomes.deepWater = newColor;
        deepWaterButton.setBackground(newColor);

        //Refresh map and set background
        Main.instance.world.refreshNoiseMap();
        Main.instance.updateBackground(newColor);
    }

    private void shallowWater(ActionEvent e) {
        Color newColor = JColorChooser.showDialog(colorPanel, "Shallow water color", Biomes.shallowWater);
        Biomes.shallowWater = newColor;
        shallowWaterButton.setBackground(newColor);

        //Refresh map
        Main.instance.world.refreshNoiseMap();
    }

    private void plains(ActionEvent e) {
        Color newColor = JColorChooser.showDialog(colorPanel, "Plains color", Biomes.plains);
        Biomes.plains = newColor;
        plainsButton.setBackground(newColor);

        //Refresh map
        Main.instance.world.refreshNoiseMap();
    }

    private void coast(ActionEvent e) {
        Color newColor = JColorChooser.showDialog(colorPanel, "Coast color", Biomes.coast);
        Biomes.coast = newColor;
        coastButton.setBackground(newColor);

        //Refresh map
        Main.instance.world.refreshNoiseMap();
    }

    private void desert(ActionEvent e) {
        Color newColor = JColorChooser.showDialog(colorPanel, "Desert color", Biomes.desert);
        Biomes.desert = newColor;
        desertButton.setBackground(newColor);

        //Refresh map
        Main.instance.world.refreshNoiseMap();
    }

    private void hills(ActionEvent e) {
        Color newColor = JColorChooser.showDialog(colorPanel, "Hills color", Biomes.hills);
        Biomes.hills = newColor;
        hillsButton.setBackground(newColor);

        //Refresh map
        Main.instance.world.refreshNoiseMap();
    }

    private void mountains(ActionEvent e) {
        Color newColor = JColorChooser.showDialog(colorPanel, "Mountains color", Biomes.mountains);
        Biomes.mountains = newColor;
        mountainsButton.setBackground(newColor);

        //Refresh map
        Main.instance.world.refreshNoiseMap();
    }

    private void forest(ActionEvent e) {
        Color newColor = JColorChooser.showDialog(colorPanel, "Forest color", Biomes.forest);
        Biomes.forest = newColor;
        forestButton.setBackground(newColor);

        //Refresh map
        Main.instance.world.refreshNoiseMap();
    }

    private void tropicalForest(ActionEvent e) {
        Color newColor = JColorChooser.showDialog(colorPanel, "Tropical forest color", Biomes.tropicalForest);
        Biomes.tropicalForest = newColor;
        tropicalForestButton.setBackground(newColor);

        //Refresh map
        Main.instance.world.refreshNoiseMap();
    }

    private void taiga(ActionEvent e) {
        Color newColor = JColorChooser.showDialog(colorPanel, "Taiga color", Biomes.taiga);
        Biomes.taiga = newColor;
        taigaButton.setBackground(newColor);

        //Refresh map
        Main.instance.world.refreshNoiseMap();
    }

    private void tundra(ActionEvent e) {
        Color newColor = JColorChooser.showDialog(colorPanel, "Tundra color", Biomes.tundra);
        Biomes.tundra = newColor;
        tundraButton.setBackground(newColor);

        //Refresh map
        Main.instance.world.refreshNoiseMap();
    }

    private void colorDefaults(ActionEvent e) {
        Biomes.defaultColors();
    }

    private JTabbedPane tabbedPane1;
    private JScrollPane generalSP;
    private JPanel generalPanel;
    private JLabel titleLabel;
    private JTextField titleField;
    private JButton setTitleButton;
    private JLabel descriptionLabel;
    private JScrollPane descriptionSP;
    private JTextArea descriptionArea;
    private JButton setDescriptionButton;
    private JLabel savePathLabel;
    private JTextField savePathField;
    private JLabel seedLabel;
    private JTextField seedField;
    private JScrollPane colorSP;
    private JPanel colorPanel;
    private final JLabel biomesLabel;
    private final JLabel deepWaterLabel;
    public JButton deepWaterButton;
    private final JLabel shallowWaterLabel;
    public JButton shallowWaterButton;
    private final JLabel plainsLabel;
    public JButton plainsButton;
    private final JLabel coastLabel;
    public JButton coastButton;
    private final JLabel desertLabel;
    public JButton desertButton;
    private final JLabel hillsLabel;
    public JButton hillsButton;
    private final JLabel mountainsLabel;
    public JButton mountainsButton;
    private final JLabel forestLabel;
    public JButton forestButton;
    private final JLabel tropicalForestLabel;
    public JButton tropicalForestButton;
    private final JLabel taigaLabel;
    public JButton taigaButton;
    private JLabel tundraLabel;
    public JButton tundraButton;
    private final JButton defaultButton;
    private JScrollPane hotkeysSP;
    private JPanel hotkeysPanel;
    private JLabel basicLabel;
    private JLabel moveUpLabel;
    private JButton moveUpButton;
    private JLabel moveDownLabel;
    private JButton moveDownButton;
    private JLabel moveLeftLabel;
    private JButton moveLeftButton;
    private JLabel moveRightLabel;
    private JButton moveRightButton;
    private JLabel viewportLabel;
    private JLabel addLocationLabel;
    private JButton addLocationButton;
    private JLabel addRoadLabel;
    private JButton addRoadButton;
    private JLabel addMenuLabel;
    private JButton addMenuButton;
}