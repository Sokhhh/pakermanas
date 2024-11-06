// Ghost.java
package Factory;

import AbstractFactory.IPacMan;
import entities.PacMan;
import game.Maze;
import java.awt.Graphics;

public interface Vaiduoklis {
    void move(Maze maze, IPacMan pacman); // Defines ghost movement behavior
    void render(Graphics g); // Defines rendering for each ghost type
    boolean collidesWith(IPacMan pacman);
    void setPosition(int x, int y);
    // Get Ghost's X position
    int getX();

    // Get Ghost's Y position
    int getY();
    void setDirection(int dx, int dy);
}
