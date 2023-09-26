package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import utils.Vector;
import utils.Video;

public class Sprite {
    public static Image ASTEROID, SPACEWALKING;
    static {
        try {
            ASTEROID = Video.loadImage("asteroid");
            SPACEWALKING = Video.loadImage("spacewalking");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Image image;
    public Vector position;
    public Vector direction;
    public double width;
    public double height;

    public Sprite(Image image, Vector position, Vector direction, double width, double height) {
        this.image = image;
        this.position = position;
        this.direction = direction;
        this.width = width;
        this.height = height;
    }

    public double getRadius() {
        return (width + height) / 4.0;
    }

    public Rectangle2D getBounds2D() {
        return new Rectangle2D.Double((position.x - width / 2), position.y - height / 2, width, height);
    }

    public void draw(Graphics2D graphics) {
        double imageWidth = image.getWidth(null);
        double imageHeight = image.getHeight(null);

        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(direction.angle(), 0, 0);
        affineTransform.scale(width / imageWidth, height / imageHeight);
        affineTransform.translate(-imageWidth / 2.0, -imageHeight / 2.0);
        AffineTransform transform = graphics.getTransform();

        graphics.translate(position.x, position.y);
        graphics.drawImage(image, affineTransform, null);
        graphics.setTransform(transform);
        graphics.setColor(Color.RED);
    }

}

