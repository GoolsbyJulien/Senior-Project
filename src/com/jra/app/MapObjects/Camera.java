package com.jra.app.MapObjects;

import com.jra.api.core.MapObject;
import com.jra.api.input.Keyboard;
import com.jra.api.input.Mouse;
import com.jra.api.render.MapRenderer;
import com.jra.api.util.Action;
import com.jra.api.util.Vector;
import com.jra.app.Main;

import java.awt.*;

public class Camera extends MapObject {
    private MapRenderer window;

    public Camera(MapRenderer w) {
        window = w;
    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public String serialize() {
        return "-1";
    }

    private float camSpeed = 10;
    boolean isMovingTowards = false;
    Vector moveTowards = new Vector(0, 0);


    public Vector screenPointToWorldPoint(Vector point) {


        return new Vector((int) ((point.x + Main.instance.mapRenderer.cameraPosition.x * Main.instance.mapRenderer.cameraZoom) / Main.instance.mapRenderer.cameraZoom), (int) ((point.y + Main.instance.mapRenderer.cameraPosition.y * Main.instance.mapRenderer.cameraZoom) / Main.instance.mapRenderer.cameraZoom));
    }

    @Override
    public void tick() {

        camSpeed = (float) (10 / window.cameraZoom);
        if (Keyboard.W || Keyboard.UP)
            pos.y -= camSpeed;
        else if (Keyboard.S || Keyboard.DOWN)
            pos.y += camSpeed;

        else if (Keyboard.D || Keyboard.RIGHT)
            pos.x += camSpeed;
        else if (Keyboard.A || Keyboard.LEFT)
            pos.x -= camSpeed;



        if (Mouse.RIGHT_CLICK) {
            int mouseX = (int) ((Mouse.mousePos.x + Main.instance.mapRenderer.cameraPosition.x * Main.instance.mapRenderer.cameraZoom) / Main.instance.mapRenderer.cameraZoom);
            int mouseY = (int) ((Mouse.mousePos.y + Main.instance.mapRenderer.cameraPosition.y * Main.instance.mapRenderer.cameraZoom) / Main.instance.mapRenderer.cameraZoom);

            if (!isMovingTowards) {
                moveTowards.x = mouseX;
                moveTowards.y = mouseY;
                isMovingTowards = true;
            } else {
                int deltaX = mouseX - moveTowards.x;
                int deltaY = mouseY - moveTowards.y;
                pos.x -= deltaX;
                pos.y -= deltaY;
            }
        } else {
            isMovingTowards = false;
        }

        //Calculate Boundaries
        if (Main.instance.currentProject.getProjectType() == 1) { //Custom image bounds

        } else { //Perlin bounds
            if (pos.x < -700 * window.cameraZoom)
                pos.x = (int) (-700 * window.cameraZoom);
            if (pos.x > 700 * window.cameraZoom)
                pos.x = (int) (700 * window.cameraZoom);
            if (pos.y < -1280 * window.cameraZoom)
                pos.y = (int) (-1280 * window.cameraZoom);
            if (pos.y > 1280 * window.cameraZoom)
                pos.y = (int) (1280 * window.cameraZoom);
        }

        boolean zoomChanged = false;
        if(window.cameraZoom != Mouse.wheel)
            zoomChanged = true;

        window.cameraZoom = Mouse.wheel;
        window.cameraPosition = pos;

        if(zoomChanged){
            Main.instance.updateComponents(Main.instance.mapScene);
            zoomChanged = false;
        }
    }


    @Override
    public String toString() {
        return "Camera{" +
                "Zoom='" + Math.round(window.cameraZoom * 100.0) / 100.0 + '\'' +

                '}';
    }

    @Override
    public void onReady() {
        Keyboard.rAction = new Action() {
            @Override
            public void act() {
                Main.instance.bottomPanel.addRoad();

            }
        };

        Keyboard.lAction = new Action() {
            @Override
            public void act() {
                Main.instance.bottomPanel.addLocation();

            }
        };

        name = "Camera";
    }
}
