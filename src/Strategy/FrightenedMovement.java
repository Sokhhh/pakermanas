package Strategy;

import AbstractFactory.IPacMan;
import Factory.Vaiduoklis;
import game.Maze;

import java.io.Serializable;

public class FrightenedMovement implements MovementStrategy, Serializable {

    @Override
    public void move(Vaiduoklis ghost, Maze maze, IPacMan pacman){
        // Get the ghost's position
        int x = ghost.getX();
        int y = ghost.getY();

        // Update the ghost's position
        ghost.setPosition(x, y);
    }
}
