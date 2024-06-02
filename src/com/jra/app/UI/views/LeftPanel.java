package com.jra.app.UI.views;

import com.jra.app.UI.StyleGlobals;
import com.jra.app.UI.components.Hierarchy;
import com.jra.app.UI.components.PanelButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LeftPanel extends JPanel {

    public Hierarchy hierarchy = new Hierarchy();

    public LeftPanel() {
        PanelButton button = new PanelButton("Hierarchy");
        JScrollPane scrollPane = new JScrollPane(hierarchy);
        scrollPane.setBorder(null);
        scrollPane.getHorizontalScrollBar().setBackground(StyleGlobals.BACKGROUND);
        scrollPane.getVerticalScrollBar().setBackground(StyleGlobals.BACKGROUND);
        scrollPane.getVerticalScrollBar().setUnitIncrement(13);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        setLocation(0, 0);
        setPreferredSize(new Dimension(300, 800));
        setBackground(StyleGlobals.BACKGROUND);

    }


}
