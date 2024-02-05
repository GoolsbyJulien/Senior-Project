package com.jra.api.managers;

import com.jra.api.core.MapObject;

import java.awt.*;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

public class MapObjectManager {
    public CopyOnWriteArrayList<MapObject> gameObjects = new CopyOnWriteArrayList<>();

    public void add(MapObject e) {

        e.onReady();
        gameObjects.add(e);
        sortLayer();
    }


    public int numberOfObjects() {
        return gameObjects.size();
    }

    public void render(Graphics g) {
        for (MapObject e : gameObjects) {
            e.render(g);
        }

    }

    public void sortLayer() {

        gameObjects.sort(Comparator.comparingInt(o -> o.layer));
    }

    public void dispose() {
        for (MapObject e : gameObjects) {
            gameObjects.remove(e);
        }
    }

    public void remove(MapObject e) {
        gameObjects.remove(e);
    }

    public void tick() {
        for (MapObject e : gameObjects) {
            e.tick();
        }

    }

    public void load() {

    }
}
