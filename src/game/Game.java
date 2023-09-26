package game;

import utils.Audio;
import utils.Vector;
import utils.Windowed;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static game.Constants.*;

public class Game {
    public static final int STARTING_ASTEROIDS = 3;
    public static final int STARTING_LIVES = 5;
    public static final int SAFETY_DURATION_MS = 5000;
    int score;
    int lives_left;
    int current_level;
    int remainingAsteroids; // Keeping track of asteroids as the player destroys them
    int remainingEnemyShips; // Keep track of NPC spacecraft as the player destroys them.
    long initial_start_time;
    long current_level_start_time;

    boolean safety_period;
    public List<Entity> entities; // List for all active entities during the game.
    public List<Ship> all_ships; // List for all active spacecrafts during the game.

    Player playerShip;
    Key controller; // Player keyboard input listener.
    View view; // Graphical end of the game.

    boolean game_over;
    boolean reset;

    public Game() {
        view = new View(this);
        entities = new ArrayList<>();
        all_ships = new ArrayList<>();

        for (int i = 0; i < STARTING_ASTEROIDS; i++) {
            entities.add(new Asteroid());
        }
        controller = new Key();
        playerShip = new Player(controller);
        entities.add(playerShip);
        all_ships.add(playerShip);
        addSpacecrafts();

        score = 0;
        remainingAsteroids = STARTING_ASTEROIDS;
        remainingEnemyShips = 0;
        lives_left = STARTING_LIVES;
        current_level = 1;

        game_over = false;
        safety_period = true;
        reset = false;

        JFrame window = new Windowed(view, "Spacer");
        window.setResizable(false);
        window.addKeyListener(controller);

    }

    public static void main(String[] args) {
        // The start of the entire program.
        Game game = new Game();
        game.loop();
    }

    public void loop() {
        // Method containing the game loop and ending command line report.
        long DELAY_TIME_IN_MILLISECONDS = Math.round(1000.0 / FPS);
        initial_start_time = System.currentTimeMillis();
        current_level_start_time = initial_start_time;
        while (!game_over) {

            long currentTime = System.currentTimeMillis();
            update();
            view.repaint();
            long timeToSleep = currentTime + DELAY_TIME_IN_MILLISECONDS - System.currentTimeMillis(); // The pause time is offset by how many milliseconds Java has counted.

            if (timeToSleep >= 0) {
                try {
                    Thread.sleep(timeToSleep);
                } catch (Exception ignored) {}
            }
        }

        // At this point, the game would be over.
        Audio.playSound(Audio.death);
        System.out.println("Ship Totalled!");
        System.out.println("Cash in Hand: $" + score);
        System.out.println("Total Game Time: " + ((System.currentTimeMillis() - initial_start_time) / 1000.0));
    }

    public int getScore() {
        return score;
    }

    public int getLivesLeft() {
        return lives_left;
    }

    public int getCurrentLevel() {
        return current_level;
    }

    public boolean reset(boolean next_stage) {
        entities.clear();
        all_ships.clear();
        if (next_stage) {
            current_level++;
        }
        else {
            lives_left--;
            score -= 500;
        }

        if (lives_left == 0) {
            return false;
        }

        for (int i = 0; i < STARTING_ASTEROIDS + (current_level - 1) * 2; i++) {
            entities.add(new Asteroid());
        }

        remainingAsteroids = STARTING_ASTEROIDS + (current_level - 1) * 2;
        playerShip.reset();
        entities.add(playerShip);
        all_ships.add(playerShip);
        addSpacecrafts();

        try {
            Thread.sleep(2000);
        } catch (Exception ignored) {

        }

        safety_period = true;
        current_level_start_time = System.currentTimeMillis();
        return true;

    }

    private void addSpacecrafts() {
        for (int i = 0; i < score / 1000; i++) {
            Controller controller;
            Color colour = Color.RED;
            Random randomint = new Random();
            double chance = randomint.nextDouble();
            if (chance > 0.5) {
                controller = new Spinner();
            } else {
                controller = new Aimless();
            }
            Vector position = new Vector(randomint.nextInt(WORLD_LENGTH), randomint.nextInt(WORLD_HEIGHT));
            EnemySpacecraft enemySpacecraft = new EnemySpacecraft(position, controller, colour);
            entities.add(enemySpacecraft);
            all_ships.add(enemySpacecraft);
        }
    }

    public void update() {
        if (!safety_period) {
            for (int i = 0; i < entities.size(); i++) {
                int j = i + 1;
                while (j < entities.size()) {
                    entities.get(i).touchChecker(entities.get(j));
                    j++;
                }
            }
        }
        else {
            safety_period = System.currentTimeMillis() < current_level_start_time + SAFETY_DURATION_MS;
        }

        game_over = true;
        List<Entity> alive_entities = new ArrayList<>();
        for (Entity entity : entities) {
            if (!entity.is_dead) {
                entity.update();
                alive_entities.add(entity);
                if (entity == playerShip) {
                    game_over = false;
                }
            }
            else if (entity == playerShip){
                reset = true;
                break;
            }
            else {
                updateScore(entity);
            }
        }
        for (Ship ship : all_ships) {
            if (ship.missile != null) {
                alive_entities.add(ship.missile);
                ship.missile = null;
            }
        }


        synchronized (Game.class) {
            if (remainingAsteroids == 0) {
                score += 1000;
                if (score % 10000 == 0) {
                    System.out.println("New life gained!");
                    lives_left += 1;
                }
                Audio.playSound(Audio.levelcomplete);
                reset(true);
            }
            else {
                if (reset) {
                    game_over = !reset(false);
                    reset = false;
                } else {
                    entities.clear();
                    entities.addAll(alive_entities);
                }
            }
        }
    }

    public void updateScore(Entity entity) {
        if (entity.getClass() == Asteroid.class) {
            score += 100;
            remainingAsteroids -= 1;
        } else if (entity.getClass() == EnemySpacecraft.class) {
            score += 200;
            remainingEnemyShips -= 1;
        }
    }

}

