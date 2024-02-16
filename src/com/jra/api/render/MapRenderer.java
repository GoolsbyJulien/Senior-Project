package com.jra.api.render;

import com.jra.api.core.Scene;
import com.jra.api.input.Keyboard;
import com.jra.api.input.Mouse;
import com.jra.api.util.Action;
import com.jra.api.util.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;

public class MapRenderer extends Canvas implements Runnable {

    public boolean disposeOnRender = true;
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 700;

    public boolean isDisplayFPS() {
        return displayFPS;
    }

    public void setDisplayFPS(boolean displayFPS) {
        this.displayFPS = displayFPS;
    }

    private boolean displayFPS = false;
    private boolean isRunning = false;
    private BufferStrategy bs;
    private Graphics g;
    private Color backgroundColor = Color.black;
    private long startTime;
    public Scene currentScene;
    private int lastFps;
    public Action eachFrame = null;
    public Action onClose = null;
    private Mouse mouse = new Mouse();


    public Vector cameraPosition = new Vector(0, 0);
    public double cameraZoom = 1;

    public MapRenderer(Scene scene) {

        setBackground(Color.black);
        setLocation((1280 - 500) / 2, 0);
        currentScene = scene;
        addKeyListener(new Keyboard());
        addMouseMotionListener(mouse);
        addMouseListener(mouse);
        addMouseWheelListener(mouse);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);


    }

    public void startUpdateThread() {
        Thread t = new Thread(this);
        t.start();

    }


    public int getLastFPS() {
        return lastFps;
    }

    public void changeScene(Scene s) {
        currentScene = s;
        currentScene.load();
    }

    public long getTime() {
        return System.currentTimeMillis() - startTime;
    }


    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }


    @Override
    public void run() {

        long initialTime = System.nanoTime();
        final double timeU = 1000000000 / 60;
        final double timeF = 1000000000 / 60;
        double deltaU = 0, deltaF = 0;
        int frames = 0, ticks = 0;
        long timer = System.currentTimeMillis();
        startTime = System.currentTimeMillis();
        isRunning = true;
        changeScene(currentScene);
        while (isRunning) {
            long currentTime = System.nanoTime();
            deltaU += (currentTime - initialTime) / timeU;
            deltaF += (currentTime - initialTime) / timeF;
            initialTime = currentTime;

            if (deltaU >= 1) {
                currentScene.update();


                ticks++;
                deltaU--;
            }


            if (deltaF >= 1) {

                render();

                if (eachFrame != null)
                    eachFrame.act();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - timer > 1000) {

                lastFps = frames;
                if (displayFPS)
                    System.out.println(String.format("UPS: %s, FPS: %s", ticks, frames));
                frames = 0;
                ticks = 0;
                timer += 1000;
            }
        }
        System.exit(0);
    }


    public JPanel getPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(this);
        return panel;
    }

    public void render() {
        bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, getWidth(), getHeight());

        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());

        Graphics2D g2d = (Graphics2D) g;

        g2d.scale(cameraZoom, cameraZoom);
        g2d.translate(-cameraPosition.x, -cameraPosition.y);

        currentScene.render(g);
        g2d.translate(cameraPosition.x, cameraPosition.y);
        g2d.setTransform(new AffineTransform());
        currentScene.drawUI(g);
        bs.show();
        //if (disposeOnRender)
        // g.dispose();

    }
}
