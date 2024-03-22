package com.jra.api.util;

import com.jra.app.Main;
import com.jra.app.Project;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SaveProject {
    private Project currentProject;

    public SaveProject() throws IOException {
        currentProject = Main.instance.currentProject;

        //Create save folder if not already present
        Files.createDirectories(Paths.get("Saves"));
        JFileChooser chooser = new JFileChooser("Saves");

        //Add an extension filter
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".jra", "jra");
        chooser.addChoosableFileFilter(filter);
        //Open save explorer
        int r = chooser.showSaveDialog(null);

        //Create Save file
        if (r == JFileChooser.APPROVE_OPTION) {
            File saveFile;

            if (!chooser.getSelectedFile().toString().contains(".jra")) {
                saveFile = new File((chooser.getSelectedFile() + ".jra"));
            } else {
                saveFile = new File(chooser.getSelectedFile().toURI());
            }


            //Write information into file
            FileWriter fw = new FileWriter(saveFile);

            fw.write("P:" + currentProject.getPerlinSeed() + "\n");
            fw.write("N:" + currentProject.getProjectName());
            fw.close();
        }
    }
}
