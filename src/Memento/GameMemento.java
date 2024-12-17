package Memento;

import AbstractFactory.IPacMan;
import Factory.Vaiduoklis;
import game.DeepCopyUtil;
import game.Maze;
import game.ScoreCounterSingleton;

import java.util.List;

public class GameMemento {
    private final Maze maze;
    private final IPacMan pacman;
    private final List<Vaiduoklis> ghosts;
    private final int score;

    public GameMemento(Maze maze, IPacMan pacman, List<Vaiduoklis> ghosts, int score) {
        // Assume these objects are safely copied or immutable
        this.maze = DeepCopyUtil.deepCopy(maze);
        this.pacman = DeepCopyUtil.deepCopy(pacman);
        this.ghosts = DeepCopyUtil.deepCopy(ghosts);
        this.score = score;
    }

    public Maze getMaze() {
        return DeepCopyUtil.deepCopy(maze);
    }

    public IPacMan getPacman() {
        return DeepCopyUtil.deepCopy(pacman);
    }

    public List<Vaiduoklis> getGhosts() {
        return DeepCopyUtil.deepCopy(ghosts);
    }

    public int getScore() {
        return DeepCopyUtil.deepCopy(score);
    }
}
