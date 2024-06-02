package com.jra.app.MapObjects;

import com.jra.api.core.MapObject;
import com.jra.api.input.Mouse;
import com.jra.api.util.Serializer;
import com.jra.api.util.Util;
import com.jra.api.util.Vector;
import com.jra.app.Main;
import com.jra.app.UI.StyleGlobals;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

public class MapLabel extends MapObject implements Serializable {
    private boolean followMouse;
    private static boolean hasSelectedObject = false;
    public static MapLabel currentObject;
    private static int selectedBorderThickness = 10;
    private Color color = Util.RandomColor();
    private int width = 50, height = 50;
    private int fontSize = 20;
    private String label = "";
    private String description;
    private Color borderColor;
    public String UUID;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        name = label;

        this.label = label;
    }

    public MapLabel(Vector pos) {
        this.pos = pos;
    }


    public void genID() {
        int id = Util.RandomRange(0, 1000000);


        UUID = Integer.toString(id);
    }

    public void setUUID(String id) {
        UUID = id;
    }

    @Override
    public void render(Graphics g) {
        if(visibility){
            g.setColor(color);
            g.setFont(StyleGlobals.getFont(fontSize));
            g.drawString(label, pos.x + (width - label.length()) / 4, (int) (pos.y + height * 0.5));
        }
    }

    @Override
    public String serialize() {
        String[][] fields = {
                {"X", Integer.toString(pos.x),},
                {"Y", Integer.toString(pos.y),},
                {"Color", Integer.toString(color.getRGB())},
                {"Label", getLabel()},
                {"size", Integer.toString(width)},
                {"Description", description},
                {"UUID", UUID}};

        return Serializer.serialize("SO", fields);
    }


    int frame = 0;


    @Override
    public void tick() {

        Rectangle rect = new Rectangle(pos.x, pos.y, width, height);
        int mouseX = (int) ((Mouse.mousePos.x + Main.instance.mapRenderer.cameraPosition.x * Main.instance.mapRenderer.cameraZoom) / Main.instance.mapRenderer.cameraZoom);
        int mouseY = (int) ((Mouse.mousePos.y + Main.instance.mapRenderer.cameraPosition.y * Main.instance.mapRenderer.cameraZoom) / Main.instance.mapRenderer.cameraZoom);
        if (followMouse) {
            pos.x = mouseX;
            pos.y = mouseY;
            if (!Mouse.LEFT_CLICK) {
                followMouse = false;
                hasSelectedObject = false;
            }
            if (currentObject == this)
                Main.instance.rightPanel.setLocationText(pos.x, pos.y);
        }

        if (Mouse.LEFT_CLICK && !hasSelectedObject && rect.contains(mouseX, mouseY) && !Road.isHasRoad()) {
            if (Mouse.wasDragged()) {
                followMouse = true;
                hasSelectedObject = true;
            }
            if (currentObject != this) {
                currentObject = this;
            }
            if (currentObject != Main.instance.rightPanel.currentObject) {
                Main.instance.rightPanel.update(currentObject);
                Main.instance.rightPanel.setLocationText(pos.x, pos.y);
            }
        }


        if (rect.contains(mouseX, mouseY) && !hasSelectedObject) {
            Main.instance.mapRenderer.hoveredObject = this;
        } else if (Main.instance.mapRenderer.hoveredObject == this) {
            Main.instance.mapRenderer.hoveredObject = null;
        }

    }


    public void setColor(Color c) {
        this.color = c;
        updateBorderColor();
    }


    private void updateBorderColor() {
        if (!(Util.colorBrightness(color) > 245))
            borderColor = Color.white;
        else
            borderColor = Color.black;
    }

    @Override
    public void onReady() {
        updateBorderColor();
        layer = 6;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void changeSize(int size) {
        width = size;
        height = size;
        fontSize = (int) (size * 0.4);
        selectedBorderThickness = size / 5;
    }

    public int getWidth() {
        return width;
    }

    public static boolean isHasSelectedObject() {
        return hasSelectedObject;
    }
}
