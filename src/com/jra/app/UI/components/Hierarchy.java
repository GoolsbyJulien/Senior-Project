package com.jra.app.UI.components;

import com.jra.api.core.MapObject;
import com.jra.app.UI.StyleGlobals;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Hierarchy extends JPanel {
    private JLabel jLabel = new JLabel();

    public Hierarchy() {
        jLabel.setForeground(Color.white);
        jLabel.setFont(StyleGlobals.getFont(20));
        jLabel.setText("Seed: ");
        setBackground(new Color(0, 0, 0));

        add(jLabel);
    }//

    public void generateHierarchy(CopyOnWriteArrayList<MapObject> mapObjects) {

        String output = "";

        for (MapObject o : mapObjects) {
            output = output + o + "<br>";
        }
        jLabel.setText("<html>" + output + "</html>");
    }

}
