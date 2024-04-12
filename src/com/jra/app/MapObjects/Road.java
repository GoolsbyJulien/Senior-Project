package com.jra.app.MapObjects;

import com.jra.api.core.MapObject;
import com.jra.api.input.Mouse;
import com.jra.api.util.Util;
import com.jra.app.Main;

import java.awt.*;
import java.awt.geom.Line2D;

public class Road extends MapObject {
    MapObject mapObject, mapObject2;
    private boolean showRoad = false;
    private static final float SHOW_POINT = 1.5f;
    public static Road currentObject;
    private Color roadColor = new Color(255, 255, 255, Util.clampedLerp(0, 255, Main.instance.mapRenderer.cameraZoom / 5));
    private static boolean hasRoad = false;

    public Road(MapObject m1, MapObject m2, String rName) {
        mapObject = m1;
        mapObject2 = m2;

        name = rName;
    }

    @Override
    public void render(Graphics g) {
        if (!showRoad)
            return;
        g.setColor(roadColor);
        Graphics2D g2 = (Graphics2D) g;

        Stroke dashed = new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                0, new float[]{9}, 0);
        ((Graphics2D) g).setStroke(dashed);
        //((Graphics2D) g).setStroke(new BasicStroke(5));
        g.drawLine(mapObject.pos.x + 25, mapObject.pos.y + 25, mapObject2.pos.x + 25, mapObject2.pos.y + 25);
    }

    @Override
    public void tick() {
        showRoad = Main.instance.mapRenderer.cameraZoom > SHOW_POINT;

        //Hovering
        int mouseX = (int) ((Mouse.mousePos.x + Main.instance.mapRenderer.cameraPosition.x * Main.instance.mapRenderer.cameraZoom) / Main.instance.mapRenderer.cameraZoom);
        int mouseY = (int) ((Mouse.mousePos.y + Main.instance.mapRenderer.cameraPosition.y * Main.instance.mapRenderer.cameraZoom) / Main.instance.mapRenderer.cameraZoom);
        double distance = Line2D.ptSegDist(mapObject.pos.x + 25,mapObject.pos.y + 25,mapObject2.pos.x + 25,mapObject2.pos.y + 25,mouseX,mouseY);

        if(distance < 3){
            Main.instance.mapRenderer.hoveredObject = this;
            if(Mouse.LEFT_CLICK && currentObject != Main.instance.rightPanel.currentObject && !SelectableObject.isHasSelectedObject()){
                hasRoad = true;
                currentObject = this;
                Main.instance.rightPanel.update(currentObject);
            }
        } else if (Main.instance.mapRenderer.hoveredObject == this) {
            Main.instance.mapRenderer.hoveredObject = null;
        }
        hasRoad = false;
    }

    @Override
    public void onReady() {

    }

    public Color getRoadColor() {
        return roadColor;
    }

    public void setRoadColor(Color roadColor) {
        this.roadColor = roadColor;
    }
    public static boolean isHasRoad() {
        return hasRoad;
    }
}
