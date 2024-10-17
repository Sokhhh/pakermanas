package entities;

import game.Maze;
import java.awt.*;
import java.util.Random;

public class GhostCPU extends Ghost {
    private int x, y;
    private int dx, dy;  // Direction in x and y axis
    private Random rand;
    private static final double RANDOM_MOVE_PROBABILITY = 0.95;

    public GhostCPU(int startX, int startY) {
        super(startX, startY);
        this.x = startX;
        this.y = startY;
        this.dx = 0;
        this.dy = 0;
        this.rand = new Random();
    }

    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void move(Maze maze, PacMan pacman) {
        int newX = x, newY = y;

        // Check if the ghost will move randomly or target Pac-Man
        if (rand.nextDouble() < RANDOM_MOVE_PROBABILITY) {
            // Make a random move
            int direction = rand.nextInt(4);
            if (direction == 0 && !maze.isWall(x + 1, y)) newX = x + 1;  // Move right
            else if (direction == 1 && !maze.isWall(x - 1, y)) newX = x - 1;  // Move left
            else if (direction == 2 && !maze.isWall(x, y + 1)) newY = y + 1;  // Move down
            else if (direction == 3 && !maze.isWall(x, y - 1)) newY = y - 1;  // Move up
        } else {
            // Regular chase logic: move toward Pac-Man if possible
            int pacX = pacman.getX();
            int pacY = pacman.getY();

            // Prioritize horizontal or vertical movement based on distance to Pac-Man
            if (Math.abs(pacX - x) > Math.abs(pacY - y)) {
                // Pac-Man is farther away horizontally, prioritize horizontal movement
                if (pacX > x && !maze.isWall(x + 1, y)) newX = x + 1;  // Move right
                else if (pacX < x && !maze.isWall(x - 1, y)) newX = x - 1;  // Move left
            } else {
                // Pac-Man is farther away vertically, prioritize vertical movement
                if (pacY > y && !maze.isWall(x, y + 1)) newY = y + 1;  // Move down
                else if (pacY < y && !maze.isWall(x, y - 1)) newY = y - 1;  // Move up
            }

            // Fallback horizontal move if vertical is blocked
            if (newX == x && newY == y) {
                if (pacX > x && !maze.isWall(x + 1, y)) {
                    newX = x + 1;  // Move right
                } else if (pacX < x && !maze.isWall(x - 1, y)) {
                    newX = x - 1;  // Move left
                }
            }

            // Fallback vertical move if horizontal is blocked
            if (newX == x && newY == y) {
                if (pacY > y && !maze.isWall(x, y + 1)) {
                    newY = y + 1;  // Move down
                } else if (pacY < y && !maze.isWall(x, y - 1)) {
                    newY = y - 1;  // Move up
                }
            }
        }

        // Update ghost's position
        x = newX;
        y = newY;
    }

    public void render(Graphics g) {
        int ghostSize = 20;  // Ghost size (20x20)
        int xPos = x * ghostSize;
        int yPos = y * ghostSize;

        // Ghost body (half-circle for head and wavy bottom for ghost shape)
        g.setColor(Color.RED);  // Ghost color

        // Head (top half-circle)
        g.fillArc(xPos, yPos, ghostSize, ghostSize, 0, 180);

        // Ghost body rectangle (below the head)
        g.fillRect(xPos, yPos + ghostSize / 2, ghostSize, ghostSize / 2);

        // Wavy bottom part (3 small semi-circles for legs)
        g.setColor(Color.RED);  // Ensuring color consistency

        // Left leg (first small arc)
        g.fillArc(xPos, yPos + ghostSize - 5, ghostSize / 3, 10, 0, 180);

        // Middle leg (second small arc)
        g.fillArc(xPos + ghostSize / 3, yPos + ghostSize - 5, ghostSize / 3, 10, 0, 180);

        // Right leg (third small arc)
        g.fillArc(xPos + (2 * ghostSize / 3), yPos + ghostSize - 5, ghostSize / 3, 10, 0, 180);

        // Ghost eyes (white circles)
        g.setColor(Color.WHITE);
        g.fillOval(xPos + 4, yPos + 5, 5, 5);  // Left eye
        g.fillOval(xPos + 12, yPos + 5, 5, 5);  // Right eye

        // Pupils (small black circles)
        g.setColor(Color.BLACK);
        g.fillOval(xPos + 6, yPos + 7, 2, 2);  // Left pupil
        g.fillOval(xPos + 14, yPos + 7, 2, 2);  // Right pupil
    }

    // Get Ghost's X position
    public int getX() {
        return x;
    }

    // Get Ghost's Y position
    public int getY() {
        return y;
    }

    // Set Ghost's position (for multiplayer sync)
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean collidesWith(PacMan pacman) {
        return this.x == pacman.getX() && this.y == pacman.getY();
    }

}
