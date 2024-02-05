package com.jra.app.MapObjects;

import com.jra.api.core.MapObject;
import com.jra.api.input.Keyboard;
import com.jra.api.input.Mouse;
import com.jra.api.render.MapRenderer;

import java.awt.*;

public class Camera extends MapObject {
    private MapRenderer window;

    public Camera(MapRenderer w) {
        window = w;
    }

    @Override
    public void render(Graphics g) {

    }

    private float camSpeed = 10;

    @Override
    public void tick() {

        camSpeed = (float) (10 / window.cameraZoom);
        if (Keyboard.W)
            pos.y -= camSpeed;
        else if (Keyboard.S)
            pos.y += camSpeed;

        else if (Keyboard.D)
            pos.x += camSpeed;
        else if (Keyboard.A)
            pos.x -= camSpeed;


        window.cameraZoom = Mouse.wheel;
        window.cameraPosition = pos;
    }


    @Override
    public String toString() {
        return "Camera{" +
                "Zoom='" + Math.round(window.cameraZoom * 100.0) / 100.0 + '\'' +
                "pos=" + pos +

                '}';
    }

    @Override
    public void onReady() {
        name = "Camera";
    }
}
