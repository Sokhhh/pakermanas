// AggressiveVaiduoklis.java
package Factory;

import AbstractFactory.IPacMan;
import Strategy.MovementStrategy;
import entities.PacMan;
import game.Maze;
import java.awt.Graphics;
import java.awt.Color;

public class AggressiveVaiduoklis implements Vaiduoklis {
    private int x, y;
    private int dx, dy;
    private MovementStrategy movementStrategy;;

    public AggressiveVaiduoklis(int startX, int startY, MovementStrategy strategy) {
        this.x = startX;
        this.y = startY;
        this.movementStrategy = strategy;
    }

    @Override
    public void move(Maze maze, IPacMan pacman) {
        if (movementStrategy != null){
            movementStrategy.move(this,maze,pacman);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x * 20, y * 20, 20, 20); // Draw ghost as a red circle
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

    public void setMovementStrategy(MovementStrategy strategy){
        this.movementStrategy = strategy;
    }
}
