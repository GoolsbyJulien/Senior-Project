package com.jra.app.MapObjects;

import com.jra.app.Main;

import java.awt.*;

public class Biomes {
    //Biome Colors
    public static Color deepWater = new Color(7, 0, 161);
    public static Color shallowWater = new Color(94, 167, 255);
    public static Color plains = new Color(50, 127, 53);
    public static Color coast = new Color(255, 233, 185);
    public static Color desert = new Color(255, 233, 185);
    public static Color hills = new Color(112, 122, 112);
    public static Color mountains = Color.WHITE;
    public static Color forest = new Color(40, 86, 40);
    public static Color tropicalForest = new Color(138, 224, 97);
    public static Color taiga = new Color(57, 48, 7);
    public static Color tundra = new Color(106, 161, 203);

    public static Color getDesert(float height) {
        if (height < 0.35)
            return desert;
        else if (height < 0.5)
            return hills;

        return mountains;
    }

    public static Color getWinter(float height) {
        if (height < 0.17)
            return coast;

        if (height < 0.4) return tundra;

        else if (height < 7) return hills;

        return mountains;
    }

    public static Color getForest(float height) {
        if (height < 0.17)
            return coast;

        if (height < 0.45) return forest;

        else if (height < 7) return hills;

        return mountains;
    }

    public static Color getTropicalForest(float height) {
        if (height < 0.17)
            return coast;

        if (height < 0.45) return tropicalForest;

        else if (height < 7) return hills;

        return mountains;
    }

    public static Color getTaiga(float height) {
        if (height < 0.17)
            return coast;

        if (height < 0.5) return taiga;

        else if (height < 7) return hills;

        return mountains;
    }

    public static Color Normal(float height) {
        if (height < 0.17)
            return coast;

        if (height < 0.4) return plains;

        else if (height < 0.5) return hills;

        return mountains;
    }

    public static void defaultColors(){
        deepWater = new Color(7, 0, 161);
        shallowWater = new Color(94, 167, 255);
        plains = new Color(50, 127, 53);
        coast = new Color(255, 233, 185);
        desert = new Color(255, 233, 185);
        hills = new Color(112, 122, 112);
        mountains = Color.WHITE;
        forest = new Color(40, 86, 40);
        tropicalForest = new Color(138, 224, 97);
        taiga = new Color(57, 48, 7);
        tundra = new Color(106, 161, 203);

        //Set setting backgrounds
        Main.instance.updateBackground(deepWater);
        Main.instance.menu.settings.deepWaterButton.setBackground(deepWater);
        Main.instance.menu.settings.shallowWaterButton.setBackground(shallowWater);
        Main.instance.menu.settings.plainsButton.setBackground(plains);
        Main.instance.menu.settings.coastButton.setBackground(coast);
        Main.instance.menu.settings.desertButton.setBackground(desert);
        Main.instance.menu.settings.hillsButton.setBackground(hills);
        Main.instance.menu.settings.mountainsButton.setBackground(mountains);
        Main.instance.menu.settings.forestButton.setBackground(forest);
        Main.instance.menu.settings.tropicalForestButton.setBackground(tropicalForest);
        Main.instance.menu.settings.taigaButton.setBackground(taiga);
        Main.instance.menu.settings.tundraButton.setBackground(tundra);

        //Refresh map
        Main.instance.world.refreshNoiseMap();
    }
}