package game;

import utils.Audio;
import utils.Vector;


import java.awt.*;
import java.awt.geom.AffineTransform;

public class Player extends Ship {
    public static final int RADIUS = 8;
    public static final double SCALE = 1.5;
    public static final Color COLOUR = Color.white;

    // Arrowhead polygon (relative) coordinates
    public int[] X_POLYGON = { -6, 0, 6, 0 };
    public int[] Y_POLYGON = { 8, 4, 8, -8 };
    public int[] X_INNER_POLYGON = { -5, 0, 5, 0 };
    public int[] Y_INNER_POLYGON = { 7, 3, 7, -7 };

    public Player(Controller controller) {
        super(new Vector(Constants.WIDTH / 2.0, Constants.HEIGHT / 2.0), new Vector(0, -1), RADIUS, controller, COLOUR);
        dir = new Vector(0,-1);
        death_sound = Audio.bangLarge;
    }


    @Override
    public boolean canHit(Entity other) {
        return true;
    }

    @Override
    public void update() {
        super.update(true);

    }

    public void reset() {
        position.set(new Vector(Constants.WIDTH / 2.0, Constants.HEIGHT / 2.0));
        velocity.set(new Vector(0,0));
        dir.set(new Vector(0, -1));
        is_dead = false;
    }
    @Override
    public void draw(Graphics2D g) {
        AffineTransform affineTransform = g.getTransform();
        g.translate(position.x, position.y);
        double rotation = dir.angle() + Math.PI / 2;
        g.rotate(rotation);
        g.scale(SCALE, SCALE);
        g.setColor(COLOUR);
        g.fillPolygon(X_POLYGON, Y_POLYGON, X_POLYGON.length);
        if (thrusting) {
            g.setColor(Color.red);
            g.fillPolygon(X_INNER_POLYGON, Y_INNER_POLYGON, X_INNER_POLYGON.length);
        }
        g.setTransform(affineTransform);
    }
}
