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
        PanelButton button = new PanelButton("Tools");

        setLayout(new BorderLayout());
        add(button, BorderLayout.NORTH);
        setLocation(0, 0);
        setPreferredSize(new Dimension(1280, 200));
        setBackground(StyleGlobals.BACKGROUND);

        JPanel pButtons = new JPanel(new FlowLayout());
        pButtons.setBackground(StyleGlobals.BACKGROUND);

        PanelButton addObjectButton = new PanelButton("Add Location");
        PanelButton addRoadButton = new PanelButton("Add Road");

        pButtons.add(addObjectButton);
        pButtons.add(addRoadButton);
        addObjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //UI
                    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    panel.setSize(250,250);
                    panel.setPreferredSize(new Dimension(250,200));
                    JTextField nameField = new JTextField(15);
                    JTextArea descriptionField = new JTextArea(8, 20);
                    descriptionField.setLineWrap(true);
                    descriptionField.setWrapStyleWord(true);

                    JScrollPane textArea = new JScrollPane(descriptionField);

                    panel.add(new Label("Name"));
                    panel.add(nameField);
                    panel.add(new Label("Description"));
                    panel.add(textArea);

                    int result = JOptionPane.showConfirmDialog(Main.instance.frame, panel, "Add Location",
                            JOptionPane.OK_CANCEL_OPTION);

                    if (nameField.getText() == null)
                        return;

                    if (result == JOptionPane.OK_OPTION) {
                        SelectableObject temp = new SelectableObject(Main.instance.cam.screenPointToWorldPoint(new Vector(400, 300)));
                        temp.setLabel(nameField.getText());
                        temp.setDescription(descriptionField.getText());
                        Main.instance.mapScene.addGameobject(temp);
                        Main.instance.updateComponents(Main.instance.mapScene);
                    }
                } catch (Exception e1) {

                }
            }
        });

        add(pButtons, BorderLayout.CENTER);
    }
}
