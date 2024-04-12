package com.jra.app;

import com.jra.app.MapObjects.ImageWorld;
import com.jra.app.MapObjects.SelectableObject;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Project {
    private int perlinSeed = 0;
    private String projectName;
    private int projectType;
    private int newProjectType = 0;
    private BufferedImage projectImage;
    private String projectDescription;

    public static final String DEFAULT_NAME = "Untitled";

    public String filePath = null;

    public Project() {
        projectName = DEFAULT_NAME;
    }

    public void setPerlinSeed(int perlinSeed) {
        this.perlinSeed = perlinSeed;
    }

    public void setPerlinSeedLoad(int perlinSeed) {
        //Remove Image from scene
        Main.instance.mapScene.goManager.gameObjects.forEach((n) -> {
            if (n.getClass() == ImageWorld.class) {
                Main.instance.mapScene.removeGameObject(n);
                Main.instance.mapScene.addGameobject(Main.instance.world);

            }
            if (n instanceof SelectableObject)
                Main.instance.mapScene.removeGameObject(n);

        });


        //Check if seeds are different
        if (perlinSeed != this.perlinSeed) {
            Main.instance.world.generateMap(perlinSeed);
        }

        this.perlinSeed = perlinSeed;
    }

    public int getPerlinSeed() {
        return perlinSeed;
    }

    public void setProjectName(String projectName) {


        if (projectName == null || projectName.trim().isEmpty())
            projectName = DEFAULT_NAME;
        this.projectName = projectName;
        Main.instance.updateTitle();
    }

    public String getProjectName() {
        return projectName;
    }

    public int getProjectType() {
        return projectType;
    }

    public void setProjectType(int type) {
        //0 is perlin
        //1 is image
        projectType = type;
    }

    public int getNewProjectType() {
        return newProjectType;
    }

    public void setNewProjectType(int type) {
        newProjectType = type;
    }

    public BufferedImage getImage() {
        return projectImage;
    }

    public void setImage(BufferedImage path) throws IOException {
        projectImage = path;
    }

    public void setProjectDescription(String description) {
        projectDescription = description;
    }

    public String getProjectDescription() {
        return projectDescription;
    }
}
