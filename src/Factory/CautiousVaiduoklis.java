// CautiousVaiduoklis.java
package Factory;

import AbstractFactory.IPacMan;
import entities.PacMan;
import game.Maze;
import java.awt.Graphics;
import java.awt.Color;

public class CautiousVaiduoklis implements Vaiduoklis {
    private int x, y;

    public CautiousVaiduoklis(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    @Override
    public void move(Maze maze, IPacMan pacman) {
        if (Math.abs(x - pacman.getX()) + Math.abs(y - pacman.getY()) < 5) {
            // Move away if too close
            if (x < pacman.getX() && !maze.isWall(x - 1, y)) x--;
            else if (x > pacman.getX() && !maze.isWall(x + 1, y)) x++;

            if (y < pacman.getY() && !maze.isWall(x, y - 1)) y--;
            else if (y > pacman.getY() && !maze.isWall(x, y + 1)) y++;
        } else {
            // Random wandering otherwise
            if (x < pacman.getX() && !maze.isWall(x + 1, y)) x++;
            else if (x > pacman.getX() && !maze.isWall(x - 1, y)) x--;

            if (y < pacman.getY() && !maze.isWall(x, y + 1)) y++;
            else if (y > pacman.getY() && !maze.isWall(x, y - 1)) y--;
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval(x * 20, y * 20, 20, 20); // Draw ghost as a green circle
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
}
