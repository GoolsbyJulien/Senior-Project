package com.jra.app.UI.views;

import com.jra.app.MapObjects.SelectableObject;
import com.jra.app.UI.StyleGlobals;
import com.jra.app.UI.components.PanelButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RightPanel extends JPanel {

    SelectableObject currentObject = null;
    JTextField name = new JTextField();


    public  void update(SelectableObject object){
        currentObject =object;
        name.setText(object.getLabel());
    }
    public RightPanel() {
        PanelButton button = new PanelButton("Right Panel");


        setLayout(new BorderLayout());
        add(button, BorderLayout.NORTH);
        setLocation(0, 0);
        setPreferredSize(new Dimension(300, 800));
        name.setFont(StyleGlobals.getFont(10));
        name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelectableObject.currentObject.setLabel(name.getText());
            }
        });
        add(name, BorderLayout.CENTER);
        setBackground(StyleGlobals.BACKGROUND);

    }
}
