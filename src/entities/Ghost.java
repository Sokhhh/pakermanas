package entities;

import game.Maze;
import java.awt.*;
import java.util.Random;

public class Ghost {
    private int x, y;
    private Maze maze;
    private PacMan pacman;
    private int dx = 0, dy = 0;

    public Ghost(Maze maze, PacMan pacman, int startX, int startY) {
        this.maze = maze;
        this.pacman = pacman;
        this.x = startX;
        this.y = startY;
    }

    public void move() {
        Random rand = new Random();
        int direction = rand.nextInt(4);

        int newX = x, newY = y;
        if (direction == 0 && !maze.isWall(x + 1, y)) newX++;
        else if (direction == 1 && !maze.isWall(x - 1, y)) newX--;
        else if (direction == 2 && !maze.isWall(x, y + 1)) newY++;
        else if (direction == 3 && !maze.isWall(x, y - 1)) newY--;

        x = newX;
        y = newY;
    }

    public void render(Graphics g) {
        g.setColor(Color.RED);  // Ghost color
        g.fillRect(x * 20, y * 20, 20, 20);  // Draw ghost as a red square
    }

    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}