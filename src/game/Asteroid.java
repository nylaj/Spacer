package game;

import utils.Vector;
import utils.Audio;

import java.awt.*;
import java.util.Random;

import static game.Constants.*;


public class Asteroid extends Entity {
    public static final double VMIN = 100, VMAX = 150;
    public Sprite sprite;
    public double rotationPerFrame;


    public Asteroid(double x, double y, double vx, double vy, Sprite sprite) {
        super(new Vector(x, y),
                new Vector(vx, vy), sprite.getRadius());
    }

    public Asteroid() {
        super(new Vector(WORLD_LENGTH * Math.random(), WORLD_HEIGHT + Math.random()), new Vector(0, 0), 0);
        double speed = VMIN + ( VMAX - VMIN ) * Math.random();
        double angle = Math.random() * 2 * Math.PI;
        velocity.set(new Vector(speed * Math.cos(angle), speed * Math.sin(angle)));
        death_sound = Audio.bangSmall;
        rotationPerFrame = Math.random() * 0.1;
        double width = Math.min(Math.max(20 + new Random().nextGaussian() * 30, 30), 50);

        Image image = Sprite.ASTEROID;
        double height = width * image.getHeight(null) / image.getWidth(null);
        double direction = Math.random() * 2 * Math.PI;
        this.direction = new Vector(Math.cos(direction), Math.sin(direction));
        this.sprite = new Sprite(image, position, this.direction, width, height);
        radius = this.sprite.getRadius();
    }

    public void draw(Graphics2D g) {
        sprite.draw(g);
    }

    @Override
    public void update() {
        super.update();
        direction.rotate(rotationPerFrame);
    }

    @Override
    public boolean canHit(Entity other) {
        return other instanceof Player || other instanceof Missile && ( (Missile) other ).firedByPlayer;
    }
}
