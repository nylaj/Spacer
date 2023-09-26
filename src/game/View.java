package game;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

import static game.Constants.*;

public class View extends JComponent {
    public static final Color TEXT_COLOR = Color.WHITE, TEXT_BG_COLOR = Color.BLACK;
    private final Game game;
    Image background_image = Constants.STAR_BACKGROUND;
    AffineTransform background_transform;

    public View(Game game) {
        // Setting up the graphics.
        this.game = game;
        double imageWidth = background_image.getWidth(null);
        double imageHeight = background_image.getHeight(null);
        double stretchX;
        if (imageWidth > Constants.WIDTH) { // If the image is larger than the game dimensions
            stretchX = 1; // We don't need to stretch it or scale it down.
        } else { // Otherwise stretch the image to fit.
            stretchX = Constants.WIDTH / imageWidth;
        }
        double stretchY;
        if (imageHeight > Constants.HEIGHT) {
            stretchY = 1;
        } else {
            stretchY = Constants.HEIGHT / imageHeight;
        }
        background_transform = new AffineTransform();
        background_transform.scale(stretchX, stretchY);

    }

    public void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        graphics.drawImage(background_image, background_transform, null);
        synchronized (Game.class) {
            for (Entity entity : game.entities)
                entity.draw(graphics); // Draw every alive entity where it should be.
        }

        // Bottom GUI
        graphics.setColor(TEXT_BG_COLOR);
        graphics.fillRect(0, getHeight() - SCORE_PANEL_HEIGHT, getWidth(), SCORE_PANEL_HEIGHT);

        graphics.setColor(TEXT_COLOR);
        graphics.drawRect(0, getHeight() - SCORE_PANEL_HEIGHT, getWidth(), SCORE_PANEL_HEIGHT);

        graphics.setFont(new Font("dialog", Font.ITALIC, (2 * SCORE_PANEL_HEIGHT / 3)));

        graphics.drawString("Cash: $" + game.getScore(), 10, getHeight() - SCORE_PANEL_HEIGHT / 3);
        graphics.drawString("Lives Left: " + game.getLivesLeft(), 200, getHeight() - SCORE_PANEL_HEIGHT / 3);
        graphics.drawString("Level: " + game.getCurrentLevel(), 400, getHeight() - SCORE_PANEL_HEIGHT / 3);

    }

    public Dimension getPreferredSize() {
        return Constants.GAME_SIZE;
    }
}
