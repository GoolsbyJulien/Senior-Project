package com.jra.app;

import com.jra.api.core.Scene;
import com.jra.api.render.Window;

import java.awt.*;

public class Main {


    public Main() {


        Scene helloWorld = new Scene();


        Window window = new Window("Hello World", helloWorld);
        window.setSize(800, 800);
        window.setBackgroundColor(Color.blue);
        helloWorld.addGameobject(new Camera(window));
        helloWorld.uiLayer = g -> {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Verdana", 0, 50));

            g.drawString("FPS: " + window.getLastFPS(), 250, 650);
        };

        helloWorld.addGameobject(new World());
    }


    public static void main(String[] args) {

        new Main();
    }

}
