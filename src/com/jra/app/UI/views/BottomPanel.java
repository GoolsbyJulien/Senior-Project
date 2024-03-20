package com.jra.app.UI.views;

import com.jra.app.UI.StyleGlobals;
import com.jra.app.UI.components.PanelButton;

import javax.swing.*;
import java.awt.*;

public class BottomPanel extends JPanel {
    public BottomPanel() {
        PanelButton button = new PanelButton("Bottom Panel");

        setLayout(new BorderLayout());
        add(button, BorderLayout.NORTH);
        setLocation(0, 0);
        setPreferredSize(new Dimension(1280, 200));
        setBackground(StyleGlobals.BACKGROUND);

    }
}
