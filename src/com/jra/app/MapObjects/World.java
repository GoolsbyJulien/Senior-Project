package com.jra.app.MapObjects;

import com.jra.api.core.MapObject;
import com.jra.api.input.Keyboard;
import com.jra.api.util.PerlinNoise;
import com.jra.api.util.Util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class World extends MapObject {

    int worldSize = 800;

    float scale = 0.5f;
    float[][] noise = new float[worldSize][worldSize];
    BufferedImage bi;


    @Override
    public void render(Graphics g) {
        if (bi == null)
            return;

        Graphics2D g2 = (Graphics2D) g;
// Generate Perlin noise
        // Render the noise on the canvas

        g.drawImage(bi, 0, 0, null);
//        g.dispose();


    }

    public static int lerp(int a, int b, double percent) {
        return (int) ((1 - percent) * a + percent * b);
    }

    /**
     * Calculates the linear interpolation between a and b with the given
     * percent
     *
     * @param a
     * @param b
     * @param percent
     * @return
     */
    public static Color lerp(Color a, Color b, double percent) {
        int red = lerp(a.getRed(), b.getRed(), percent);
        int blue = lerp(a.getBlue(), b.getBlue(), percent);
        int green = lerp(a.getGreen(), b.getGreen(), percent);
        int alpha = lerp(a.getAlpha(), b.getAlpha(), percent);
        return new Color(red, green, blue, alpha);
    }

    @Override
    public void tick() {

        if (Keyboard.F) {
            h++;
            refreshNoiseMap();
        }
        if (Keyboard.E)
            gen();
    }

    int h = 0;


    public void refreshNoiseMap() {
        for (int x = 0; x < worldSize; x++) {
            for (int y = 0; y < worldSize; y++) {
                noise[x][y] = Util.clamp(0, 1, p.GetNoise(x, y));
                //noise[x][y] = Util.clamp(0, 1, (float) Math.sin(x));
                Color color;
                if (h == 0)
                    color = biome(Util.clamp(0, 1, noise[x][y]));

                else {
                    h = -1;
                    color = lerp(Color.black, Color.white, noise[x][y]);
                }
                bi.setRGB(x, y, color.getRGB());
            }
        }
    }

    PerlinNoise p;

    private void gen() {

        bi = new BufferedImage(worldSize, worldSize, BufferedImage.TYPE_INT_ARGB);
        int seed = Util.RandomRange(0, 1999000);

        System.out.println("New World with seed " + seed);
        p = new PerlinNoise(seed);
        p.SetFractalType(PerlinNoise.FractalType.FBm);
        p.SetFrequency(0.004f);
        p.SetDomainWarpAmp(2);
        p.SetFractalOctaves(5);
        p.SetFractalLacunarity(2);
        p.SetNoiseType(PerlinNoise.NoiseType.Perlin);

        refreshNoiseMap();


//        for (int x = 0; x < worldSize; x++) {
//            for (int y = 0; y < worldSize; y++) {
//
//                // System.out.println(noise[Mouse.mousePos.x][Mouse.mousePos.y]);
//
////                float color = Math.abs(noise[x][y]);
////                g.setColor(biome(Util.clamp(0, 1, noise[x][y])));
////                g2.setStroke(new BasicStroke(1));
////                g.setColor(Util.RandomColor());
//////                g.drawLine(x, y, x, y);
////
//
//
//            }
//
//        }
//        noise = PerlinNoise.fallOff(noise);
    }

    @Override
    public void onReady() {
        name = "World";
        gen();

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

