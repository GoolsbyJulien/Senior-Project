package com.jra.app.UI.views;

import com.jra.app.Main;
import com.jra.app.MapObjects.LocationType;
import com.jra.app.MapObjects.SelectableObject;
import com.jra.app.UI.StyleGlobals;
import com.jra.app.UI.components.PanelButton;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class RightPanel extends JPanel {

    SelectableObject currentObject = null;
    public JPanel inspectorPanel = new JPanel(new GridBagLayout());
    private JTextField name = new JTextField(10);
    private JTextArea description = new JTextArea(10, 9);
    private JComboBox<LocationType> type = new JComboBox<>(LocationType.values());
    private JTextField locationX = new JTextField(4);
    private JTextField locationY = new JTextField(4);
    private JCheckBox showLabel = new JCheckBox("Show Label");


    public void update(SelectableObject object) {
        inspectorPanel.setVisible(true);
        currentObject = object;
        name.setText(object.getLabel());
        description.setText(SelectableObject.currentObject.getDescription());
        type.setSelectedItem(SelectableObject.currentObject.getLocationType());
    }

    public RightPanel() {


        inspectorPanel.setVisible(false);
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
        c.weighty = 1;

        /**
         * Name
         */
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setForeground(Color.WHITE);
        inspectorPanel.add(nameLabel, c);
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
        inspectorPanel.add(name, c);

        /**
         * Type
         */
        JLabel typeLabel = new JLabel("Type");
        typeLabel.setForeground(Color.WHITE);
        c.gridx = 0;
        c.gridy = 1;
        c.ipadx = 15;
        inspectorPanel.add(typeLabel, c);
        c.gridx = 1;
        c.ipadx = 50;
        c.ipady = 5;
        type.setBackground(StyleGlobals.BACKGROUND);
        type.setFont(StyleGlobals.getFont(13));
        type.setForeground(Color.WHITE);
        inspectorPanel.add(type, c);

        /**
         * Description
         */
        JLabel descriptionLabel = new JLabel("Description");
        descriptionLabel.setForeground(Color.WHITE);
        c.gridx = 0;
        c.gridy = 2;
        c.ipadx = 15;
        c.ipady = 80;
        inspectorPanel.add(descriptionLabel, c);
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

        /**
         * Object location
         */
        description.setBackground(StyleGlobals.ACCENT);
        descriptionPane.setBorder(null);
        c.gridx = 1;
        c.ipadx = 120;
        c.ipady = 50;
        inspectorPanel.add(descriptionPane, c);
        JLabel location = new JLabel("Location");

        location.setVerticalAlignment(JLabel.TOP);
        location.setForeground(Color.WHITE);
        c.gridx = 0;
        c.gridy = 3;
        c.ipadx = 15;
        c.ipady = 10;
        inspectorPanel.add(location, c);

        Panel locationPanel = new Panel(new FlowLayout());
        JLabel xLabel = new JLabel("X");
        xLabel.setForeground(Color.WHITE);
        JLabel yLabel = new JLabel("Y");
        yLabel.setForeground(Color.white);
        locationX.setBackground(StyleGlobals.BACKGROUND);
        locationY.setBackground(StyleGlobals.BACKGROUND);
        locationX.setForeground(Color.WHITE);
        locationY.setForeground(Color.WHITE);
        locationX.setEditable(false);
        locationY.setEditable(false);
        locationPanel.add(xLabel);
        locationPanel.add(locationX);
        locationPanel.add(yLabel);
        locationPanel.add(locationY);
        c.gridx = 1;
        c.gridy = 3;
        c.anchor = GridBagConstraints.WEST;
        c.ipadx = 150;
        c.ipady = 10;
        inspectorPanel.add(locationPanel, c);

        /**
         * Button Panel
         */
        JPanel Buttons = new JPanel(new FlowLayout());

        PanelButton delete = new PanelButton("Delete");
        Buttons.add(delete);

        PanelButton icon = new PanelButton("Set Icon");
        Buttons.add(icon);

        PanelButton color = new PanelButton("Set Color");
        Buttons.add(color);
        c.gridx = 0;
        c.gridy = 4;
        c.ipadx = 5;
        c.ipady = 2;
        c.gridwidth = 2;
        Buttons.setBackground(StyleGlobals.BACKGROUND);
        inspectorPanel.add(Buttons, c);

        /**
         * Size Slider
         */
        JLabel Size = new JLabel("Size");
        Size.setForeground(Color.WHITE);
        c.gridx = 0;
        c.gridy = 5;
        c.ipadx = 150;
        c.ipady = 10;
        inspectorPanel.add(Size, c);

        JSlider sizeSlider = new JSlider(JSlider.HORIZONTAL, 5, 50, 50);
        sizeSlider.setForeground(Color.red);
        sizeSlider.setBackground(StyleGlobals.BACKGROUND);
        c.gridx = 1;
        inspectorPanel.add(sizeSlider, c);


        /**
         * Show Label Checkbox
         */
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 6;
        showLabel.setSelected(true);
        showLabel.setBackground(StyleGlobals.BACKGROUND);
        showLabel.setForeground(Color.WHITE);
        showLabel.setFont(StyleGlobals.getFont(12));
        inspectorPanel.add(showLabel, c);

        /**
         * Outer panel
         */
        inspectorPanel.setBackground(StyleGlobals.BACKGROUND);
        setBackground(StyleGlobals.BACKGROUND);


        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(inspectorPanel, "Are you sure you want to delete this location?");

                if (option == JOptionPane.OK_OPTION) {
                    Main.instance.mapScene.removeGameObject(SelectableObject.currentObject);
                }
            }
        });

        sizeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                SelectableObject.currentObject.changeSize(sizeSlider.getValue());
            }
        });

        color.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(inspectorPanel, "Choose a color", Color.RED);
                SelectableObject.currentObject.setColor(newColor);
            }
        });

        showLabel.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                SelectableObject.currentObject.toggleLabel(showLabel.isSelected());
            }
        });

        type.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelectableObject.currentObject.setLocationType((LocationType) type.getSelectedItem());
            }
        });

        JPanel panelWrapper = new JPanel(new BorderLayout());
        panelWrapper.setBackground(StyleGlobals.BACKGROUND);
        add(panelWrapper, BorderLayout.CENTER);

        panelWrapper.add(inspectorPanel, BorderLayout.NORTH);
    }

    public void setLocationText(int x, int y) {
        locationX.setText(String.valueOf(x));
        locationY.setText(String.valueOf(y));
    }
}
