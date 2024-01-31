package com.jra.app.UI.views;

import com.jra.app.UI.components.Hierarchy;
import com.jra.app.UI.components.PanelButton;

import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JPanel {

    public Hierarchy hierarchy = new Hierarchy();

    public LeftPanel() {
        PanelButton button = new PanelButton("Left Panel");


        setLayout(new BorderLayout());
        add(button, BorderLayout.NORTH);
        add(hierarchy, BorderLayout.CENTER);
        setLocation(0, 0);
        setPreferredSize(new Dimension(300, 800));
        setBackground(new Color(26, 26, 25));

    }


}
