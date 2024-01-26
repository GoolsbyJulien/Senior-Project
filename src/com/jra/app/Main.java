package com.jra.app;

import com.jra.api.core.Scene;
import com.jra.api.input.Keyboard;
import com.jra.api.render.MapRenderer;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static MapRenderer mapRenderer;
    public JFrame frame;
    private boolean showFPS = false;

    public Main() {


        Scene helloWorld = new Scene();
        createWindow();


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
                g.drawString("FPS: " + mapRenderer.getLastFPS(), 250, 650);
        };


        helloWorld.addGameobject(new World());
        //helloWorld.addGameobject(new Moutain());

    }


    public void createWindow() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menu = new JMenuBar();
        menu.add(new JMenuItem("Test"));
        frame.setJMenuBar(menu);
        frame.setSize(1280, 720);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();

        panel.setLayout(new BorderLayout());
        panel.add(new JButton("View"), BorderLayout.NORTH);
        panel.setLocation(0, 0);
        panel.setSize(200, 600);

        frame.add(panel);
        frame.setResizable(false);
        frame.setTitle("JRA Map Maker");
        frame.getContentPane().setBackground(new Color(43, 43, 43));
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        new Main();
    }

}
