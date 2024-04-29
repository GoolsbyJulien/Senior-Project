package com.jra.api.util;

import com.jra.api.core.MapObject;
import com.jra.app.Main;
import com.jra.app.Project;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SaveProject {


    public static void saveAs() throws IOException {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        Project currentProject = Main.instance.currentProject;

        //Create save folder if not already present
        Files.createDirectories(Paths.get("Saves"));
        JFileChooser chooser = new JFileChooser("Saves");
        JTextField field = new JTextField("Hello, World");
        JPanel accessory = new JPanel();
        accessory.setLayout(new FlowLayout());
        accessory.add(field);


        //Add an extension filter
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".jra", "jra");
        chooser.addChoosableFileFilter(filter);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //Open save explorer
        int r = chooser.showSaveDialog(null);

        //Create Save file
        if (r == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().toString();

            quickSave(path);
        }
    }

    public static void quickSave(String path) throws IOException {

        Project currentProject = Main.instance.currentProject;

        String name = currentProject.getProjectName().equals("Untitled") ? JOptionPane.showInputDialog("Enter Project Name") : currentProject.getProjectName();

        currentProject.setProjectName(name);


        File saveFile = new File(path + "\\" + name + "\\" + name + ".jra");


        Files.createDirectories(Paths.get(path, name));


        if (saveFile.exists())
            saveFile.delete();
        saveFile.createNewFile();

        //Save image if applicable

        if (currentProject.getProjectType() == 1) {
            File outputImage = new File(path + "\\" + name + "\\"
                    + name + ".jpg");
            ImageIO.write(currentProject.getImage(), "jpg", outputImage);
        }

        //Write information into file
        FileWriter fw = new FileWriter(saveFile);

        fw.write("Type:" + currentProject.getProjectType() + "\n");
        fw.write("P:" + currentProject.getPerlinSeed() + "\n");
        fw.write("N:" + currentProject.getProjectName() + "\n");
        fw.write("Type:" + currentProject.getProjectType() + "\n");
        fw.write("D:" + currentProject.getProjectDescription());

        for (MapObject m : Main.instance.mapScene.goManager.gameObjects) {
            String sObject = m.serialize();
            if (sObject != null)
                fw.write(sObject + "\n");
        }
        fw.close();
    }

}
