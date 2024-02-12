package com.jra.app;

import com.jra.api.core.MapObject;
import com.jra.api.input.Mouse;

import java.awt.*;

public class NewLayers extends MapObject {

    @Override
    public void render(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(pos.x, pos.y, 50, 50);

    }

    @Override
    public void tick() {
            int mouseX = (int)((Mouse.mousePos.x +Main.instance.mapRenderer.cameraPosition.x) / Main.instance.mapRenderer.cameraZoom);
            int mouseY = (int)((Mouse.mousePos.y +Main.instance.mapRenderer.cameraPosition.y) / Main.instance.mapRenderer.cameraZoom);


            if (Mouse.LEFT_CLICK) {
                pos.x = mouseX;
                pos.y = mouseY;
            }
    }


    @Override
    public void onReady() {
        layer = 6;
    }
}
