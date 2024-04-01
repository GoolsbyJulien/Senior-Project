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
    JTextArea description = new JTextArea(5, 2);


    public void update(SelectableObject object) {

        panel.setVisible(true);
        currentObject = object;
        name.setText(object.getLabel());
    }

    public RightPanel() {
        panel.setVisible(false);
        PanelButton button = new PanelButton("Right Panel");
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
        JScrollPane descriptionPane = new JScrollPane();
        description.setFont(StyleGlobals.getFont(15));
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        descriptionPane.setBackground(Color.red);

        description.setBackground(Color.red);
        descriptionPane.add(description);
        c.gridx = 1;
        c.ipadx = 150;
        c.ipady = 80;
        panel.add(descriptionPane, c);
        System.out.println(descriptionPane.getBackground());
        add(panel);
        panel.setBackground(StyleGlobals.BACKGROUND);
        setBackground(StyleGlobals.BACKGROUND);
    }
}
