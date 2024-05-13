package com.jra.app.UI.components;

import com.jra.app.Main;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Settings extends JFrame{
    public Settings() {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarculaLaf");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        tabbedPane1 = new JTabbedPane();
        generalSP = new JScrollPane();
        generalPanel = new JPanel();
        titleLabel = new JLabel();
        titleField = new JTextField();
        setTitleButton = new JButton();
        descriptionLabel = new JLabel();
        descriptionSP = new JScrollPane();
        descriptionArea = new JTextArea();
        setDescriptionButton = new JButton();
        savePathLabel = new JLabel();
        savePathField = new JTextField();
        colorPanel = new JPanel();
        hotkeysPanel = new JPanel();

        //======== this ========
        setTitle("Settings");
        setIconImage(new ImageIcon("./assets/Icon.png").getImage());
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(new Color(60, 63, 65));

        //======== tabbedPane1 ========
        {
            tabbedPane1.setBorder(null);
            tabbedPane1.setTabPlacement(SwingConstants.LEFT);
            tabbedPane1.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

            //======== generalSP ========
            {
                generalSP.setBorder(null);

                //======== generalPanel ========
                {
                    generalPanel.setBorder(null);
                    generalPanel.setLayout(new MigLayout(
                            "hidemode 3,align left top",
                            // columns
                            "[fill]" +
                                    "[fill]" +
                                    "[fill]",
                            // rows
                            "[]" +
                                    "[]" +
                                    "[]" +
                                    "[]"));

                    //---- titleLabel ----
                    titleLabel.setText("Project Title");
                    generalPanel.add(titleLabel, "cell 0 0");

                    //---- titleField ----
                    titleField.setText("Untitled");
                    generalPanel.add(titleField, "cell 1 0");

                    //---- setTitleButton ----
                    setTitleButton.setText("Set New Title");
                    setTitleButton.addActionListener(e -> setTitle(e));
                    generalPanel.add(setTitleButton, "cell 2 0");

                    //---- descriptionLabel ----
                    descriptionLabel.setText("Description");
                    generalPanel.add(descriptionLabel, "cell 0 1");

                    //======== descriptionSP ========
                    {

                        //---- descriptionArea ----
                        descriptionArea.setLineWrap(true);
                        descriptionSP.setViewportView(descriptionArea);
                    }
                    generalPanel.add(descriptionSP, "cell 1 1,width :200:200,height 50:100:100");

                    //---- setDescriptionButton ----
                    setDescriptionButton.setText("Set New Description");
                    setDescriptionButton.addActionListener(e -> setDescription(e));
                    generalPanel.add(setDescriptionButton, "cell 2 1");

                    //---- savePathLabel ----
                    savePathLabel.setText("Save Path");
                    generalPanel.add(savePathLabel, "cell 0 2");
                    generalPanel.add(savePathField, "cell 1 2");
                }
                generalSP.setViewportView(generalPanel);
            }
            tabbedPane1.addTab("General", generalSP);

            //======== colorPanel ========
            {
                colorPanel.setBorder(null);
                colorPanel.setLayout(new MigLayout(
                        "hidemode 3",
                        // columns
                        "[fill]" +
                                "[fill]" +
                                "[fill]",
                        // rows
                        "[]" +
                                "[]" +
                                "[]"));
            }
            tabbedPane1.addTab("Color Pallet", colorPanel);

            //======== hotkeysPanel ========
            {
                hotkeysPanel.setBorder(null);
                hotkeysPanel.setLayout(new MigLayout(
                        "hidemode 3",
                        // columns
                        "[fill]" +
                                "[fill]",
                        // rows
                        "[]" +
                                "[]" +
                                "[]"));
            }
            tabbedPane1.addTab("Keybindings", hotkeysPanel);
        }
        contentPane.add(tabbedPane1, BorderLayout.CENTER);
        setSize(700, 450);
        setLocationRelativeTo(getOwner());
        setVisible(true);

        try {
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
    }

    private void setTitle(ActionEvent e) {
        Main.instance.currentProject.setProjectName(titleField.getText());
        JOptionPane.showMessageDialog(null, "Title changed");
    }

    private void setDescription(ActionEvent e) {
        Main.instance.currentProject.setProjectDescription(descriptionArea.getText());
        JOptionPane.showMessageDialog(null, "Description changed");
    }

    private JTabbedPane tabbedPane1;
    private JScrollPane generalSP;
    private JPanel generalPanel;
    private JLabel titleLabel;
    private JTextField titleField;
    private JButton setTitleButton;
    private JLabel descriptionLabel;
    private JScrollPane descriptionSP;
    private JTextArea descriptionArea;
    private JButton setDescriptionButton;
    private JLabel savePathLabel;
    private JTextField savePathField;
    private JPanel colorPanel;
    private JPanel hotkeysPanel;
}
