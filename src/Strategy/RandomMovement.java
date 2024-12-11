package Strategy;

import AbstractFactory.IPacMan;
import Factory.Vaiduoklis;
import game.Maze;

import java.util.Random;

public class RandomMovement implements MovementStrategy{
    private final Random random = new Random();

    @Override
    public void move(Vaiduoklis ghost, Maze maze, IPacMan pacman) {
        int x = ghost.getX();
        int y = ghost.getY();
        int direction = random.nextInt(4);

        switch (direction) {
            case 0: if (!maze.isWall(x + 1, y)) x++; break; // Move right
            case 1: if (!maze.isWall(x - 1, y)) x--; break; // Move left
            case 2: if (!maze.isWall(x, y + 1)) y++; break; // Move down
            case 3: if (!maze.isWall(x, y - 1)) y--; break; // Move up
            default: break;
        }

        ghost.setPosition(x, y);
    }
}
