package com.jra.app.MapObjects;

import com.jra.api.core.MapObject;
import com.jra.api.input.Mouse;
import com.jra.api.util.Util;
import com.jra.api.util.Vector;
import com.jra.app.Main;
import com.jra.app.UI.StyleGlobals;

import java.awt.*;

public class SelectableObject extends MapObject {
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
        Rectangle rect = new Rectangle(pos.x, pos.y, width, height);

        g.setColor(borderColor);

        if (currentObject == this)
            g.fillRect(pos.x - selectedBorderThickness / 2, pos.y - selectedBorderThickness / 2, rect.width + selectedBorderThickness, rect.height + selectedBorderThickness);

        g.setColor(color);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
        g.setFont(StyleGlobals.getFont(fontSize));
        g.drawString(label, pos.x + (width - label.length()) / 2, (int) (pos.y - (height * 0.2)));
    }

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

        if (Mouse.LEFT_CLICK && !hasSelectedObject && rect.contains(mouseX, mouseY)) {
            if (Mouse.wasDragged()) {
                followMouse = true;
                hasSelectedObject = true;
            }
            if (currentObject != this) {
                currentObject = this;
                Main.instance.rightPanel.update(currentObject);
                Main.instance.rightPanel.setLocationText(pos.x, pos.y);

            }
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
}
