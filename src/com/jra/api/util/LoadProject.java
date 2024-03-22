package com.jra.api.util;

import com.jra.app.Main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class LoadProject {
    public LoadProject() throws IOException {
        //Create save folder if not already present
        Files.createDirectories(Paths.get("Saves"));
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

            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();

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
                }
            }
            scanner.close();
        }
    }
}
