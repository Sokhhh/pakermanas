package Flyweight;

import java.awt.*;

public class Pellet {
    private final Color color;
    private final int size;

    public Pellet(Color color, int size) {
        this.color = color;
        this.size = size;
    }

    public void render(Graphics g, int x, int y) {
        int offset = (20 - size) / 2;  // Center the pellet in the grid cell
        g.setColor(color);
        g.fillOval(x * 20 + offset, y * 20 + offset, size, size);
    }
}
