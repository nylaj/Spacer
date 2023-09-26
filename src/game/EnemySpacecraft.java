package game;

import utils.Audio;
import utils.Vector;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class EnemySpacecraft extends Ship {
    public static final int RADIUS = 8;
    public static final double DRAWING_SCALE = 1.5;
    public static final Color COLOUR = Color.RED;

    public int[] XP = { -6, 0, 6, 0 }, YP = { 8, 4, 8, -8 };
    public int[] XPTHRUST = { -5, 0, 5, 0 }, YPTHRUST = { 7, 3, 7, -7 };

    public EnemySpacecraft(Vector spawn_point, Controller controller, Color colour) {
        super(spawn_point, new Vector(), RADIUS, controller, colour);
        dir.set(Vector.polar(Math.random()*2*Math.PI, 1));
        death_sound = Audio.bangMedium;
    }

    @Override
    public void draw(Graphics2D graphics) {
        AffineTransform at = graphics.getTransform();
        graphics.translate(position.x, position.y);
        double rot = dir.angle() + Math.PI / 2;
        graphics.rotate(rot);
        graphics.scale(DRAWING_SCALE, DRAWING_SCALE);
        graphics.setColor(COLOUR);
        graphics.fillPolygon(XP, YP, XP.length);
        if (thrusting) {
            graphics.setColor(Color.YELLOW);
            graphics.fillPolygon(XPTHRUST, YPTHRUST, XPTHRUST.length);
        }
        graphics.setTransform(at);
    }

    @Override
    public void update() {
        super.update(false);
    }

    @Override
    public boolean canHit(Entity other) {
        return other instanceof Player || other instanceof Missile && ((Missile) other).firedByPlayer;
    }

    @Override
    public String toString() {
        return "EnemySpacecraft at" + position + ", " + velocity + ", " + dir + "";
    }
}
