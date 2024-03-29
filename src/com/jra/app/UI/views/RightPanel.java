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
    public JPanel panel = new JPanel(new GridBagLayout());
    JTextField name = new JTextField();
    JTextArea description = new JTextArea(5, 2);


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

        //Grid bag constraints
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 15;
        c.ipady = 10;

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setForeground(Color.WHITE);
        panel.add(nameLabel, c);

        name.setFont(StyleGlobals.getFont(15));
        name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelectableObject.currentObject.setLabel(name.getText());
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
        descriptionPane.add(description);
        c.gridx = 1;
        c.ipadx = 150;
        c.ipady = 80;
        panel.add(descriptionPane, c);

        add(panel);
        panel.setBackground(StyleGlobals.BACKGROUND);
        setBackground(StyleGlobals.BACKGROUND);
    }
}
