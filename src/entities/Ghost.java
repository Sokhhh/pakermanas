package entities;

import game.Maze;
import java.awt.*;

public class Ghost {
    private int x, y;
    private int dx, dy;  // Direction in x and y axis

    public Ghost(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.dx = 0;
        this.dy = 0;
    }

    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void move(Maze maze, PacMan pacman) {
        int newX = x + dx;
        int newY = y + dy;

        // Ensure that Ghost doesn't move into walls
        if (!maze.isWall(newX, newY)) {
            x = newX;
            y = newY;
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x * 20, y * 20, 20, 20);  // Drawing Ghost as a red circle
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
