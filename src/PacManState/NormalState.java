package PacManState;

import AbstractFactory.IPacMan;
import game.Maze;

public class NormalState implements PacManState {
    @Override
    public void move(IPacMan pacman, Maze maze) {
        pacman.move(maze); // Use default movement
    }

    @Override
    public void eatPellet(IPacMan pacman, Maze maze) {
        pacman.eatPellet(maze); // Use default pellet collection
    }
}
