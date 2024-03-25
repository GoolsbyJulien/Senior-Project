package com.jra.app.UI;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class StyleGlobals {


    public static final Color BACKGROUND = new Color(35, 35, 35);
    public static final Color ACCENT = new Color(45, 45, 45);
    private static Font customFont;

    public static Font getFont(int size) {


        if (customFont == null) {
            try {
                //create the font to use. Specify the size!
                customFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/Roboto-Regular.ttf"));
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                //register the font
                ge.registerFont(customFont);
            } catch (IOException | FontFormatException e) {
                e.printStackTrace();
            }
        }
        return customFont.deriveFont(0, size);
//use the font
    }
}
