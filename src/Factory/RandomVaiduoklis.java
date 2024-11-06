// RandomVaiduoklis.java
package Factory;

import AbstractFactory.IPacMan;
import entities.PacMan;
import game.Maze;
import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;

public class RandomVaiduoklis implements Vaiduoklis {
    private int x, y;
    private Random random;
    private int dx, dy;

    public RandomVaiduoklis(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.random = new Random();
    }

    @Override
    public void move(Maze maze, IPacMan pacman) {
        int direction = random.nextInt(4);
        if (direction == 0 && !maze.isWall(x + 1, y)) x++; // Right
        else if (direction == 1 && !maze.isWall(x - 1, y)) x--; // Left
        else if (direction == 2 && !maze.isWall(x, y + 1)) y++; // Down
        else if (direction == 3 && !maze.isWall(x, y - 1)) y--; // Up
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(x * 20, y * 20, 20, 20); // Draw ghost as a blue circle
    }

    @Override
    public boolean collidesWith(IPacMan pacman) {
        return this.x == pacman.getX() && this.y == pacman.getY();
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Get Ghost's X position
    public int getX() {
        return x;
    }

    // Get Ghost's Y position
    public int getY() {
        return y;
    }

    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
}
