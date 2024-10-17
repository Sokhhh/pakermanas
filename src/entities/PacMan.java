package entities;

import game.Maze;
import java.awt.*;

public class PacMan {
    private int x, y;
    private int dx, dy;  // Direction in x and y axis
    private int mouthAngle = 45;  // Starting mouth angle for animation
    private boolean mouthOpening = false;  // Controls mouth animation

    public PacMan(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.dx = 0;
        this.dy = 0;
    }

    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void move(Maze maze) {
        int newX = x + dx;
        int newY = y + dy;

        // Ensure that Pac-Man doesn't move into walls
        if (!maze.isWall(newX, newY)) {
            x = newX;
            y = newY;
            maze.eatPellet(x, y);


            if (mouthOpening) {
                mouthAngle += 5;  // Open the mouth
                if (mouthAngle >= 45) mouthOpening = false;  // Switch direction when fully open
            } else {
                mouthAngle -= 5;  // Close the mouth
                if (mouthAngle <= 10) mouthOpening = true;  // Switch direction when fully closed
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.YELLOW);  // Pac-Man color
        int pacmanSize = 20;  // Size of Pac-Man

        // Determine mouth direction based on movement
        int startAngle = 0;
        if (dx == 1 && dy == 0) {
            startAngle = mouthAngle;  // Moving right
        } else if (dx == -1 && dy == 0) {
            startAngle = 180 + mouthAngle;  // Moving left
        } else if (dx == 0 && dy == -1) {
            startAngle = 90 + mouthAngle;  // Moving up
        } else if (dx == 0 && dy == 1) {
            startAngle = 270 + mouthAngle;  // Moving down
        }

        // Draw Pac-Man with the correct mouth direction
        g.fillArc(x * pacmanSize, y * pacmanSize, pacmanSize, pacmanSize, startAngle, 360 - 2 * mouthAngle);
    }

    // Get Pac-Man's X position
    public int getX() {
        return x;
    }

    // Get Pac-Man's Y position
    public int getY() {
        return y;
    }

    // Set Pac-Man's position (for multiplayer sync)
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
