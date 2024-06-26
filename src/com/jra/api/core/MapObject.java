package com.jra.api.core;

import com.jra.api.util.Vector;

import java.awt.*;

public abstract class MapObject {

    public String name = "Map Object";
    public Vector pos = new Vector();
    public boolean visibility = true;

    public abstract void render(Graphics g);

    public abstract String serialize();

    public abstract void tick();

    public abstract void onReady();

    public void setPos(Vector pos) {
        this.pos = pos;
    }

    public void setPos(int x, int y) {
        this.pos = new Vector(x, y);
    }
    public void setVisibility(boolean visibility){this.visibility = visibility;}

    @Override
    public String toString() {
        return name + " (Layer: " + layer + ")";
    }

    public int layer = 0;

}
