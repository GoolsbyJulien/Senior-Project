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

public class SelectableObject extends MapObject implements Serializable {
    private boolean followMouse;
    private static boolean hasSelectedObject = false;
    public static SelectableObject currentObject;
    private static int selectedBorderThickness = 10;
    private Color color = Util.RandomColor();
    private int width = 50, height = 50;
    private int fontSize = 20;
    private String label = "";
    private String description;
    private Color borderColor;
    private LocationType locationType = LocationType.SETTLEMENT;
    private int iconType = 0; //Rectangle by default

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        name = label;

        this.label = label;
    }

    public SelectableObject(Vector pos) {
        this.pos = pos;
    }

    @Override
    public void render(Graphics g) {
        Rectangle rect;
        Ellipse2D.Double circle;
        Polygon triangle;

        if(visibility){
            if (iconType == 1) {
            //Set shapes
            circle = new Ellipse2D.Double(pos.x, pos.y, width, height);

            //Shape info
            g.setColor(borderColor);
            if (currentObject == this)
                g.fillOval(pos.x - selectedBorderThickness / 2, pos.y - selectedBorderThickness / 2,
                        (int) (circle.width + selectedBorderThickness), (int) (circle.height + selectedBorderThickness));

            g.setColor(color);
            g.fillOval((int)circle.x, (int)circle.y, (int)circle.width, (int)circle.height);
        } else if (iconType == 2) {
            //Inverse Triangle using polygons
            //1 is top right
            //2 is top left
            //3 is bottom
            triangle = new Polygon(new int[] {pos.x + width, pos.x, pos.x + (width / 2)},
                    new int[] {pos.y, pos.y, pos.y + width}, 3);

            g.setColor(borderColor);
            if(currentObject == this)
                g.fillPolygon(new int[]{pos.x + width + selectedBorderThickness, pos.x - selectedBorderThickness, pos.x + (width / 2)},
                        new int[]{pos.y - (selectedBorderThickness/2), pos.y - (selectedBorderThickness/2), pos.y + width + selectedBorderThickness}, 3);

            g.setColor(color);
            g.fillPolygon(triangle);
        } else if (iconType == 3) {
            //Triangle using polygons
            //1 is bottom right
            //2 is bottom left
            //3 is top
            triangle = new Polygon(new int[] {pos.x + width, pos.x, pos.x + (width / 2)},
                    new int[] {pos.y + width, pos.y + width, pos.y}, 3);

            g.setColor(borderColor);
            if(currentObject == this)
                g.fillPolygon(new int[]{pos.x + width + selectedBorderThickness, pos.x - selectedBorderThickness, pos.x + (width / 2)},
                        new int[]{pos.y + width + (selectedBorderThickness/2), pos.y + width + (selectedBorderThickness/2), pos.y - selectedBorderThickness}, 3);

            g.setColor(color);
            g.fillPolygon(triangle);
        } else{
            rect = new Rectangle(pos.x, pos.y, width, height);
            g.setColor(borderColor);
            if (currentObject == this)
                g.fillRect(pos.x - selectedBorderThickness / 2, pos.y - selectedBorderThickness / 2,
                        rect.width + selectedBorderThickness, rect.height + selectedBorderThickness);

            g.setColor(color);
            g.fillRect(rect.x, rect.y, rect.width, rect.height);
        }
        g.setFont(StyleGlobals.getFont(fontSize));
        g.drawString(label, pos.x + (width - label.length()) / 4, (int) (pos.y - (height * 0.2)));
        }
        else{

        }
    }

    @Override
    public String serialize() {
        String[][] fields = {{"X", Integer.toString(pos.x),}, {"Y", Integer.toString(pos.y),}, {"Color", Integer.toString(color.getRGB())}, {"Label", getLabel()}, {"size", Integer.toString(width)}, {"Description", description}, {"type", Integer.toString(locationType.ordinal())}};

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
            if(currentObject != Main.instance.rightPanel.currentObject){
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

    public void toggleLabel(boolean check) {
        if (!check)
            label = "";
        else
            label = name;
    }

    public int getWidth() {
        return width;
    }
    public static boolean isHasSelectedObject() {
        return hasSelectedObject;
    }

    public void changeIcon(int iconType){
        this.iconType = iconType;
    }
}
