package game;

public class Spinner implements Controller {
    Action action = new Action();

    @Override
    public Action action() {
        // As a Spinner, the ship will only turn and fire whenever it can.
        action.shoot = true;
        action.turn = 1;
        return action;
    }
}
