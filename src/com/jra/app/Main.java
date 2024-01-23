package com.jra.app;

import com.jra.api.core.Scene;
import com.jra.api.render.Window;

import java.awt.*;

public class Main {


    public Main() {


        Scene helloWorld = new Scene();
        helloWorld.uiLayer = g -> {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Verdana", 0, 50));

            g.drawString("Hello World", 250, 350);
        };


        Window window = new Window("Hello World", helloWorld);
        window.setSize(800, 800);
        window.setBackgroundColor(Color.blue);
        helloWorld.addGameobject(new Camera(window));
        helloWorld.addGameobject(new World());
    }


    public static void main(String[] args) {

        new Main();
    }

}
