package com.jra.app.UI.views;

import com.jra.api.core.MapObject;
import com.jra.api.input.Mouse;
import com.jra.api.util.Vector;
import com.jra.app.Main;
import com.jra.app.MapObjects.Road;
import com.jra.app.MapObjects.SelectableObject;
import com.jra.app.UI.StyleGlobals;
import com.jra.app.UI.components.PanelButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BottomPanel extends JPanel {
    private boolean isCreatingLine = false;
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
        PanelButton addRiverButton = new PanelButton("Add River (X)");
        PanelButton addLabelButton = new PanelButton("Add Label (X)");
        PanelButton addPolygonButton = new PanelButton("Draw Polygon (X)");

        pButtons.add(addObjectButton);
        pButtons.add(addRoadButton);
        pButtons.add(addRiverButton);
        pButtons.add(addLabelButton);
        pButtons.add(addPolygonButton);
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

        addRoadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isCreatingLine){
                    //Objects
                    ArrayList<MapObject> objects = new ArrayList<>();

                    //Create UI
                    JFrame frame = new JFrame("");
                    frame.setSize(250,210);
                    frame.setLocationRelativeTo(Main.instance.bottomPanel);
                    frame.setAlwaysOnTop(true);
                    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    panel.setPreferredSize(new Dimension(250,210));
                    panel.add(new Label("Select a location, then click a button"));
                    panel.add(new Label("below. Each must be a different location"));

                    JButton object1 = new JButton("Select location 1");
                    JButton object2 = new JButton("Select location 2");
                    JButton createRoad = new JButton("Create Road");

                    panel.add(object1);
                    panel.add(object2);
                    panel.add(createRoad);
                    frame.add(panel);
                    frame.setVisible(true);

                    object1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(SelectableObject.currentObject != null){
                                if(objects.size() >= 2){
                                    if(objects.get(0) != null){
                                        objects.set(0, SelectableObject.currentObject);
                                    }
                                }
                                else {
                                    objects.add(SelectableObject.currentObject);
                                }
                                object1.setText(SelectableObject.currentObject.getLabel());
                            }else{
                                JOptionPane.showMessageDialog(null, "Please select a location!");
                            }
                        }
                    });

                    object2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(SelectableObject.currentObject != null){
                                if(objects.size() >= 2){
                                    if(objects.get(1) != null){
                                        objects.set(1, SelectableObject.currentObject);
                                    }
                                }
                                else {
                                    objects.add(SelectableObject.currentObject);
                                }
                                object2.setText(SelectableObject.currentObject.getLabel());
                            }else{
                                JOptionPane.showMessageDialog(null, "Please select a location!");
                            }
                        }
                    });

                    createRoad.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (objects.size() >= 2) {
                                if(objects.get(0) != objects.get(1)){
                                    Road temp = new Road(objects.get(0),objects.get(1), (objects.get(0).name + "  -  " + objects.get(1).name
                                            + " road"));
                                    Main.instance.mapScene.addGameobject(temp);
                                    Main.instance.updateComponents(Main.instance.mapScene);

                                    //Reset dialogue
                                    object1.setText("Select location 1");
                                    object2.setText("Select location 2");
                                    objects.clear();
                                    frame.setVisible(false);
                                }else{
                                    JOptionPane.showMessageDialog(null, "Please select different locations!");
                                }
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Please select locations!");
                            }
                        }
                    });
                }
            }
        });

        addRiverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isCreatingLine){
                    //Create UI
                    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    panel.setSize(250,50);
                    panel.setPreferredSize(new Dimension(250,50));
                    panel.add(new Label("Click two points to create a river between"));
                    int result = JOptionPane.showConfirmDialog(Main.instance.frame, panel, "Add River",
                            JOptionPane.OK_CANCEL_OPTION);

                    //Create river
                    if (result == JOptionPane.OK_OPTION) {

                    }
                }
            }
        });

        add(pButtons, BorderLayout.CENTER);
    }
}
