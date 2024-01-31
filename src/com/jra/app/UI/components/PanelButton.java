package com.jra.app.UI.components;

import com.jra.app.UI.StyleGlobals;

import javax.swing.*;
import java.awt.*;

public class PanelButton extends JButton {

    public PanelButton(String text) {
        super(text);
        setBackground(new Color(60, 59, 64));
        setForeground(Color.white);
        setBorderPainted(false);

       
        setFont(StyleGlobals.getFont(15));
        setFocusPainted(false);
    }
}
