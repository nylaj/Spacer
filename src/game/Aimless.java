package game;

public class Aimless implements Controller {
    public Action action;
    public Aimless() {
        action = new Action();
    }

    @Override
    public Action action() {
        // Randomly choose whether or not to move forward or fire, giving the illusion of autonomy.
        if (Math.random() < 0.05) {
            action.shoot = true;
        }
        else {
            action.shoot = false;
        }

        double turn = Math.random();
        if (turn < 0.1) {
            action.turn = 1;
            action.thrust = (Math.random() < 0.3) ? 1 : 0;
            return action;
        } else {
            action.turn = (turn > 0.9) ? -1 : 0;
            action.thrust = (Math.random() < 0.3) ? 1 : 0;
            return action;
        }
    }
}
