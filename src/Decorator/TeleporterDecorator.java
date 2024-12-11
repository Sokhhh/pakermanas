package Decorator;

import AbstractFactory.IPacMan;
import game.Maze;
import java.awt.*;
import java.util.Random;

public class TeleporterDecorator extends PacManDecorator {

    private final Random random;  // Random generator to pick teleport destination

    public TeleporterDecorator(IPacMan decoratedPacMan) {
        super(decoratedPacMan);
        this.random = new Random();
    }

    @Override
    public void move(Maze maze) {
        // Delegate to decorated Pac-Man, but check if teleportation is triggered
        super.move(maze);
    }

    @Override
    public void render(Graphics g) {
        // Render the base Pac-Man with teleportation effects if needed
        decoratedPacMan.render(g);
    }

    // Method to teleport Pac-Man to a random valid position
    public void teleport(Maze maze) {
        // Find a random valid position within the maze
        int newX = random.nextInt(maze.getWidth());
        int newY = random.nextInt(maze.getHeight());

        // Ensure that the new position is valid (not a wall)
        while (!maze.isValidPosition(newX, newY)) {
            newX = random.nextInt(maze.getWidth());
            newY = random.nextInt(maze.getHeight());
        }

        // Set the Pac-Man's position to the valid random position
        decoratedPacMan.setPosition(newX, newY);
    }

    // Override the eatPellet method to teleport when a specific pellet is eaten
    @Override
    public void eatPellet(Maze maze) {
        super.eatPellet(maze);
    }
}
