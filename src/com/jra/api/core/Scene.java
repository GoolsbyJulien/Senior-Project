package com.jra.api.core;

import com.jra.api.managers.MapObjectManager;
import com.jra.api.util.Action;

import java.awt.*;

public class Scene {
    public MapObjectManager goManager;

    public UILayer uiLayer;
    public Action loadScene;

    public Scene() {
        goManager = new MapObjectManager();
    }

    public void addGameobject(MapObject e) {

        goManager.add(e);
    }

    public void removeGameObject(MapObject e) {
        goManager.remove(e);
    }

    public void update() {
        goManager.tick();
    }


    public void load() {
        goManager.load();
        if (loadScene != null)
            loadScene.act();
    }

    public void drawUI(Graphics g) {
        if (uiLayer != null)
            uiLayer.draw(g);
    }

    public void render(Graphics g) {
        goManager.render(g);

    }

    public void dispose() {
        goManager.dispose();
    }


}
