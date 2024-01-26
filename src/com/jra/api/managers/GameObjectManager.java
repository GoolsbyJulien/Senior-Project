package com.jra.api.managers;

import com.jra.api.core.GameObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameObjectManager {
    private CopyOnWriteArrayList<GameObject> gameObjects = new CopyOnWriteArrayList<>();
    private ArrayList<GameObject> gameObjectsToBeRemoved = new ArrayList<>();

    public void add(GameObject e) {

        e.onReady();
        gameObjects.add(e);
    }


    public int numberOfObjects() {
        return gameObjects.size();
    }

    public void render(Graphics g) {
        for (GameObject e : gameObjects) {
            e.render(g);

        }
    }

    public void dispose() {
        for (GameObject e : gameObjects) {
            gameObjects.remove(e);
        }
    }

    public void remove(GameObject e) {
        gameObjects.remove(e);
    }

    public void tick() {
        for (GameObject e : gameObjects) {
            e.tick();
        }

    }

    public void load() {

    }
}
