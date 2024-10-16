package entities;

import game.Maze;
import java.awt.*;

public class PacMan {
    private int x, y;
    private Maze maze;
    private int dx = 0, dy = 0;  // Direction Pac-Man is moving

    public PacMan(Maze maze, int startX, int startY) {
        this.maze = maze;
        this.x = startX;
        this.y = startY;
    }

    public void move() {
        int newX = x + dx;
        int newY = y + dy;

        // Only move if there is no wall in the new direction
        if (!maze.isWall(newX, newY)) {
            x = newX;
            y = newY;
            maze.eatPellet(x, y);  // Eat pellet if present
        }
    }

    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void render(Graphics g) {
        g.setColor(Color.YELLOW);  // PacMan color
        g.fillArc(x * 20, y * 20, 20, 20, 45, 270);  // Draw Pac-Man as a yellow circle with a mouth
    }

    public boolean isCaught(Ghost[] ghosts) {
        for (Ghost ghost : ghosts) {
            if (ghost.getX() == this.x && ghost.getY() == this.y) {
                return true;
            }
        }
        return false;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}