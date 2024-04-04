package com.jra.app.UI.views;

import com.jra.app.Main;
import com.jra.app.MapObjects.SelectableObject;
import com.jra.app.UI.StyleGlobals;
import com.jra.app.UI.components.PanelButton;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;

public class RightPanel extends JPanel {

    SelectableObject currentObject = null;
    public JPanel panel = new JPanel(new GridBagLayout());
    JTextField name = new JTextField(10);
    JTextArea description = new JTextArea(10, 2);

    JTextField locationX = new JTextField(4);
    JTextField locationY = new JTextField(4);



    public void update(SelectableObject object) {

        panel.setVisible(true);
        currentObject = object;
        name.setText(object.getLabel());
        description.setText(SelectableObject.currentObject.getDescription());
    }

    public RightPanel() {

        panel.setVisible(false);
        PanelButton button = new PanelButton("Inspector");
        setLayout(new BorderLayout());

        add(button, BorderLayout.NORTH);
        setLocation(0, 0);
        setPreferredSize(new Dimension(300, 800));

        //Grid bag constraints
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 15;
        c.ipady = 10;

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setForeground(Color.WHITE);
        panel.add(nameLabel, c);
        name.setBackground(StyleGlobals.ACCENT);
        name.setForeground(Color.white);
        name.setCaretColor(Color.white);
        name.setBorder(null);
        name.setFont(StyleGlobals.getFont(15));
        name.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                SelectableObject.currentObject.setLabel(name.getText());
                Main.instance.updateComponents(Main.instance.mapScene);
            }
        });
        c.gridx = 1;
        c.ipadx = 150;
        c.ipady = 10;
        panel.add(name, c);

        JLabel descriptionLabel = new JLabel("Description");
        descriptionLabel.setForeground(Color.WHITE);
        c.gridx = 0;
        c.gridy = 1;
        c.ipadx = 15;
        c.ipady = 80;
        panel.add(descriptionLabel, c);
        //Description area
        JScrollPane descriptionPane = new JScrollPane(description);
        description.setFont(StyleGlobals.getFont(15));
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setBackground(StyleGlobals.ACCENT);
        description.setForeground(Color.white);
        description.setCaretColor(Color.white);
        description.setBorder(null);
        description.setFont(StyleGlobals.getFont(15));
        description.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                    SelectableObject.currentObject.setDescription(description.getText());
                }
        });

        description.setBackground(StyleGlobals.ACCENT);
        descriptionPane.setBorder(null);
        c.gridx = 1;
        c.ipadx = 150;
        c.ipady = 2;
        panel.add(descriptionPane, c);
        System.out.println(descriptionPane.getBackground());
        JLabel location = new JLabel("Location");

        location.setVerticalAlignment(JLabel.TOP);
        location.setForeground(Color.WHITE);
        c.gridx = 0;
        c.gridy = 2;
        c.ipadx = 15;
        c.ipady = 10;
        panel.add(location, c);



        Panel locationPanel = new Panel(new FlowLayout());
        JLabel xLabel = new JLabel("X");
        xLabel.setForeground(Color.WHITE);
        JLabel yLabel = new JLabel("Y");
        yLabel.setForeground(Color.white);
        locationPanel.add(xLabel);
        locationPanel.add(locationX);
        locationPanel.add(yLabel);
        locationPanel.add(locationY);
        c.gridx = 1;
        c.gridy=2;
        c.anchor = GridBagConstraints.WEST;
        c.ipadx = 150;
        c.ipady = 10;
        panel.add(locationPanel, c);



        panel.setBackground(StyleGlobals.BACKGROUND);
        setBackground(StyleGlobals.BACKGROUND);

        JPanel newPanel = new JPanel(new BorderLayout());
        newPanel.setBackground(StyleGlobals.BACKGROUND);
        add(newPanel, BorderLayout.CENTER);

        newPanel.add(panel, BorderLayout.NORTH);

        JPanel Buttons = new JPanel(new FlowLayout());

        PanelButton field = new PanelButton("Type Field");

        Buttons.add(field);

        PanelButton icon = new PanelButton("Change Icon");

        Buttons.add(icon);
        PanelButton d1 = new PanelButton("Delete");
        c.gridx = 0;
        c.gridy = 4;
        c.ipadx = 5;
        c.ipady = 2;
        c.gridwidth =2;
        Buttons.add(d1);
        Buttons.setBackground(StyleGlobals.BACKGROUND);
        panel.add(Buttons, c);

        JPanel SecondRowButtons = new JPanel(new FlowLayout());
        PanelButton color = new PanelButton("Change Color");
        c.gridx = 0;
        c.gridy = 5;
        c.ipadx = 5;
        c.ipady = 2;
        c.gridwidth =2;
        SecondRowButtons.add(color);
        SecondRowButtons.setBackground(StyleGlobals.BACKGROUND);
        panel.add(SecondRowButtons, c);


        JLabel Size = new JLabel("Size");
        Size.setForeground(Color.WHITE);
        c.gridx = 0;
        c.gridy= 6;
        c.ipadx = 150;
        c.ipady = 10;
        panel.add(Size, c);

        JSlider sizeSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);
        c.gridx = 1;
        panel.add(sizeSlider, c);
    }
}
