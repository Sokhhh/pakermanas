// MovementStrategy.java
package Strategy;

import AbstractFactory.IPacMan;
import Factory.Vaiduoklis;
import game.Maze;

public interface MovementStrategy {
    void move(Vaiduoklis ghost, Maze maze, IPacMan pacman);
}