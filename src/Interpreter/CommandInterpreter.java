package Interpreter;

import AbstractFactory.IPacMan;
import game.Maze;

import java.util.HashMap;
import java.util.Map;

public class CommandInterpreter {
    private Map<String, int[]> directions;

    public CommandInterpreter() {
        directions = new HashMap<>();
        directions.put("up", new int[]{0, -1});
        directions.put("down", new int[]{0, 1});
        directions.put("left", new int[]{-1, 0});
        directions.put("right", new int[]{1, 0});
    }

    public void interpretCommand(String input, IPacMan pacman, Maze maze) {
        String[] args = input.split(" ");
        if (args.length != 2 || !args[0].equalsIgnoreCase("move")) {
            System.out.println("Invalid command. Use 'move <up|down|left|right>'.");
            return;
        }

        int[] direction = directions.get(args[1].toLowerCase());
        if (direction == null) {
            System.out.println("Invalid direction. Use 'up', 'down', 'left', or 'right'.");
            return;
        }

        pacman.setDirection(direction[0], direction[1]);
        pacman.move(maze); // Move Pac-Man
        System.out.println("Pac-Man moved " + args[1] + ".");
    }
}
