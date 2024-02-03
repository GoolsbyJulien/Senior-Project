package com.jra.app;

import com.jra.app.MapObjects.World;

public class Project {
    private int perlinSeed = 0;
    private String projectName;

    public Project(){
        projectName = "Untitled";
    }

    public void setPerlinSeed(int perlinSeed){
        this.perlinSeed = perlinSeed;
    }
    public void setPerlinSeedLoad(int perlinSeed){
        //Check if seeds are different
        if(perlinSeed != this.perlinSeed){
            Main.world.gen(perlinSeed);
        }

        this.perlinSeed = perlinSeed;
    }
    public int getPerlinSeed(){
        return perlinSeed;
    }

    public void setProjectName(String projectName){
        this.projectName = projectName;
    }

    public String getProjectName(){
        return projectName;
    }
}
