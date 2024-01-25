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

    private int camSpeed = 10;

    @Override
    public void tick() {
        if (Keyboard.W)
            window.camY -= 10;
        else if (Keyboard.S)
            window.camY += 10;

        else if (Keyboard.A)
            window.camX += 10;
        else if (Keyboard.D)
            window.camX -= 10;
    }

    @Override
    public void onReady() {

    }
}
