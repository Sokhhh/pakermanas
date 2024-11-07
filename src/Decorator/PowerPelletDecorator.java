package Decorator;

import AbstractFactory.IPacMan;
import game.Maze;

import java.awt.*;

public class PowerPelletDecorator implements IPacMan {

    private int mouthAngle = 45;  // Starting mouth angle for animation

    private IPacMan pacman;
    private boolean superModeActive;
    private long superModeEndTime;

    public PowerPelletDecorator(IPacMan pacman) {
        this.pacman = pacman;
        this.superModeActive = false;
    }

    @Override
    public void move(Maze maze) {
        if (superModeActive) {
            long currentTime = System.currentTimeMillis();
            if (currentTime > superModeEndTime) {
                // Deactivate super mode after time expires.
                superModeActive = false;
            }
        }
        pacman.move(maze);
    }

    @Override
    public int getX() {return pacman.getX();}

    @Override
    public int getY() {return pacman.getY();}

    @Override
    public int getDx(){return pacman.getDx();}

    @Override
    public int getDy(){return pacman.getDy();}

    @Override
    public void setDirection(int dx, int dy) {pacman.setDirection(dx, dy);}

    @Override
    public void setPosition(int x, int y) {pacman.setPosition(x, y);}

    @Override
    public void render(Graphics g) {
        // Render the super mode effect if active
        if (superModeActive) {
            g.setColor(Color.CYAN);  // Color for Super Pac-Man (you can choose any color)
            int pacmanSize = 20;  // Size of Pac-Man

            // Get the PacMan position
            int pacmanX = pacman.getX();
            int pacmanY = pacman.getY();

            // Determine mouth direction based on Pac-Man's movement direction
            int startAngle = 0;
            if (pacman.getDx() == 1 && pacman.getDy() == 0) {
                startAngle = mouthAngle;  // Moving right
            } else if (pacman.getDx() == -1 && pacman.getDy() == 0) {
                startAngle = 180 + mouthAngle;  // Moving left
            } else if (pacman.getDx() == 0 && pacman.getDy() == -1) {
                startAngle = 90 + mouthAngle;  // Moving up
            } else if (pacman.getDx() == 0 && pacman.getDy() == 1) {
                startAngle = 270 + mouthAngle;  // Moving down
            }

            // Draw Pac-Man with the correct mouth direction and super mode effect
            g.fillArc(pacmanX * pacmanSize, pacmanY * pacmanSize, pacmanSize, pacmanSize, startAngle, 360 - 2 * mouthAngle);
        } else {
            // Render the base PacMan (calls the original render method)
            pacman.render(g);
        }
    }

    // This method activates super mode for a limited time (e.g., 10 seconds).
    public void activateSuperMode() {
        this.superModeActive = true;
        this.superModeEndTime = System.currentTimeMillis() + 10000; // Super mode lasts for 10 seconds
    }

    public boolean isSuperModeActive() {
        return superModeActive;
    }
}
