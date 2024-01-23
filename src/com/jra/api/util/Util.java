package com.jra.api.util;

import java.awt.*;

public class Util {
    public static int RandomRange(int min, int max) {


        max += 1;

        return (int) ((Math.random() * (max - min)) + min);
    }

    public static Color RandomColor() {
        return new Color(RandomRange(0, 255), RandomRange(0, 255), RandomRange(0, 255));
    }

    public static long secondsFromNow(int seconds) {
        return System.currentTimeMillis() + seconds * 1000;
    }
}
