package PacManState;

import AbstractFactory.IPacMan;
import game.Maze;

public class TeleportingState implements PacManState {
    @Override
    public void move(IPacMan pacman, Maze maze) {
        pacman.move(maze);
    }

    @Override
    public void eatPellet(IPacMan pacman, Maze maze) {
        pacman.eatPellet(maze);
    }
}
