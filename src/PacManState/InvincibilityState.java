package PacManState;

import AbstractFactory.IPacMan;
import game.Maze;

import java.io.Serializable;

public class InvincibilityState implements PacManState, Serializable {
    @Override
    public void move(IPacMan pacman, Maze maze) {
        pacman.move(maze); // Normal movement
    }

    @Override
    public void eatPellet(IPacMan pacman, Maze maze) {
        pacman.eatPellet(maze);// Normal pellet collection
    }
}

