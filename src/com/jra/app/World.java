package com.jra.app;

import com.jra.api.core.GameObject;
import com.jra.api.util.Util;

import java.awt.*;

public class World extends GameObject {
    float[][] noise = new float[800][800];

    @Override
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
// Generate Perlin noise
        // Render the noise on the canvas
        for (int x = 0; x < 800; x++) {
            for (int y = 0; y < 800; y++) {
                // System.out.println(noise[Mouse.mousePos.x][Mouse.mousePos.y]);

                float color = Math.abs(noise[x][y]);
                g.setColor(biome(clamp(0, 1, noise[x][y])));
                g2.setStroke(new BasicStroke(1));
                g.drawLine(x, y, x, y);
            }
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public void onReady() {
        System.out.println("r");
        PerlinNoise p = new PerlinNoise(Util.RandomRange(0, 1000));
        p.SetFractalType(PerlinNoise.FractalType.FBm);
        p.SetFrequency(0.004f);
        p.SetDomainWarpAmp(2);
        p.SetFractalOctaves(5);
        p.SetFractalLacunarity(2);
        p.SetNoiseType(PerlinNoise.NoiseType.Perlin);


        for (int x = 0; x < 800; x++) {
            for (int y = 0; y < 800; y++) {
                noise[x][y] = clamp(0, 1, p.GetNoise(x, y));
//                       noise[x][y] = (float)Math.random();

            }
        }
    }

    Color biome(float e) {
        // these thresholds will need tuning to match your generator
        if (e < 0.2 / 4) return Color.blue;
        else if (e < 0.4 / 4) return new Color(94, 167, 255);
        else if (e < 0.45 / 2) return new Color(246, 222, 127);
        else if (e < 0.55 / 2) return new Color(123, 255, 123);
        else if (e < 0.6 / 2) return new Color(0, 190, 0);

        else if (e < 0.7 / 2) return new Color(56, 50, 29);
        else if (e < 0.8 / 2) return new Color(61, 46, 23);

        return Color.white;
    }


    public float clamp(float min, float max, float value) {
        if (value > max)
            value = max;
        if (value < min)
            value = min;
        return value;
    }
}

