package com.jra.api.core;

import com.jra.api.util.Vector;

import java.awt.*;

public abstract class MapObject {

    public String name = "Map Object";
    public Vector pos = new Vector();

    public abstract void render(Graphics g);

    public abstract void tick();

    public abstract void onReady();

    public void setPos(Vector pos) {
        this.pos = pos;
    }

    public void setPos(int x, int y) {
        this.pos = new Vector(x, y);
    }

    @Override
    public String toString() {
        return name + " (" + layer + ")" + " {" +
                "pos=" + pos +
                '}';
    }

    public int layer = 0;

}
