package com.jra.app.UI.views;

import com.jra.api.util.Vector;
import com.jra.app.Main;
import com.jra.app.MapObjects.SelectableObject;
import com.jra.app.UI.StyleGlobals;
import com.jra.app.UI.components.PanelButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BottomPanel extends JPanel {
    public BottomPanel() {
        PanelButton button = new PanelButton("Bottom Panel");

        setLayout(new BorderLayout());
        add(button, BorderLayout.NORTH);
        setLocation(0, 0);
        setPreferredSize(new Dimension(1280, 200));
        setBackground(StyleGlobals.BACKGROUND);

        JPanel pButtons = new JPanel(new FlowLayout());
        pButtons.setBackground(StyleGlobals.BACKGROUND);

        PanelButton addObjectButton = new PanelButton("Add Object");
        PanelButton addRoadButton = new PanelButton("Add Road");

        pButtons.add(addObjectButton);
        pButtons.add(addRoadButton);
        addObjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                try {

                    String name = JOptionPane.showInputDialog("Enter Name");
                    if (name == null)
                        return;
                    SelectableObject temp = new SelectableObject(Main.instance.cam.screenPointToWorldPoint(new Vector(400, 300)));
                    temp.setLabel(name);
                    Main.instance.mapScene.addGameobject(temp);

                } catch (Exception e1) {

                }
            }
        });

        add(pButtons, BorderLayout.CENTER);
    }
}
