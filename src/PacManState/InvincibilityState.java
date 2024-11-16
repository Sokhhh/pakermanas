package PacManState;

import AbstractFactory.IPacMan;
import game.Maze;

public class InvincibilityState implements PacManState {
    @Override
    public void move(IPacMan pacman, Maze maze) {
        pacman.move(maze); // Normal movement
    }

    @Override
    public void eatPellet(IPacMan pacman, Maze maze) {
        pacman.eatPellet(maze);// Normal pellet collection
    }
}

