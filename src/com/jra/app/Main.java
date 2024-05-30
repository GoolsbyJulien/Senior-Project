package com.jra.app;

import com.jra.api.core.MapObject;
import com.jra.api.core.Scene;
import com.jra.api.input.Keyboard;
import com.jra.api.render.MapRenderer;
import com.jra.app.MapObjects.Camera;
import com.jra.app.MapObjects.Road;
import com.jra.app.MapObjects.SelectableObject;
import com.jra.app.MapObjects.World;
import com.jra.app.UI.StyleGlobals;
import com.jra.app.UI.views.BottomPanel;
import com.jra.app.UI.views.LeftPanel;
import com.jra.app.UI.views.RightPanel;
import com.jra.app.UI.views.TopMenu;

import javax.swing.*;
import java.awt.*;

public class Main {

    public final String BUILD_NUMBER = "0.2 inDev";

    /**
     * Menu
     */
    public JTabbedPane mapTabbedPane;
    public JTabbedPane detailsTabbedPane;
    public MapRenderer mapRenderer;
    public RightPanel rightPanel; //inspector
    public LeftPanel leftPanel; //hierarchy
    public BottomPanel bottomPanel;
    public TopMenu menu;
    public JFrame frame;

    /**
     * Other
     */
    private boolean showFPS = false;
    public Project currentProject;
    public World world = new World();
    public Scene mapScene;
    private Color bgColor = new Color(7, 0, 161);
    public Camera cam;
    public static Main instance;


    public Main() {
        instance = this;
        mapScene = new Scene();
        currentProject = new Project();
        mapRenderer = new MapRenderer(mapScene);
        new StyleGlobals();

        //Initialize components
        bottomPanel = new BottomPanel();
        leftPanel = new LeftPanel();
        rightPanel = new RightPanel();
        mapTabbedPane = new JTabbedPane();
        detailsTabbedPane = new JTabbedPane();

        createWindow();

        mapRenderer.setBackgroundColor(bgColor);
        mapScene.addGameobject(cam = new Camera(mapRenderer));
        mapScene.uiLayer = g -> {

            if (Keyboard.F3)
                showFPS = !showFPS;
            if (!showFPS)
                return;
            g.setColor(Color.WHITE);
            g.setFont(new Font("Verdana", 0, 50));
            g.drawString("FPS: " + mapRenderer.getLastFPS(), 250, 40);
        };

        updateComponents(mapScene);
        mapScene.addGameobject(world);
        updateComponents(mapScene);
    }

    public void addComponent(MapObject mapObject) {
        updateComponents(mapScene);
        mapScene.addGameobject(mapObject);
    }

    public void updateComponents(Scene currentScene) {
        leftPanel.hierarchy.generateHierarchy(currentScene.goManager.gameObjects);
    }

    public void updateBackground(Color newColor){
        bgColor = newColor;
        mapRenderer.setBackgroundColor(bgColor);
    }

    public void createWindow() {
        /**
         * Frame properties
         */
        System.setProperty("awt.useSystemAAFontSettings", "true");
        System.setProperty("swing.aatext", "true");
        frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu = new TopMenu();

        frame.setJMenuBar(menu);
        frame.setSize(1280, 720);
        updateTitle();
        frame.getContentPane().setLayout(
                new BoxLayout(frame.getContentPane(), BoxLayout.LINE_AXIS)
        );
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(43, 43, 43));
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

        /**
         * Application icon
         */
        ImageIcon img = new ImageIcon("./assets/Icon.png");
        frame.setIconImage(img.getImage());

        /**
         * Tabbed pane properties
         */
        mapTabbedPane.addTab("Biome Map",mapRenderer);
        mapTabbedPane.setToolTipTextAt(0, "Show the biomes of the landmass");
        mapTabbedPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        mapTabbedPane.setMinimumSize(new Dimension(50, 50));
        mapTabbedPane.setPreferredSize(new Dimension(
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()-detailsTabbedPane.getWidth()), Short.MAX_VALUE));
        mapTabbedPane.setPreferredSize(new Dimension(
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()-detailsTabbedPane.getWidth()), Short.MAX_VALUE));

        detailsTabbedPane.addTab("Inspector", rightPanel);
        detailsTabbedPane.setToolTipTextAt(0, "Details of selected object");
        detailsTabbedPane.setAlignmentX(Component.RIGHT_ALIGNMENT);
        detailsTabbedPane.setMinimumSize(new Dimension(50, 50));
        detailsTabbedPane.setPreferredSize(new Dimension(350, Short.MAX_VALUE));
        detailsTabbedPane.setMaximumSize(new Dimension(350, Short.MAX_VALUE));

        /**
         * GUI layout
         */
        frame.add(mapTabbedPane);
        frame.add(Box.createRigidArea(new Dimension(5, 0)));
        frame.add(detailsTabbedPane);

        /**
         * Set Visible
         */
        frame.setVisible(true);
        mapRenderer.startUpdateThread();
    }


    public void deleteAllSelectableObjects() {
        for (MapObject n : mapScene.goManager.gameObjects)
            if (n instanceof SelectableObject || n instanceof Road)
                Main.instance.mapScene.removeGameObject(n);
    }

    public SelectableObject getSelectableObjectsFromUUID(String UUID) {
        for (MapObject n : mapScene.goManager.gameObjects)
            if (n instanceof SelectableObject) {
                SelectableObject o = (SelectableObject) n;
                if (o.UUID.equals(UUID))
                    return (SelectableObject) n;
            }
        return null;
    }

    public void updateTitle() {
        frame.setTitle("JRA Map Maker - " + currentProject.getProjectName());
    }

    public static void main(String[] args) {
        new Main();
    }
}
