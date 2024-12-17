package Flyweight;

import Visitor.Visitor;
import java.awt.*;

public class Pellet {
    private final Color color;
    private final int size;
    private final int x, y;  // Pellet's position on the grid

    // Constructor now accepts x, y to set the position of the pellet
    public Pellet(Color color, int size, int x, int y) {
        this.color = color;
        this.size = size;
        this.x = x;
        this.y = y;
    }

    // Render method to draw the pellet at its position on the grid
    public void render(Graphics g, int gridX, int gridY) {
        int offset = (20 - size) / 2;  // Center the pellet in the grid cell
        g.setColor(color);
        g.fillOval(gridX * 20 + offset, gridY * 20 + offset, size, size);
    }

    // Accept method for the Visitor pattern
    public void accept(Visitor visitor) {
        visitor.visit(this);  // Allow the visitor to visit this Pellet
    }

    // Getter for Pellet's x position
    public int getX() {
        return x;
    }

    // Getter for Pellet's y position
    public int getY() {
        return y;
    }
}
