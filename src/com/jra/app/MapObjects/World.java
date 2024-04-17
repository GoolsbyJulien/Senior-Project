package com.jra.app.MapObjects;

import com.jra.api.core.MapObject;
import com.jra.api.input.Keyboard;
import com.jra.api.util.PerlinNoise;
import com.jra.api.util.Profiler;
import com.jra.api.util.Util;
import com.jra.api.util.Vector;
import com.jra.app.Main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class World extends MapObject {

    private final int WORLD_SIZE = 1600;
    private int mapView = 0;
    float[][] noise = new float[WORLD_SIZE][WORLD_SIZE];
    int[][] biomeMap = new int[WORLD_SIZE][WORLD_SIZE];
    int biomeDensity = 200;
    private PerlinNoise p;

    float scale = 1;
    private BufferedImage bi;

    @Override
    public void render(Graphics g) {
        if (bi == null)
            return;


        g.drawImage(bi, 0, 0, null);


    }


    @Override
    public void tick() {

        if (Keyboard.E)
            generateMap();
    }


    public void setMapView(int mapView) {
        this.mapView = mapView;
        refreshNoiseMap();
    }


    Profiler profiler = new Profiler();

    public void generateBiomeMaps(int seed) {

        profiler.start();
        for (int i = 0; i < 200; i++) {

            Vector biomePoint = new Vector(Util.RandomRange(0, WORLD_SIZE), Util.RandomRange(0, WORLD_SIZE));
            int biomeID = Util.RandomRange(1, 2);

            for (int x = -199; x < Util.RandomRange(20, 200); x++) {
                for (int y = -100; y < 100; y++) {
                    int xPoint = biomePoint.x + x;
                    int yPoint = biomePoint.y + y;


                    if (xPoint < WORLD_SIZE && xPoint > 0 && yPoint < WORLD_SIZE && yPoint > 0)
                        biomeMap[xPoint][yPoint] = biomeID;
                }
            }
        }
        profiler.end();
        System.out.println(profiler.times[0]);
    }

    public void refreshNoiseMap() {
        noise = PerlinNoise.fallOff(WORLD_SIZE, WORLD_SIZE);
        for (int x = 0; x < WORLD_SIZE; x++) {
            for (int y = 0; y < WORLD_SIZE; y++) {
                noise[x][y] += p.GetNoise(x, y);
                Color color;
                if (mapView == 0) // color map
                    color = biome(biomeMap[x][y], noise[x][y]);

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

        Profiler worldProfiler = new Profiler();
        worldProfiler.start();
        bi = new BufferedImage(WORLD_SIZE, WORLD_SIZE, BufferedImage.TYPE_INT_ARGB);

        generateBiomeMaps(seed);

        System.out.println("New World with seed " + seed);
        p = new PerlinNoise(seed);
        p.SetFractalType(PerlinNoise.FractalType.FBm);
        p.SetFrequency(0.004f);
        p.SetDomainWarpAmp(20);
        p.SetFractalOctaves(7);
        p.SetNoiseType(PerlinNoise.NoiseType.Perlin);
        Main.instance.currentProject.setPerlinSeed(seed);
        refreshNoiseMap();
        worldProfiler.end();
        System.out.println(worldProfiler.times[0]);

    }


    @Override
    public void onReady() {
        name = "World";
        generateMap();
    }

    Color biome(int biomeValue, float e) {

        if (e < 0.1) return Util.lerp(new Color(7, 0, 161), Color.blue, e * 3);

        else if (e < 0.13) return Util.lerp(Color.blue, new Color(94, 167, 255), e);
        if (biomeValue == 0)
            return Biomes.Normal(e);
        if (biomeValue == 1)
            return Biomes.getDesert(e);
        if (biomeValue == 2)
            return Biomes.getWinter(e);

            // these thresholds will need tuning to match your generator


        else if (e < 0.17) return Util.lerp(new Color(94, 167, 255), new Color(255, 229, 114), e);

        else if (e < 0.4) return new Color(50, 127, 53);

        else if (e < 0.45) return new Color(40, 86, 40);


        return Color.white;
    }
}

class Biomes {

    public static Color getDesert(float height) {

        return Color.yellow;
    }

    public static Color getWinter(float height) {

        return Color.WHITE;
    }

    public static Color Normal(float height) {

        return Color.red;
    }


}



