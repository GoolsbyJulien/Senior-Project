package com.jra.api.managers;

import com.jra.api.core.GameObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameObjectManager {
    private CopyOnWriteArrayList<GameObject> gameObjects = new CopyOnWriteArrayList<>();
    private ArrayList<GameObject> gameObjectsToBeRemoved = new ArrayList<>();

    public void add(GameObject e) {


        gameObjects.add(e);

        sortLayer();
    }

    public void sortLayer() {

        gameObjects.sort(Comparator.comparingInt(o -> o.layer));
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
        for (GameObject e : gameObjects) {
            e.onReady();
        }
    }
}
