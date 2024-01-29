package com.jra.app.UI.components;

import javax.swing.*;
import java.awt.*;

public class PanelButton extends JButton {

    public PanelButton(String text) {
        super(text);
        setBackground(new Color(60, 59, 64));
        setForeground(Color.white);
        setBorderPainted(false);
        setFont(new Font("Arial", Font.PLAIN, 16));
        setFocusPainted(false);
    }
}
