package com.jra.app.UI.views;

import com.jra.api.core.MapObject;
import com.jra.api.util.LoadProject;
import com.jra.api.util.SaveProject;
import com.jra.app.Main;
import com.jra.app.MapObjects.*;
import com.jra.app.UI.components.NewProject;
import com.jra.app.UI.components.Settings;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER;

public class TopMenu extends JMenuBar {
    //Menu
    private JMenu menuFile = new JMenu("File");
    private JMenuItem fileNew = new JMenuItem("New Project");
    private JMenuItem fileOpen = new JMenuItem("Open Project");
    private JMenuItem fileSaveAs = new JMenuItem("Save as");

    private JMenuItem fileSave = new JMenuItem("Save");

    private JMenuItem fileSaveImage = new JMenuItem("Save current view as Image");
    private JMenuItem fileSettings = new JMenuItem("Settings");
    private JMenuItem fileAbout = new JMenuItem("About");
    private JMenu menuView = new JMenu("View");
    private JMenu viewMapView = new JMenu("Perlin Map View");
    private JMenu viewMapOverlay = new JMenu("Map Overlay");
    private JMenu viewLocationView = new JMenu("Location View");
    private JMenu menuAdd = new JMenu("Add");
    private JMenuItem addLocation = new JMenuItem("Add location");
    private JMenuItem addRoad = new JMenuItem("Add road");
    private JMenuItem addRiver = new JMenuItem("Add River (X)");
    private JMenuItem addLabel = new JMenuItem("Add Label (X)");
    private JMenuItem addPolygon = new JMenuItem("Add Polygon (X)");
    private JMenu menuWindow = new JMenu("Window");
    private JCheckBoxMenuItem windowShowBaseMap = new JCheckBoxMenuItem("Show base map");
    private JCheckBoxMenuItem windowShowBiomeMap = new JCheckBoxMenuItem("Show biome map");
    private JCheckBoxMenuItem windowShowInspector = new JCheckBoxMenuItem("Show inspector");
    private JCheckBoxMenuItem windowShowHierarchy = new JCheckBoxMenuItem("Show hierarchy");

    //
    public Settings settings = new Settings();


    public TopMenu() {
        //File menu
        menuFile.add(fileNew);
        menuFile.add(fileOpen);
        menuFile.addSeparator();
        menuFile.add(fileSave);
        menuFile.add(fileSaveAs);
        menuFile.add(fileSaveImage);
        menuFile.addSeparator();
        menuFile.add(fileSettings);
        menuFile.add(fileAbout);

        fileSave.addActionListener(e -> {
            try {
                SaveProject.quickSave("Saves");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        fileAbout.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Current Version: " + Main.instance.BUILD_NUMBER);
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
            if(viewPrecipitation.isSelected()){
                Main.instance.world.setOverlayView(1);
            }
            else{
                Main.instance.world.setOverlayView(0);
            }
        });
        viewTemperature.addActionListener((a) -> {
            if(viewTemperature.isSelected()){
                Main.instance.world.setOverlayView(2);
            }
            else{
                Main.instance.world.setOverlayView(0);
            }
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
        menuView.addSeparator();

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

        fileSettings.addActionListener((a) -> settings.setVisible(true));
        fileSaveAs.addActionListener((a) -> new TopMenu().saveImage());
        fileNew.addActionListener((a) -> new NewProject().setVisible(true));
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

        //Add menu
        menuAdd.add(addLocation);
        menuAdd.add(addRoad);

        addLocation.addActionListener((a) -> {
            Main.instance.bottomPanel.addLocation();
        });
        addRoad.addActionListener((a) -> {
            Main.instance.bottomPanel.addRoad();
        });

        menuAdd.addSeparator();
        menuAdd.add(addRiver);
        menuAdd.add(addLabel);
        menuAdd.add(addPolygon);

        //Window menu
        //menuWindow.add(windowShowBaseMap);
        //menuWindow.add(windowShowBiomeMap);
        //menuWindow.addSeparator();
        menuWindow.add(windowShowInspector);
        menuWindow.add(windowShowHierarchy);

        windowShowBaseMap.setState(false);
        windowShowBiomeMap.setState(true);
        windowShowInspector.setState(true);
        windowShowHierarchy.setState(false);

        windowShowBaseMap.addActionListener((a) -> {

        });
        windowShowBiomeMap.addActionListener((a) -> {
            if(windowShowBiomeMap.isSelected()){
                Main.instance.mapTabbedPane.addTab("Biome Map", Main.instance.mapRenderer);
            }else{
                Main.instance.mapRenderer.setRunning(false);
                Main.instance.mapTabbedPane.remove(Main.instance.mapRenderer);
            }
        });
        windowShowInspector.addActionListener((a) -> {
            if(windowShowInspector.isSelected()){
                Main.instance.detailsTabbedPane.addTab("Inspector", Main.instance.rightPanel);
            }else{
                Main.instance.detailsTabbedPane.remove(Main.instance.rightPanel);
            }
        });
        windowShowHierarchy.addActionListener((a) -> {
            if(windowShowHierarchy.isSelected()){
                Main.instance.detailsTabbedPane.addTab("Hierarchy", Main.instance.leftPanel);
            }else{
                Main.instance.detailsTabbedPane.remove(Main.instance.leftPanel);
            }
        });

        //Add menus
        this.add(menuFile);
        this.add(menuView);
        this.add(menuAdd);
        this.add(menuWindow);

        setBorderPainted(false);
        setOpaque(true);

        /**
         * Add menu tooltip
         */
        tooltipFrame.setSize(145,250);
        tooltipFrame.setResizable(false);
        tooltipSP.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        tooltipSP.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
        tooltipPanel.setLayout(new BoxLayout(tooltipPanel, BoxLayout.PAGE_AXIS));

        //Panel buttons
        JButton locationButton = new JButton("Add location");
        JButton roadButton = new JButton("Add road");

        tooltipPanel.add(locationButton);
        tooltipPanel.add(roadButton);

        //Frame
        tooltipFrame.add(tooltipSP);
        tooltipFrame.setAlwaysOnTop(true);

        //Tool tip Action listeners
        locationButton.addActionListener(e -> {
            tooltipFrame.setVisible(false);
            Main.instance.bottomPanel.addLocation();
        });
        roadButton.addActionListener(e -> {
            tooltipFrame.setVisible(false);
            Main.instance.bottomPanel.addRoad();
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

    /**
     * Tooltips
     */
    JFrame tooltipFrame = new JFrame();
    JPanel tooltipPanel = new JPanel();
    JScrollPane tooltipSP = new JScrollPane(tooltipPanel);
    public void addMenuTooltip(){
        if(tooltipFrame.isVisible()){
            tooltipFrame.setVisible(false);
        }else{
            tooltipFrame.setVisible(true);
            tooltipFrame.setLocation(MouseInfo.getPointerInfo().getLocation().x + 5, MouseInfo.getPointerInfo().getLocation().y - 25);
        }
    }
}