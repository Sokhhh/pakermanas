package PacManState;

import AbstractFactory.IPacMan;
import game.Maze;
import game.ScoreCounterSingleton;

public class DoublePointsState implements PacManState{
    @Override
    public void move(IPacMan pacman, Maze maze) {
        pacman.move(maze); // Normal movement
    }

    @Override
    public void eatPellet(IPacMan pacman, Maze maze) {
        System.out.println("Double points collected!");
        pacman.eatPellet(maze);// Normal pellet collection
        ScoreCounterSingleton scoreCounter = ScoreCounterSingleton.getInstance();
        scoreCounter.addScore(2);
    }
}
