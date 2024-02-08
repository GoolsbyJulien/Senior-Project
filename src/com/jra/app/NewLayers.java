package com.jra.app;

import com.jra.api.core.MapObject;

import java.awt.*;

public class NewLayers extends MapObject {

    @Override
    public void render(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(pos.x, pos.y, 50, 50);

    }

    @Override
    public void tick() {

    }

    @Override
    public void onReady() {
        layer = -6;
    }
}
