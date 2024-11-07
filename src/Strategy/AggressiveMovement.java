// AggressiveMovementStrategy.java
package Strategy;

import AbstractFactory.IPacMan;
import Factory.Vaiduoklis;
import game.Maze;

public class AggressiveMovement implements MovementStrategy {

    @Override
    public void move(Vaiduoklis ghost, Maze maze, IPacMan pacman) {
        int x = ghost.getX();
        int y = ghost.getY();

        if (x < pacman.getX() && !maze.isWall(x + 1, y)) x++;
        else if (x > pacman.getX() && !maze.isWall(x - 1, y)) x--;

        if (y < pacman.getY() && !maze.isWall(x, y + 1)) y++;
        else if (y > pacman.getY() && !maze.isWall(x, y - 1)) y--;

        ghost.setPosition(x, y);
    }
}
