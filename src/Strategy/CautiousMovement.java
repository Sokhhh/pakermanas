package Strategy;

import AbstractFactory.IPacMan;
import Factory.Vaiduoklis;
import game.Maze;

import java.io.Serializable;

public class CautiousMovement implements MovementStrategy, Serializable {

    @Override
    public void move(Vaiduoklis ghost, Maze maze, IPacMan pacman){
        // Get the ghost's position
        int x = ghost.getX();
        int y = ghost.getY();

        // Check if the ghost is too close to Pac-Man
        if (Math.abs(x - pacman.getX()) + Math.abs(y - pacman.getY()) < 5) {
            // Move away from Pac-Man if too close
            if (x < pacman.getX() && !maze.isWall(x - 1, y)) x--; // Move left
            else if (x > pacman.getX() && !maze.isWall(x + 1, y)) x++; // Move right

            if (y < pacman.getY() && !maze.isWall(x, y - 1)) y--; // Move up
            else if (y > pacman.getY() && !maze.isWall(x, y + 1)) y++; // Move down
        } else {
            // Random wandering behavior
            if (x < pacman.getX() && !maze.isWall(x + 1, y)) x++; // Move right
            else if (x > pacman.getX() && !maze.isWall(x - 1, y)) x--; // Move left

            if (y < pacman.getY() && !maze.isWall(x, y + 1)) y++; // Move down
            else if (y > pacman.getY() && !maze.isWall(x, y - 1)) y--; // Move up
        }

        // Update the ghost's position
        ghost.setPosition(x, y);
    }
}
