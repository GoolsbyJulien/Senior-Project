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

    private final Color color = Util.RandomColor();

    public String getLabel() {
        name = label;
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    private String label = "";


    public SelectableObject(Vector pos) {
        this.pos = pos;
    }

    @Override
    public void render(Graphics g) {
        Rectangle rect = new Rectangle(pos.x, pos.y, 50, 50);

        g.setColor(new Color(255, 255, 255, 200));

        if (currentObject == this)
            g.fillRect(pos.x - 3, pos.y - 3, rect.width + 6, rect.height + 6);
        
        g.setColor(color);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
        g.setFont(StyleGlobals.getFont(20));
        g.drawString(label, pos.x, pos.y - 10);

    }


    int frame = 0;

    @Override


    public void tick() {

        Rectangle rect = new Rectangle(pos.x, pos.y, 50, 50);
        int mouseX = (int) ((Mouse.mousePos.x + Main.instance.mapRenderer.cameraPosition.x * Main.instance.mapRenderer.cameraZoom) / Main.instance.mapRenderer.cameraZoom);
        int mouseY = (int) ((Mouse.mousePos.y + Main.instance.mapRenderer.cameraPosition.y * Main.instance.mapRenderer.cameraZoom) / Main.instance.mapRenderer.cameraZoom);
        if (followMouse) {
            pos.x = mouseX;
            pos.y = mouseY;
            if (!Mouse.LEFT_CLICK) {
                followMouse = false;
                hasSelectedObject = false;
            }
        }

        if (Mouse.LEFT_CLICK && !hasSelectedObject && rect.contains(mouseX, mouseY)) {

            if (Mouse.wasDragged()) {
                followMouse = true;
                hasSelectedObject = true;
            }
            if (currentObject != this) {
                currentObject = this;
                Main.instance.rightPanel.update(currentObject);

            }
        }


    }


    @Override
    public void onReady() {
        layer = 6;
    }
}
