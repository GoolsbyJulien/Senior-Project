package com.jra.app;

import com.jra.api.core.GameObject;
import com.jra.api.input.Keyboard;
import com.jra.api.render.Window;

import java.awt.*;

public class Camera extends GameObject {
    private Window window;

    public Camera(Window w) {
        window = w;
    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public void tick() {
        if (Keyboard.W)
            window.camX++;


    }

    @Override
    public void onReady() {

    }
}
