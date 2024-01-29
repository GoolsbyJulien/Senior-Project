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
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        UIManager.put("MenuBar.background", Color.BLACK);
        menu.setBackground(Color.RED);
        menu.setOpaque(true);
        JPanel rightPanel = new JPanel();
        JButton button1 = new JButton("View");
        button1.setBackground(new Color(60, 59, 64));
        button1.setForeground(Color.white);
        button1.setBorderPainted(false);
        button1.setFont(new Font("Arial", Font.PLAIN, 12));
        button1.setFocusPainted(false);
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(button1, BorderLayout.NORTH);
        rightPanel.setLocation(0, 0);
        rightPanel.setPreferredSize(new Dimension(300, 800));
        rightPanel.setBackground(new Color(43, 44, 48));


        JPanel leftPanel = new JPanel();
        JButton button = new JButton("View");
        button.setBackground(new Color(60, 59, 64));
        button.setForeground(Color.white);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setFocusPainted(false);
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(button, BorderLayout.NORTH);
        leftPanel.setLocation(0, 0);
        leftPanel.setPreferredSize(new Dimension(300, 800));
        leftPanel.setBackground(new Color(43, 44, 48));


        JPanel bottom = new JPanel();
        JButton button2 = new JButton("View");
        button2.setBackground(new Color(60, 59, 64));
        button2.setForeground(Color.white);
        button2.setBorderPainted(false);
        button2.setFont(new Font("Arial", Font.PLAIN, 12));
        button2.setFocusPainted(false);
        bottom.setLayout(new BorderLayout());
        bottom.add(button, BorderLayout.NORTH);
        bottom.setLocation(0, 0);
        bottom.setPreferredSize(new Dimension(1280, 200));
        bottom.setBackground(new Color(43, 44, 48));


        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.EAST);
        frame.add(bottom, BorderLayout.SOUTH);

        frame.setResizable(false);
        frame.setTitle("JRA Map Maker");
        frame.getContentPane().setBackground(new Color(43, 43, 43));
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        new Main();
    }

}
