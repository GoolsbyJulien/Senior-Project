package com.jra.app.MapObjects;

import com.jra.api.core.MapObject;
import com.jra.api.input.Keyboard;
import com.jra.api.util.PerlinNoise;
import com.jra.api.util.Profiler;
import com.jra.api.util.Util;
import com.jra.api.util.Vector;
import com.jra.app.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

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
    public String serialize() {
        return null;
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


        for (int x = 100; x < WORLD_SIZE - WORLD_SIZE / 15; x++) {
            int c = r.nextInt(10);
            for (int y = 0; y < 1300; y++) {


                float e = noise[x][y] * 100;

                //  temp = 10.0 * (e * e) + 100.0 + (10.0 - 10);

                if (y < WORLD_SIZE / 5 + 100 + c + r.nextInt(100) - 200 + (-1 * (Math.sin(0.0100 * x * x)) * 10) - 10) {
                    biomeMap[x][y] = 2;
                    continue;

                }


//                if (temp < -0.4f)
//                    biomeMap[x][y] = 2;
//
//                else if (temp > 0.5 && humidity > 0.4)
//                    biomeMap[x][y] = 1;
//                else if (humidity < 0.4) {
//                    biomeMap[x][y] = 0;
//                } else {
//                    biomeMap[x][y] = 3;
//                }

            }
        }


        for (int i = 0; i < 12; i++) {

            Vector biomePoint = new Vector(Util.RandomRange(0, WORLD_SIZE), Util.RandomRange(500, WORLD_SIZE));
            int biomeID = Util.RandomRange(0, 1);

            for (int x = 0; x < 100; x++) {
                int c = r.nextInt(10);


                for (int y = 0; y < 100; y++) {


                    int xPoint = biomePoint.x + x;
                    int yPoint = biomePoint.y + y;

                    if (!(xPoint < WORLD_SIZE && xPoint > 0 && yPoint < WORLD_SIZE && yPoint > 0))
                        continue;


                    if (noise[xPoint][yPoint] < 0.13 || noise[xPoint][yPoint] >= 0.46)
                        continue;
                    biomeMap[xPoint][yPoint] = 1;
                }
            }
        }
        profiler.end();
        System.out.println("Biome Gen Time :" + profiler.times[0] + "ms");

    }

//    private boolean pointInMap(int x, int y) {
//
//
//        return x < WORLD_SIZE && x > 0 && y < WORLD_SIZE && y > 0;
//    }

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


    public void saveToImg() {

        File outputfile = new File("image.png");

        try {
            ImageIO.write(bi, "png", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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


        if (height < 0.5)
            return Util.RandomRange(0, 1) == 0 ? new Color(225, 180, 73) : new Color(225, 185, 80).darker();

        return Color.white;
    }

    public static Color getWinter(float height) {
        if (height < 0.4) return new Color(253, 253, 253);

        else if (height < 7) return new Color(16, 130, 210);


        return Color.white;
    }

    public static Color Normal(float height) {


        if (height < 0.17)
            return new Color(255, 233, 185);
        if (height < 0.4) return new Color(50, 127, 53);

        else if (height < 0.45) return new Color(40, 86, 40);

        return Color.white;
    }


}



