package com.jra.app;

import com.jra.api.core.GameObject;

import java.awt.*;

public class NewLayers extends GameObject {

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
        layer = 6;
    }
}
