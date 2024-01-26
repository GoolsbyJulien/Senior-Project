package com.jra.api.render;

import com.jra.api.core.Scene;
import com.jra.api.input.Keyboard;
import com.jra.api.input.Mouse;
import com.jra.api.util.Action;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;

public class Window extends Canvas implements Runnable {


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
    public JFrame frame;
    private Mouse mouse = new Mouse();

    public Window(String title, Scene scene) {

        setBackground(Color.black);
        setSize(500, 500);
        setLocation((1280 - 500) / 2, 0);
        currentScene = scene;
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                if (onClose != null)
                    onClose.act();

                isRunning = false;
            }

            @Override
            public void windowClosed(WindowEvent e) {


            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        frame.setSize(WIDTH, HEIGHT);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        addKeyListener(new Keyboard());
        frame.addMouseMotionListener(mouse);
        frame.addMouseListener(mouse);
        frame.addMouseWheelListener(mouse);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        frame.setResizable(false);

        Thread t = new Thread(this);
        t.start();
        frame.add(this);

        Button b = new Button("test");
        b.setSize(100, 100);
        b.setLocation(0, 0);
        frame.getContentPane().setBackground(new Color(43, 43, 43));
    }

    public void setTitle(String title) {
        frame.setTitle(title);
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

    public void setFrameSize(int width, int height) {
        frame.setSize(width, height);

    }

    public float camX = 0;
    public float camY = 0;
    public double zoom = 1;

    private void render() {
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

        g2d.scale(zoom, zoom);
        g2d.translate(-camX, -camY);

        currentScene.render(g);
        g2d.translate(camX, camY);
        g2d.setTransform(new AffineTransform());
        currentScene.drawUI(g);
        bs.show();
        g.dispose();

    }
}
