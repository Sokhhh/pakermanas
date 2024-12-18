package Factory;

import AbstractFactory.IPacMan;
import Strategy.MovementStrategy;
import game.Maze;
import Visitor.Visitor;
import java.awt.Graphics;
import java.awt.Color;
import java.io.Serializable;

public class CautiousVaiduoklis implements Vaiduoklis, Serializable {
    private int x, y;
    private int dx, dy;
    private MovementStrategy movementStrategy;

    public CautiousVaiduoklis(int startX, int startY, MovementStrategy strategy) {
        this.x = startX;
        this.y = startY;
        this.movementStrategy = strategy;
    }

    @Override
    public void move(Maze maze, IPacMan pacman) {
        if (movementStrategy != null) {
            movementStrategy.move(this, maze, pacman);
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

    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void setMovementStrategy(MovementStrategy strategy) {
        this.movementStrategy = strategy;
    }

    // Accept method for the Visitor pattern
    public void accept(Visitor visitor) {
        visitor.visit(this);  // Allow the visitor (like CollisionVisitor) to visit this Ghost
    }
}
