package PacManState;

import AbstractFactory.IPacMan;
import game.Maze;

public interface PacManState {
    void move(IPacMan pacman, Maze maze);
    void eatPellet(IPacMan pacman, Maze maze);
}
