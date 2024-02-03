package com.jra.app.MapObjects;

import com.jra.api.core.MapObject;
import com.jra.api.input.Keyboard;
import com.jra.api.util.PerlinNoise;
import com.jra.api.util.Util;
import com.jra.app.Main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class World extends MapObject {

    private final int WORLD_SIZE = 800;
    private int mapView = 0;
    float[][] noise = new float[WORLD_SIZE][WORLD_SIZE];

    private PerlinNoise p;

    float scale = 0.5f;
    private BufferedImage bi;

    @Override
    public void render(Graphics g) {
        if (bi == null)
            return;


        g.drawImage(bi, 0, 0, null);
        g.dispose();


    }


    @Override
    public void tick() {

        if (Keyboard.F) {
            mapView++;
            refreshNoiseMap();
        }
        if (Keyboard.E)
            generateMap();
    }


    public void refreshNoiseMap() {
        for (int x = 0; x < WORLD_SIZE; x++) {
            for (int y = 0; y < WORLD_SIZE; y++) {
                noise[x][y] = Util.clamp(0, 1, p.GetNoise(x, y));
                Color color;
                if (mapView == 0) // color map
                    color = biome(Util.clamp(0, 1, noise[x][y]));

                else { // noise map
                    mapView = -1;
                    color = Util.lerp(Color.black, Color.white, noise[x][y]);
                }
                bi.setRGB(x, y, color.getRGB());
            }
        }
    }


    public void generateMap() {

        generateMap(Util.RandomRange(0, 1000000));
    }

    public void generateMap(int seed) {
        bi = new BufferedImage(WORLD_SIZE, WORLD_SIZE, BufferedImage.TYPE_INT_ARGB);

        System.out.println("New World with seed " + seed);
        p = new PerlinNoise(seed);
        p.SetFractalType(PerlinNoise.FractalType.FBm);
        p.SetFrequency(0.004f);
        p.SetDomainWarpAmp(2);
        p.SetFractalOctaves(5);
        p.SetFractalLacunarity(2);
        p.SetNoiseType(PerlinNoise.NoiseType.Perlin);
        Main.instance.currentProject.setPerlinSeed(seed);
        refreshNoiseMap();
    }


    @Override
    public void onReady() {
        name = "World";
        generateMap();

    }

    Color biome(float e) {
        // these thresholds will need tuning to match your generator
        if (e < 0.2 / 1000) return Color.blue;
        else if (e < 0.4 / 4) return new Color(94, 167, 255);
        else if (e < 0.45 / 2) return new Color(246, 222, 127);
        else if (e < 0.55 / 2) return new Color(123, 255, 123);
        else if (e < 0.6 / 2) return new Color(0, 190, 0);

        else if (e < 0.7 / 2) return new Color(56, 50, 29);
        else if (e < 0.8 / 2) return new Color(61, 46, 23);

        return Color.white;
    }
}

