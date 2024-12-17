package Flyweight;

import Visitor.Visitor;
import java.awt.*;

public class Pellet {
    private final Color color;
    private final int size;
    private final int x, y;  // Pellet's position on the grid
    private final String type;  // Type of the pellet (e.g., "regular", "invincibility", etc.)

    // Constructor now accepts x, y to set the position of the pellet, and type for pellet type
    public Pellet(Color color, int size, int x, int y, String type) {
        this.color = color;
        this.size = size;
        this.x = x;
        this.y = y;
        this.type = type;  // Set the type of the pellet
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

    // Getter for Pellet's type
    public String getType() {
        return type;  // Return the type of the pellet (e.g., "regular", "invincibility", etc.)
    }

    // Getter for Pellet's size
    public int getSize() {
        return size;  // Return the size of the pellet
    }

    // Getter for Pellet's color
    public Color getColor() {
        return color;  // Return the color of the pellet
    }
}
