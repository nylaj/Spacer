package game;

import java.awt.*;
import utils.Vector;

public class Missile extends Entity {
    private double lifetime;
    public static final int RADIUS = 2;
    public static final int MISSILE_LIFE = 50;
    public static final int INITIAL_SPEED = 100;

    boolean firedByPlayer;

    public Missile(Vector position, Vector velocity, boolean player_fired) {
        super(position, velocity, RADIUS);
        this.lifetime = MISSILE_LIFE;
        firedByPlayer = player_fired;
    }

    @Override
    public void update() {
        super.update();
        lifetime -= 1;
        if (lifetime <= 0) is_dead = true;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillOval((int) position.x - RADIUS, (int) position.y - RADIUS, 2 * RADIUS, 2 * RADIUS);


    }

    @Override
    public boolean canHit(Entity other) {
        return firedByPlayer || other.getClass() == Player.class || other.getClass() == Missile.class;
    }

    @Override
    public void hit() {
        is_dead = true;

    }
}