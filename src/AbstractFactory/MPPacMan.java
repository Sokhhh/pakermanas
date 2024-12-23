package AbstractFactory;

import PacManState.PacManState;
import Prototype.CloneableEntity;
import game.Maze;
import game.ScoreCounterSingleton;
import Visitor.Visitor;

import java.awt.*;
import java.io.Serializable;

public class MPPacMan implements IPacMan, CloneableEntity, Serializable {
    private int x, y;
    private int dx, dy;  // Direction in x and y axis
    private int mouthAngle = 45;  // Starting mouth angle for animation
    private boolean mouthOpening = false;  // Controls mouth animation

    public MPPacMan(int startX, int startY) {
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

    @Override
    public void eatPellet(Maze maze) {
        // Check if Pac-Man is on a pellet and eat it
        if (maze.eatPellet(x, y)) {
            ScoreCounterSingleton scoreCounter = ScoreCounterSingleton.getInstance(); // Get the ScoreCounter instance
            scoreCounter.addScore(1);  // Increment score by 1 when a pellet is eaten
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

    @Override
    public void setPacmanState(PacManState state) {
        // Set state if needed (empty in this class for now)
    }

    @Override
    public PacManState getPacmanState() {
        return null;  // Return state if needed (empty in this class for now)
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

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    @Override
    public MPPacMan clone() {
        return new MPPacMan(this.x, this.y);
    }

    // Accept method for the Visitor pattern
    public void accept(Visitor visitor) {
        visitor.visit(this);  // Allow the CollisionVisitor (or other visitors) to visit this PacMan
    }
}
