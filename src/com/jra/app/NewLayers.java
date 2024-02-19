package com.jra.app;

import com.jra.api.core.MapObject;
import com.jra.api.input.Mouse;
import com.jra.api.util.Util;
import com.jra.api.util.Vector;

import java.awt.*;

public class NewLayers extends MapObject {
    private boolean followMouse;
    private final Color color = Util.RandomColor();

    public  NewLayers(Vector pos){
        this.pos = pos;
    }

    @Override
    public void render(Graphics g) {
        Rectangle rect = new Rectangle(pos.x, pos.y, 50, 50);

        g.setColor(color);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);

    }

    @Override
    public void tick() {
        Rectangle rect = new Rectangle (pos.x, pos.y, 50, 50);
            int mouseX = (int)((Mouse.mousePos.x +Main.instance.mapRenderer.cameraPosition.x * Main.instance.mapRenderer.cameraZoom) / Main.instance.mapRenderer.cameraZoom);
            int mouseY = (int)((Mouse.mousePos.y +Main.instance.mapRenderer.cameraPosition.y * Main.instance.mapRenderer.cameraZoom) / Main.instance.mapRenderer.cameraZoom);
            if (followMouse) {
                pos.x = mouseX;
                pos.y = mouseY;
                if (!Mouse.LEFT_CLICK)
                    followMouse = false;
            }

            if (Mouse.LEFT_CLICK && rect.contains(mouseX, mouseY) && Mouse.wasDragged()) {
                followMouse = true;
            }

        }



    @Override
    public void onReady() {
        layer = 6;
    }
}
