package com.jra.app;

import com.jra.api.core.GameObject;
import com.jra.api.input.Keyboard;
import com.jra.api.util.Util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class World extends GameObject {
    float[][] noise = new float[800][800];
    BufferedImage bi;


    @Override
    public void render(Graphics g) {
        if (bi == null)
            return;

        Graphics2D g2 = (Graphics2D) g;
// Generate Perlin noise
        // Render the noise on the canvas
        for (int x = 0; x < 800; x++) {
            for (int y = 0; y < 800; y++) {

                // System.out.println(noise[Mouse.mousePos.x][Mouse.mousePos.y]);
                if (!(noise[x][y] > 0.04f))
                    continue;
//                float color = Math.abs(noise[x][y]);
//                g.setColor(biome(Util.clamp(0, 1, noise[x][y])));
//                g2.setStroke(new BasicStroke(1));
//                g.setColor(Util.RandomColor());
////                g.drawLine(x, y, x, y);
//
                Color color = biome(Util.clamp(0, 1, noise[x][y]));
                bi.setRGB(x, y, color.getRGB());

            }

        }
        g.drawImage(bi, 0, 0, null);
//        g.dispose();

    }

    @Override
    public void tick() {


        if (Keyboard.E)
            gen();
    }

    private void gen() {

        bi = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        int seed = Util.RandomRange(0, 1999000);
        System.out.println("New World with seed " + seed);
        PerlinNoise p = new PerlinNoise(seed);
        p.SetFractalType(PerlinNoise.FractalType.FBm);
        p.SetFrequency(0.004f);
        p.SetDomainWarpAmp(2);
        p.SetFractalOctaves(5);
        p.SetFractalLacunarity(2);
        p.SetNoiseType(PerlinNoise.NoiseType.Perlin);


        for (int x = 0; x < 800; x++) {
            for (int y = 0; y < 800; y++) {
                noise[x][y] = Util.clamp(0, 1, p.GetNoise(x, y));

            }
        }
        noise = PerlinNoise.fallOff(noise);
    }

    @Override
    public void onReady() {
        gen();
        layer = 1;
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

