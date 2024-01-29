package com.jra.api.managers;

import com.jra.api.core.MapObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class MapObjectManager {
    private CopyOnWriteArrayList<MapObject> gameObjects = new CopyOnWriteArrayList<>();
    private ArrayList<MapObject> gameObjectsToBeRemoved = new ArrayList<>();

    public void add(MapObject e) {

        e.onReady();
        gameObjects.add(e);
    }


    public int numberOfObjects() {
        return gameObjects.size();
    }

    public void render(Graphics g) {
        for (MapObject e : gameObjects) {
            e.render(g);

        }
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
