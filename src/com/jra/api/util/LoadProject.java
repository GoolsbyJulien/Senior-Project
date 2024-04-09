package com.jra.api.util;

import com.jra.api.core.MapObject;
import com.jra.app.Main;
import com.jra.app.MapObjects.ImageWorld;
import com.jra.app.MapObjects.World;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class LoadProject {
    public LoadProject() throws IOException {
        //Create save folder if not already present
        Path savesFolder = Paths.get("Saves");
        Files.createDirectories(savesFolder);
        JFileChooser chooser = new JFileChooser("Saves");

        //Add an extension filter
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".jra", "jra");
        chooser.addChoosableFileFilter(filter);

        //Open load explorer
        int r = chooser.showOpenDialog(null);

        //Load Save file
        if (r == JFileChooser.APPROVE_OPTION) {
            Scanner scanner = new Scanner(chooser.getSelectedFile());


            Main.instance.currentProject.filePath = null;
            if (!chooser.getSelectedFile().getParent().equals(savesFolder.toString())) {
                Main.instance.currentProject.filePath = chooser.getSelectedFile().getParent();

            }
            boolean deserializationMode = false;
            StringBuilder currentObject = new StringBuilder();
            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();


                if (!deserializationMode) {
                    //Make this more efficient
                    if (currentLine.contains("P:")) {
                        String newSeed = currentLine.replace("P:", "");
                        Main.instance.currentProject.setPerlinSeedLoad(Integer.parseInt(newSeed));
                    } else if (currentLine.contains("N:")) {
                        String newName = currentLine.replace("N:", "");
                        Main.instance.currentProject.setProjectName(newName);
                    } else if (currentLine.contains("Type:")) {
                        String newType = currentLine.replace("Type:", "");
                        Main.instance.currentProject.setProjectType(Integer.parseInt(newType));
                    } else if (currentLine.contains("D:")) {
                        String newDescription = currentLine.replace("D:", "");
                        Main.instance.currentProject.setProjectDescription(newDescription);
                        deserializationMode = true;
                    }
                } else {

                    currentObject.append(currentLine);
                    if (currentLine.trim().equals("}")) {


                        MapObject temp = Serializer.deserialize(currentObject.toString().split("\\{")[0], currentObject.toString());
                        if (temp != null)
                            Main.instance.addComponent(temp);

                        currentObject = new StringBuilder();
                    }

                    //Make this more efficient
                    if (currentLine.contains("Type:")) {
                        String newType = currentLine.replace("Type:", "");
                        Main.instance.currentProject.setProjectType(Integer.parseInt(newType));
                    } else if (currentLine.contains("P:")) {
                        if (Main.instance.currentProject.getProjectType() == 0) {
                            String newSeed = currentLine.replace("P:", "");
                            Main.instance.currentProject.setPerlinSeedLoad(Integer.parseInt(newSeed));
                        }
                    } else if (currentLine.contains("N:")) {
                        String newName = currentLine.replace("N:", "");
                        Main.instance.currentProject.setProjectName(newName);
                    } else if (currentLine.contains("D:")) {
                        String newDescription = currentLine.replace("D:", "");
                        Main.instance.currentProject.setProjectDescription(newDescription);
                    }

                }
                //Set image
                if (Main.instance.currentProject.getProjectType() == 1) {
                    System.out.println("img");
                    //Remove previous project
                    Main.instance.mapScene.goManager.gameObjects.forEach((n) -> {
                        if (n.getClass() == ImageWorld.class) {
                            Main.instance.mapScene.removeGameObject(n);
                        } else if (n.getClass() == World.class) {
                            Main.instance.mapScene.removeGameObject(n);
                        }
                    });

                    //Add image
                    try {
                        BufferedImage img = ImageIO.read(new File(chooser.getSelectedFile().toString().replaceFirst("[.][^.]+$", "") + ".jpg"));
                        Main.instance.currentProject.setImage(img);
                        Main.instance.addComponent(new ImageWorld());
                    } catch (IIOException e) {
                        JOptionPane.showMessageDialog(null, "Cannot find project Image!");
                        Main.instance.currentProject.setProjectType(0);
                        Main.instance.mapScene.addGameobject(Main.instance.world);
                        Main.instance.world.generateMap();
                    }
                }

            }
            scanner.close();

        }
    }
}