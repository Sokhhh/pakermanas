// Ghost.java
package Factory;

import entities.PacMan;
import game.Maze;
import java.awt.Graphics;

public interface Vaiduoklis {
    void move(Maze maze, PacMan pacman); // Defines ghost movement behavior
    void render(Graphics g); // Defines rendering for each ghost type
    boolean collidesWith(PacMan pacman);
}
