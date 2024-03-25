package com.jra.app;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Project {
    private int perlinSeed = 0;
    private String projectName;
    private int projectType;
    private int newProjectType = 0;
    private BufferedImage projectImage;


    public Project() {
        projectName = "Untitled";
    }

    public void setPerlinSeed(int perlinSeed) {
        this.perlinSeed = perlinSeed;
    }

    public void setPerlinSeedLoad(int perlinSeed) {
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

        this.projectName = projectName;
        Main.instance.updateTitle();
    }

    public String getProjectName() {
        return projectName;
    }

    public int getProjectType(){
        return projectType;
    }

    public void setProjectType(int type){
        //0 is perlin
        //1 is image
        projectType = type;
    }

    public int getNewProjectType(){
        return newProjectType;
    }

    public void setNewProjectType(int type){
        newProjectType = type;
    }

    public BufferedImage getImage(){
        return projectImage;
    }

    public void setImage(BufferedImage path) throws IOException {
        projectImage = path;
    }
}
