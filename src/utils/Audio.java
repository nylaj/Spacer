package utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Audio {

    final static String path = "sounds/";

    // Audio files to refer to
    public final static Clip bangLarge = getClip("bangLarge");
    public final static Clip bangMedium = getClip("bangMedium");
    public final static Clip bangSmall = getClip("bangSmall");
    public final static Clip fire_missile = getClip("fire");
    public final static Clip levelcomplete = getClip("levelcomplete");
    public final static Clip death = getClip("death");

    public static void playSound(Clip clip) {
        clip.setFramePosition(0); // Reset to the start position
        clip.start(); // Play
    }

    private static Clip getClip(String filename) {
        //Obtains and packages audio file on the filesystem into a Clip object using AudioInputStream
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            AudioInputStream sample = AudioSystem.getAudioInputStream(new File(path + filename + ".wav"));
            clip.open(sample);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clip;
    }


}
