package com.jra.app.MapObjects;

import com.jra.api.core.MapObject;
import com.jra.app.Main;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageWorld extends MapObject {
    private BufferedImage bi = Main.instance.currentProject.getImage();

    @Override
    public void render(Graphics g) {
        if (bi == null)
            return;

        g.drawImage(bi, 0, 0, null);
    }

    @Override
    public void tick() {
    }

    @Override
    public void onReady() {
        name = "World";
    }
}

