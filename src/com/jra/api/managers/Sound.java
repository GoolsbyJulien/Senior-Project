package com.jra.api.managers;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Sound {


    public URL path;


    public Clip clip;
    public static final Sound WALL_SOUND = new Sound("assets/wall.wav");
    public static final Sound HIT_SOUND = new Sound("assets/hit.wav");

    public Sound (String path){
        AudioInputStream ais = null;
        try {
            ais = AudioSystem.getAudioInputStream(new File(path));
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }


    }
}
