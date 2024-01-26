package com.jra.app;

import com.jra.api.core.Scene;
import com.jra.api.input.Keyboard;
import com.jra.api.render.Window;

import java.awt.*;

public class Main {

    public static Window window;
    private boolean showFPS = false;

    public Main() {


        Scene helloWorld = new Scene();


        window = new Window("Hello World", helloWorld);
        window.setFrameSize(1280, 700);
        window.setBackgroundColor(Color.blue);
        helloWorld.addGameobject(new Camera(window));
        helloWorld.uiLayer = g -> {
            if (Keyboard.F3)
                showFPS = !showFPS;
            g.setColor(Color.WHITE);
            g.setFont(new Font("Verdana", 0, 50));
            if (showFPS)
                g.drawString("FPS: " + window.getLastFPS(), 250, 650);
        };

        helloWorld.addGameobject(new World());


    }


    public static void main(String[] args) {

        new Main();
    }

}
