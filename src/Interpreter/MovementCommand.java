package Interpreter;

import AbstractFactory.IPacMan;
import game.Maze;

import java.util.HashMap;
import java.util.Map;

public class MovementCommand implements Expression {
    private final Map<String, int[]> directions;

    public MovementCommand() {
        // Map of valid directions and their deltas
        directions = new HashMap<>();
        directions.put("up", new int[]{0, -1});
        directions.put("down", new int[]{0, 1});
        directions.put("left", new int[]{-1, 0});
        directions.put("right", new int[]{1, 0});
    }

    @Override
    public void interpret(IPacMan pacMan, Maze maze, String input) {
        String[] args = input.split(" ");
        if (args.length != 2 || !args[0].equalsIgnoreCase("move")) {
            System.out.println("Invalid command. Use 'move <up|down|left|right>'.");
            return;
        }

        String direction = args[1].toLowerCase();
        int[] delta = directions.get(direction);

        if (delta == null) {
            System.out.println("Invalid direction. Use 'up', 'down', 'left', or 'right'.");
            return;
        }

        pacMan.setDirection(delta[0], delta[1]);
        pacMan.move(maze); // Perform the movement
        System.out.println("Pac-Man moved " + direction + ".");
    }
}
