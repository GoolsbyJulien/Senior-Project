package com.jra.app;

public class Project {
    private int perlinSeed;
    private String projectName;

    public Project(){
        perlinSeed = 11;
        projectName = "Test";
    }

    public void setPerlinSeed(int perlinSeed){
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
