package com.jra.app;

import com.jra.api.core.Scene;
import com.jra.api.input.Keyboard;
import com.jra.api.render.MapRenderer;
import com.jra.api.util.Action;
import com.jra.app.MapObjects.Camera;
import com.jra.app.MapObjects.World;
import com.jra.app.UI.views.BottomPanel;
import com.jra.app.UI.views.LeftPanel;
import com.jra.app.UI.views.RightPanel;
import com.jra.app.UI.views.TopMenu;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static MapRenderer mapRenderer;
    public static RightPanel rightPanel;
    public static LeftPanel leftPanel;
    public static BottomPanel bottomPanel;

    public JFrame frame;
    private boolean showFPS = false;
    public static Project currentProject;
    public static World world = new World();

    public Main() {


        Scene helloWorld = new Scene();
        currentProject = new Project();

        createWindow();

        //       currentProject.setProjectName("D");
        mapRenderer = new MapRenderer(frame, helloWorld);
        mapRenderer.setSize(500, 500);
        mapRenderer.setBackgroundColor(Color.blue);
        helloWorld.addGameobject(new Camera(mapRenderer));
        helloWorld.uiLayer = g -> {
            if (Keyboard.F3)
                showFPS = !showFPS;
            g.setColor(Color.WHITE);
            g.setFont(new Font("Verdana", 0, 50));
            if (showFPS)
                g.drawString("FPS: " + mapRenderer.getLastFPS(), 250, 40);
        };

        mapRenderer.eachFrame = new Action() {
            @Override
            public void act() {
                updateComponents(helloWorld);
            }
        };


        helloWorld.addGameobject(world);
        //helloWorld.addGameobject(new Moutain());

    }


    public void updateComponents(Scene curretScene) {

        leftPanel.hierarchy.generateHierarchy(curretScene.goManager.gameObjects);
    }

    public void createWindow() {


        frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TopMenu menu = new TopMenu();
        System.setProperty("awt.useSystemAAFontSettings", "off");
        System.setProperty("swing.aatext", "false");
        frame.setJMenuBar(menu);
        frame.setSize(1280, 720);

        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);


        frame.add(leftPanel = new LeftPanel(), BorderLayout.WEST);
        frame.add(rightPanel = new RightPanel(), BorderLayout.EAST);
        frame.add(bottomPanel = new BottomPanel(), BorderLayout.SOUTH);

//        frame.setResizable(false);
        frame.setTitle("JRA Map Maker - " + currentProject.getProjectName());
        frame.getContentPane().setBackground(new Color(43, 43, 43));
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        new Main();
    }
}
