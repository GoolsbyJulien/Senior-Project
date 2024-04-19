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
import java.util.Random;

public class World extends MapObject {

    private final int WORLD_SIZE = 1600;
    private int mapView = 0;
    float[][] noise = new float[WORLD_SIZE][WORLD_SIZE];
    float[][] tempV = new float[WORLD_SIZE][WORLD_SIZE];

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


    public void generateBiomeMaps(int seed) {
        Profiler profiler = new Profiler();

        profiler.start();

        Random r = new Random(seed);


        PerlinNoise hMap = new PerlinNoise(seed);
        PerlinNoise tMap = new PerlinNoise(seed);
        tMap.SetNoiseType(PerlinNoise.NoiseType.OpenSimplex2);
        tMap.SetFrequency(0.0008f);
        hMap.SetFrequency(0.007f);
        tMap.SetFractalType(PerlinNoise.FractalType.FBm);
        //  tMap.SetDomainWarpAmp(20);
        tMap.SetFractalOctaves(7);
        // hMap.SetDomainWarpAmp(20);
        hMap.SetFractalOctaves(5);

        hMap.SetFractalType(PerlinNoise.FractalType.FBm);


        Vector biomePoint = new Vector(r.nextInt(WORLD_SIZE), r.nextInt(WORLD_SIZE));
        int biomeID = r.nextInt(3);
        for (int x = 0; x < WORLD_SIZE - WORLD_SIZE / 15; x++) {
            float c = Util.RandomRange(400, 4550);
            for (int y = 15; y < WORLD_SIZE - WORLD_SIZE / 15; y++) {
                double temp = tMap.GetNoise(x, y);


                float humidity = hMap.GetNoise(x, y);
                float e = noise[x][y] * 100;

                //  temp = 10.0 * (e * e) + 100.0 + (10.0 - 10);

//                if (y < WORLD_SIZE / 4 + (-1 * (Math.sin(0.0100 * x * x)) * 10) - 10)

                if (temp < -0.4f && humidity < 0.7)
                    biomeMap[x][y] = 2;

                else if (temp > 0.3 && humidity > 0.4)
                    biomeMap[x][y] = 1;
                else if (humidity < 0.4) {
                    biomeMap[x][y] = 0;
                } else {
                    biomeMap[x][y] = 3;
                }

            }
        }
        profiler.end();
        System.out.println("Biome Gen Time :" + profiler.times[0] + "ms");

    }

    private boolean pointInMap(int x, int y) {

        return x < WORLD_SIZE && x > 0 && y < WORLD_SIZE && y > 0;
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
        worldProfiler.start();

        refreshNoiseMap();
        worldProfiler.end();
        System.out.println("World Gen: " + worldProfiler.times[0] + "ms");

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


        if (height < 0.4)
            return Util.RandomRange(0, 1) == 0 ? new Color(225, 180, 73) : new Color(225, 185, 80).darker();

        else if (height < 0.45) return new Color(91, 71, 1);
        return new Color(213, 116, 8);
    }

    public static Color getWinter(float height) {
        if (height < 0.4) return new Color(253, 253, 253);

        else if (height < 7) return new Color(16, 130, 210);


        return Color.white;
    }

    public static Color Normal(float height) {


        if (height < 0.17)
            return new Color(225, 180, 73);
        if (height < 0.4) return new Color(50, 127, 53);

        else if (height < 0.45) return new Color(40, 86, 40);

        return Color.white;
    }


}



