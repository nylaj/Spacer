package game;

import javax.sound.sampled.Clip;
import java.awt.*;

import utils.Audio;
import utils.Vector;

import static game.Constants.*;

public abstract class Entity {
    //Entity abstract class that all moving game objects are derived from
    public Vector position;
    public Vector velocity;
    public Vector direction;
    public double radius;
    public boolean is_dead;
    public Clip death_sound = null;


    public Entity(Vector position, Vector velocity, double radius) {
        this.position = position;
        this.velocity = velocity;
        this.radius = radius;
        this.is_dead = false;
        this.direction = new Vector(1,0);
    }

    public void update() {
        position.addScaled(velocity, DELAY_IN_SECONDS);
        position.wrapAround(WIDTH, HEIGHT);
    }

    public boolean overlap(Entity other) {
        return this.position.distance(other.position) < this.radius + other.radius;
    }

    public void touchChecker(Entity other) {
        // So long as the two entities aren't dead, if they are allowed to collide and are overlapping, they're both dead.
        if (!this.is_dead && !other.is_dead && this.canHit(other) && this.overlap(other)){
            this.hit();
            other.hit();
        }
    }

    public double distanceTo(Entity other) {
        // Calculates the distance between one entity and another entity.
        double distance_x = this.position.x - other.position.x;
        if (Math.abs(distance_x) > Constants.WORLD_LENGTH / 2.0) {
            distance_x = Math.abs(distance_x) - Constants.WORLD_LENGTH;
        }

        double distance_y = this.position.y - other.position.y;
        if (Math.abs(distance_y) > Constants.WORLD_HEIGHT / 2.0) {
            distance_y = Math.abs(distance_y) - Constants.WORLD_HEIGHT;
        }

        return Math.hypot(distance_x, distance_y);
    }

    public abstract boolean canHit(Entity other); // Rules for who can collide with whom.

    public abstract void draw(Graphics2D g);

    public void hit()
    {
        is_dead = true;
        if (death_sound != null)
            Audio.playSound(death_sound);
    }

}
