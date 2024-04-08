package com.jra.app.MapObjects;

import com.jra.api.core.MapObject;
import com.jra.api.input.Keyboard;
import com.jra.api.util.PerlinNoise;
import com.jra.api.util.Serializer;
import com.jra.api.util.Util;
import com.jra.app.Main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class World extends MapObject {

    private final int WORLD_SIZE = 1600;
    private int mapView = 0;
    float[][] noise = new float[WORLD_SIZE][WORLD_SIZE];

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

        String[][] fields = {{"Seed", "0"}};

        return Serializer.serialize("World", fields);
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

    public void refreshNoiseMap() {
        noise = PerlinNoise.fallOff(WORLD_SIZE, WORLD_SIZE);

        for (int x = 0; x < WORLD_SIZE; x++) {
            for (int y = 0; y < WORLD_SIZE; y++) {
                noise[x][y] += p.GetNoise(x, y);
                Color color;
                if (mapView == 0) // color map
                    color = biome(noise[x][y]);

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
        p.SetDomainWarpAmp(20);
        p.SetFractalOctaves(7);
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
        if (e < 0.1) return Util.lerp(new Color(7, 0, 161), Color.blue, e * 3);

        else if (e < 0.13) return Util.lerp(Color.blue, new Color(94, 167, 255), e);

        else if (e < 0.17) return Util.lerp(new Color(94, 167, 255), new Color(255, 229, 114), e);

        else if (e < 0.4) return new Color(50, 127, 53);

        else if (e < 0.45) return new Color(40, 86, 40);


        return Color.white;
    }


}

