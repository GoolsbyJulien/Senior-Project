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

    public MapRenderer mapRenderer;
    public RightPanel rightPanel;
    public LeftPanel leftPanel;
    public BottomPanel bottomPanel;

    public JFrame frame;
    private boolean showFPS = false;
    public Project currentProject;
    public World world = new World();
    public Scene mapScene;

    public Camera cam;
    public static Main instance;


    public Main() {
        instance = this;
        mapScene = new Scene();
        currentProject = new Project();
        mapRenderer = new MapRenderer(mapScene);
        new StyleGlobals();

        createWindow();

        mapRenderer.setBackgroundColor(new Color(7, 0, 161));
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

    public void createWindow() {
        System.setProperty("awt.useSystemAAFontSettings", "true");
        System.setProperty("swing.aatext", "true");
        frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TopMenu menu = new TopMenu();

        frame.setJMenuBar(menu);
        frame.setSize(1280, 720);
        updateTitle();
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.add(mapRenderer, BorderLayout.CENTER);

        ImageIcon img = new ImageIcon("./assets/Icon.png");


        frame.setIconImage(img.getImage());

        frame.add(leftPanel = new LeftPanel(), BorderLayout.WEST);
        frame.add(rightPanel = new RightPanel(), BorderLayout.EAST);
        frame.add(bottomPanel = new BottomPanel(), BorderLayout.SOUTH);

//        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(43, 43, 43));
        frame.setVisible(true);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
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
