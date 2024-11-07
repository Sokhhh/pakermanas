package AbstractFactory;

import Prototype.CloneableEntity;
import game.Maze;
import game.ScoreCounterSingleton;

import java.awt.*;

public class SPPacMan implements IPacMan, CloneableEntity {
    private int x, y;
    private int dx, dy;  // Direction in x and y axis
    private int mouthAngle = 45;  // Starting mouth angle for animation
    private boolean mouthOpening = false;  // Controls mouth animation

    public SPPacMan(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.dx = 0;
        this.dy = 0;
    }

    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void move(Maze maze) {
        int newX = x + dx;  // Calculate new X position
        int newY = y + dy;  // Calculate new Y position

        // Ensure that Pac-Man doesn't move into walls
        if (!maze.isWall(newX, newY)) {
            x = newX;  // Update Pac-Man's X position
            y = newY;  // Update Pac-Man's Y position

            // Check if Pac-Man is on a pellet and eat it
            if (maze.eatPellet(x, y)) {
                ScoreCounterSingleton scoreCounter = ScoreCounterSingleton.getInstance(); // Get the ScoreCounter instance
                scoreCounter.addScore(1);  // Increment score by 1 when a pellet is eaten
            }

            // Manage mouth animation
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

    public int getDx(){return dx;}
    public int getDy(){return dy;}

    @Override
    public SPPacMan clone() {
        return new SPPacMan(this.x, this.y);
    }
}
