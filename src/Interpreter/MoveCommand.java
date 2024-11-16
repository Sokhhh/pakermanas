package Interpreter;

import AbstractFactory.IPacMan;
import game.Maze;

public class MoveCommand implements Expression {
    @Override
    public void interpret(IPacMan pacMan, Maze maze, String[] args) {
        if (args.length < 2) {
            System.out.println("Invalid command. Usage: move <up|down|left|right>");
            return;
        }

        String direction = args[1];
        switch (direction.toLowerCase()) {
            case "up":
                pacMan.setDirection(0, -1); // Move up
                break;
            case "down":
                pacMan.setDirection(0, 1); // Move down
                break;
            case "left":
                pacMan.setDirection(-1, 0); // Move left
                break;
            case "right":
                pacMan.setDirection(1, 0); // Move right
                break;
            default:
                System.out.println("Invalid direction. Use: up, down, left, or right.");
        }
    }
}
