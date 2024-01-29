package com.jra.app.UI.views;

import com.jra.app.UI.components.PanelButton;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {
    public RightPanel() {
        PanelButton button = new PanelButton("Right Panel");

        setLayout(new BorderLayout());
        add(button, BorderLayout.NORTH);
        setLocation(0, 0);
        setPreferredSize(new Dimension(300, 800));
        setBackground(new Color(43, 44, 48));

    }
}
