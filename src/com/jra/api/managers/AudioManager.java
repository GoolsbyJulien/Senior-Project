package com.jra.api.managers;

public class AudioManager {
    public static final boolean SOUND_DISABLED = true;

    public static void play(Sound sound) {

        if (SOUND_DISABLED) {
            // System.err.println("Sound Disabled");
            return;
        }
        try {


            sound.clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
