package Interpreter;

import AbstractFactory.IPacMan;
import game.Maze;

public interface Expression {
    void interpret(IPacMan pacMan, Maze maze, String[] args);
}
