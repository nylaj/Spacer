package game;
import utils.Video;

import java.awt.*;
import java.io.IOException;

public class Constants {
    public static int HEIGHT = 480;
    public static int WIDTH = 640;
    public static final int SCORE_PANEL_HEIGHT = 30;
    public static int WORLD_FACTOR = 2;
    public static int WORLD_LENGTH = WORLD_FACTOR * WIDTH;
    public static int WORLD_HEIGHT = WORLD_FACTOR * HEIGHT;
    public static final Dimension GAME_SIZE = new Dimension(WIDTH, HEIGHT +SCORE_PANEL_HEIGHT);
    public static final int FPS = 60;
    public static final double DELAY_IN_SECONDS = 1.0 / FPS;
    public static Image ASTEROID;
    public static Image STAR_BACKGROUND;

    static{
        try{
            ASTEROID = Video.loadImage("asteroid");
            STAR_BACKGROUND = Video.loadImage("spacewalking");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}
