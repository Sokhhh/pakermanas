package Visitor;

import AbstractFactory.IPacMan;
import Decorator.DoublePointDecorator;
import Decorator.InvincibilityDecorator;
import Decorator.PacManDecorator;
import Decorator.TeleporterDecorator;
import Factory.Vaiduoklis;
import Flyweight.Pellet;
import PacManState.DoublePointsState;
import PacManState.TeleportingState;
import game.Maze;

public class PowerUpVisitor implements Visitor {
    private IPacMan pacMan;  // PacMan instance for power-up checks
    private Maze maze;  // Maze instance to call pacMan.eatPellet()

    public PowerUpVisitor(IPacMan pacMan, Maze maze) {
        this.pacMan = pacMan;
        this.maze = maze;  // Store the maze instance
    }

    @Override
    public void visit(IPacMan pacMan) {
        // No power-up logic needed for PacMan itself
    }

    @Override
    public void visit(Vaiduoklis ghost) {
        // No interaction with Ghosts for PowerUps
    }

    @Override
    public void visit(Pellet pellet) {
        // Check if PacMan eats a power-up pellet
        if (pacMan.getX() == pellet.getX() && pacMan.getY() == pellet.getY()) {
            // Handle PowerUp: Update score, PacMan state, etc.
            switch (pellet.getType()) {
                case "invincibility":
                    pacMan.eatPellet(maze);
                    System.out.println("PacMan gained invincibility!");
                    // Assuming PacMan can activate invincibility if it is a decorator
                    if (pacMan instanceof InvincibilityDecorator) {
                        ((InvincibilityDecorator) pacMan).activateInvincibility();  // Activate invincibility
                    }
                    break;
                case "doublePoints":
                    pacMan.eatPellet(maze);
                    System.out.println("PacMan gained double points!");
                    // Handle double points (assuming DoublePointDecorator is applied)
                    IPacMan doublePointPacMan = getDoublePointDecorator(pacMan);
                    if (doublePointPacMan != null && doublePointPacMan instanceof DoublePointDecorator) {
                        ((DoublePointDecorator) doublePointPacMan).activateDoublePoints();  // Activate double points
                    }
                    break;
                case "teleporter":
                    pacMan.eatPellet(maze);
                    System.out.println("PacMan used a teleporter!");
                    // Handle teleporter (assuming TeleporterDecorator is applied)
                    IPacMan teleporterPacMan = getTeleporterDecorator(pacMan);
                    if (teleporterPacMan != null && teleporterPacMan instanceof TeleporterDecorator) {
                        ((TeleporterDecorator) teleporterPacMan).teleport(maze);  // Activate teleportation using the maze
                    }
                    break;
                default:
                    System.out.println("PacMan ate a regular Pellet.");
                    pacMan.eatPellet(maze);  // Call eatPellet using the maze instance
            }
        }
    }

    private IPacMan getTeleporterDecorator(IPacMan pacman) {
        while (pacman instanceof PacManDecorator) {
            if (pacman instanceof TeleporterDecorator) {
                return pacman;  // Found the TeleporterDecorator
            }
            pacman.setPacmanState(new TeleportingState());
            pacman = ((PacManDecorator) pacman).decoratedPacMan;  // Unwrap the decorator
        }
        return null;  // Return null if no TeleporterDecorator is found
    }

    private IPacMan getDoublePointDecorator(IPacMan pacman) {
        while (pacman instanceof PacManDecorator) {
            if (pacman instanceof DoublePointDecorator) {
                return pacman;  // Found the DoublePointDecorator
            }
            pacman.setPacmanState(new DoublePointsState());
            pacman = ((PacManDecorator) pacman).decoratedPacMan;  // Unwrap the decorator
        }
        return null;  // Return null if no DoublePointDecorator is found
    }
}
