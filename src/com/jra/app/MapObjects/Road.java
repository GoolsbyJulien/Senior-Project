package com.jra.app.MapObjects;

import com.jra.api.core.MapObject;
import com.jra.api.util.Util;
import com.jra.app.Main;

import java.awt.*;

public class Road extends MapObject {
    MapObject mapObject, mapObject2;
    private boolean showRoad = false;

    private static final float SHOW_POINT = 1.5f;

    public Road(MapObject m1, MapObject m2) {
        mapObject = m1;
        mapObject2 = m2;

        name = "road";
    }

    @Override
    public void render(Graphics g) {
        if (!showRoad)
            return;
        g.setColor(new Color(255, 255, 255, Util.clampedLerp(0, 255, Main.instance.mapRenderer.cameraZoom / 5)));
        Graphics2D g2 = (Graphics2D) g;
        ((Graphics2D) g).setStroke(new BasicStroke(5));
        g.drawLine(mapObject.pos.x + 25, mapObject.pos.y + 25, mapObject2.pos.x + 25, mapObject2.pos.y + 25);
    }

    @Override
    public void tick() {
        showRoad = Main.instance.mapRenderer.cameraZoom > SHOW_POINT;
    }

    @Override
    public void onReady() {

    }
}
