package com.jra.app.MapObjects;

import com.jra.api.core.MapObject;
import com.jra.api.input.Mouse;
import com.jra.api.util.Serializer;
import com.jra.api.util.Util;
import com.jra.app.Main;

import java.awt.*;
import java.awt.geom.Line2D;

public class Road extends MapObject {
    SelectableObject mapObject, mapObject2;

    String UUID1, UUID2;
    private boolean showRoad = false;
    private static final float SHOW_POINT = 1.5f;
    public static Road currentObject;
    private Color roadColor = new Color(255, 255, 255, Util.clampedLerp(0, 255, Main.instance.mapRenderer.cameraZoom / 5));

    //Stroke
    private int roadWidth = 5;
    private Stroke roadStroke = new BasicStroke(roadWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
            0, new float[]{9}, 0); //Dashed by default
    private String strokeType = "Dashed";

    private static boolean hasRoad = false;

    public Road(SelectableObject m1, SelectableObject m2, String rName) {
        mapObject = m1;
        mapObject2 = m2;

        name = rName;
    }

    public Road(String m1, String m2, String rName) {
        UUID1 = m1;
        UUID2 = m2;

        name = rName;
    }

    @Override
    public void render(Graphics g) {

        if (mapObject == null || mapObject2 == null)
            return;
        if (!showRoad)
            return;
        g.setColor(roadColor);
        Graphics2D g2 = (Graphics2D) g;
        ((Graphics2D) g).setStroke(roadStroke);
        g.drawLine(mapObject.pos.x + mapObject.getWidth() / 2, mapObject.pos.y + mapObject.getWidth() / 2,
                mapObject2.pos.x + mapObject2.getWidth() / 2, mapObject2.pos.y + mapObject2.getWidth() / 2);
    }

    @Override
    public String serialize() {
        String[][] fields = {{"o1", mapObject.UUID}, {"o2", mapObject2.UUID}, {"Color", Integer.toString(roadColor.getRGB())}, {"Stroke", strokeType}, {"size", Integer.toString(roadWidth)}, {"name", name}};

        return Serializer.serialize("ROAD", fields);
    }

    @Override
    public void tick() {

        if (mapObject == null || mapObject2 == null)
            return;
        showRoad = Main.instance.mapRenderer.cameraZoom > SHOW_POINT;

        //Hovering
        int mouseX = (int) ((Mouse.mousePos.x + Main.instance.mapRenderer.cameraPosition.x * Main.instance.mapRenderer.cameraZoom) / Main.instance.mapRenderer.cameraZoom);
        int mouseY = (int) ((Mouse.mousePos.y + Main.instance.mapRenderer.cameraPosition.y * Main.instance.mapRenderer.cameraZoom) / Main.instance.mapRenderer.cameraZoom);
        double distance = Line2D.ptSegDist(mapObject.pos.x + mapObject.getWidth() / 2, mapObject.pos.y + mapObject.getWidth() / 2,
                mapObject2.pos.x + mapObject2.getWidth() / 2, mapObject2.pos.y + mapObject2.getWidth() / 2, mouseX, mouseY);

        if (distance < 3) {
            Main.instance.mapRenderer.hoveredObject = this;
            if (Mouse.LEFT_CLICK && !SelectableObject.isHasSelectedObject()) {
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

    public Stroke getRoadStroke() {
        return roadStroke;
    }

    public void setRoadStroke(Stroke roadStroke, String type) {
        this.roadStroke = roadStroke;
        strokeType = type;
    }

    public int getRoadWidth() {
        return roadWidth;
    }

    public void setRoadWidth(int roadWidth) {
        this.roadWidth = roadWidth;
        if (strokeType == "Dashed")
            roadStroke = new BasicStroke(roadWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                    0, new float[]{9}, 0);
        else
            roadStroke = new BasicStroke(roadWidth);
    }

    public void loadFromUUIDS() {
        mapObject = Main.instance.getSelectableObjectsFromUUID(UUID1);
        mapObject2 = Main.instance.getSelectableObjectsFromUUID(UUID2);

    }
}
