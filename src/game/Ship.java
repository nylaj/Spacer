package game;

import utils.Audio;
import utils.Vector;

import java.awt.*;

public abstract class Ship extends Entity {
    public static final double STEER_RATE = Math.PI/20;
    public static final double ACCELERATION = 200;
    public static final double DRAG = 0.01;
    public static final long MISSILE_FIRE_COOLDOWN_TIME = 500;
    public long timeLastShot = System.currentTimeMillis();

    public Missile missile = null;
    public boolean thrusting = false;
    public Color colour;
    public Controller controller;
    public Vector dir;

    public Ship(Vector spawn_point, Vector velocity, double radius, Controller controller, Color colour){
        super(spawn_point, velocity, radius);
        dir = new Vector(0,-1);
        this.controller = controller;
        this.colour = colour;
    }

    protected void makeMissile(){
        missile = new Missile(new Vector(position), new Vector(velocity), this.getClass() == Player.class);
        missile.position.addScaled(dir, (radius+ missile.radius)*1.1);
        missile.velocity.addScaled(dir, Missile.INITIAL_SPEED);

    }

    public void update(boolean isPlayer) {
        super.update();
        Action action = controller.action();
        dir.rotate(action.turn * STEER_RATE);
        velocity.addScaled(dir, action.thrust * ACCELERATION * Constants.DELAY_IN_SECONDS);
        velocity.multiply(1 - DRAG);
        long currentTime = System.currentTimeMillis();
        if (action.shoot && currentTime - timeLastShot > MISSILE_FIRE_COOLDOWN_TIME) {
            makeMissile();
            action.shoot = false;
            timeLastShot = currentTime;
            if (isPlayer) { // Only the player's pew pew noises should be heard...
                Audio.playSound(Audio.fire_missile);
            }
        }

        thrusting = action.thrust != 0;
    }
}
