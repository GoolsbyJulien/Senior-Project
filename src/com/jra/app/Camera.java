package com.jra.app;

import com.jra.api.core.GameObject;
import com.jra.api.input.Keyboard;
import com.jra.api.input.Mouse;
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

    private float camSpeed = 10;

    @Override
    public void tick() {
        camSpeed = (float) (10 / window.zoom);
        if (Keyboard.W)
            window.camY -= camSpeed;
        else if (Keyboard.S)
            window.camY += camSpeed;

        else if (Keyboard.D)
            window.camX += camSpeed;
        else if (Keyboard.A)
            window.camX -= camSpeed;


        window.zoom = Mouse.wheel;
    }

    @Override
    public void onReady() {

    }
}
