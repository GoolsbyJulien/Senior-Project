package com.jra.app;

import com.jra.api.core.Scene;
import com.jra.api.input.Keyboard;
import com.jra.api.render.MapRenderer;
import com.jra.api.util.Action;
import com.jra.api.util.Vector;
import com.jra.app.MapObjects.Camera;
import com.jra.app.MapObjects.Road;
import com.jra.app.MapObjects.SelectableObject;
import com.jra.app.MapObjects.World;
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


    public static Main instance;

    public Main() {

        instance = this;
        Scene mapScene = new Scene();
        currentProject = new Project();

        createWindow();

        //       currentProject.setProjectName("D");
        mapRenderer = new MapRenderer(frame, mapScene);
        mapRenderer.setBackgroundColor(new Color(7, 0, 161));
        mapScene.addGameobject(new Camera(mapRenderer));
        mapScene.uiLayer = g -> {

            if (Keyboard.F3)
                showFPS = !showFPS;
            if (!showFPS)
                return;
            g.setColor(Color.WHITE);
            g.setFont(new Font("Verdana", 0, 50));
            g.drawString("FPS: " + mapRenderer.getLastFPS(), 250, 40);
        };

        mapRenderer.eachFrame = new Action() {
            @Override
            public void act() {
                updateComponents(mapScene);
            }
        };


        SelectableObject layer1;
        SelectableObject layer2;
        SelectableObject layer3;

        mapScene.addGameobject(world);
        mapScene.addGameobject(layer1 = new SelectableObject(new Vector(0, 0)));
        mapScene.addGameobject(layer2 = new SelectableObject(new Vector(100, 0)));
        mapScene.addGameobject(layer3 = new SelectableObject(new Vector(50, 0)));

        mapScene.addGameobject(new Road(layer1, layer3));
        mapScene.addGameobject(new Road(layer2, layer3));


    }


    public void updateComponents(Scene currentScene) {

        leftPanel.hierarchy.generateHierarchy(currentScene.goManager.gameObjects);
    }

    public void createWindow() {


        frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TopMenu menu = new TopMenu();
        System.setProperty("awt.useSystemAAFontSettings", "off");
        System.setProperty("swing.aatext", "false");
        frame.setJMenuBar(menu);
        frame.setSize(1280, 720);
        updateTitle();
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        ImageIcon img = new ImageIcon("./assets/Icon.png");


        frame.setIconImage(img.getImage());

        frame.add(leftPanel = new LeftPanel(), BorderLayout.WEST);
        frame.add(rightPanel = new RightPanel(), BorderLayout.EAST);
        frame.add(bottomPanel = new BottomPanel(), BorderLayout.SOUTH);

//        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(43, 43, 43));
        frame.setVisible(true);
    }


    public void updateTitle() {
        frame.setTitle("JRA Map Maker - " + currentProject.getProjectName());

    }

    public static void main(String[] args) {

        new Main();
    }
}
