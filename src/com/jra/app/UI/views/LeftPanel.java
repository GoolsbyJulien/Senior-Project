package com.jra.app.UI.views;

import com.jra.app.UI.StyleGlobals;
import com.jra.app.UI.components.Hierarchy;
import com.jra.app.UI.components.PanelButton;

import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JPanel {

    public Hierarchy hierarchy = new Hierarchy();

    public LeftPanel() {
        PanelButton button = new PanelButton("Left Panel");
        ScrollPane scrollPane = new ScrollPane();

        setLayout(new BorderLayout());
        add(button, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        scrollPane.add(hierarchy);
        setLocation(0, 0);
        setPreferredSize(new Dimension(300, 800));
        setBackground(StyleGlobals.BACKGROUND);

    }


}
