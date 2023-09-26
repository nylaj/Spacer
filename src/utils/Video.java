package utils;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Video {
    public final static String PATH = "images/";
    public final static String EXTENSION = ".png";

    public static Map<String, Image> image_list = new HashMap<>();

    public static Image getImage(String name) {
        return image_list.get(name);
    }

    public static Image loadImage(String filename) throws IOException {
        BufferedImage image = null;
        image = ImageIO.read(new File(PATH + filename + EXTENSION));
        image_list.put(filename, image);
        return image;
    }

    public static Image loadImage(String image_name, String filename) throws IOException {
        BufferedImage image = null;
        image = ImageIO.read(new File(PATH + filename + EXTENSION));
        image_list.put(image_name, image);
        return image;
    }

    public static void loadImages(String[] fNames) throws IOException {
        for (String s : fNames)
            loadImage(s);
    }

    public static void loadImages(Iterable<String> fNames) throws IOException {
        for (String s : fNames)
            loadImage(s);
    }

}
