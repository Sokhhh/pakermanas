package Visitor;

import AbstractFactory.IPacMan;
import Factory.Vaiduoklis;
import Flyweight.Pellet;
import game.Maze;

public class CollisionVisitor implements Visitor {
    private IPacMan pacMan;  // PacMan instance for collision checks

    public CollisionVisitor(IPacMan pacMan) {
        this.pacMan = pacMan;
    }

    @Override
    public void visit(IPacMan pacMan) {
        // No collision logic needed for PacMan itself
    }

    @Override
    public void visit(Vaiduoklis ghost) {
        // Check if PacMan collides with a Ghost
        if (pacMan.getX() == ghost.getX() && pacMan.getY() == ghost.getY()) {
            // Handle collision: PacMan loses a life or other game logic
            System.out.println("PacMan collided with a Ghost!");
            // Assuming PacMan has a method to lose life
            // pacMan.loseLife();
        }
    }

    @Override
    public void visit(Pellet pellet) {
        // Check if PacMan eats a Pellet
        if (pacMan.getX() == pellet.getX() && pacMan.getY() == pellet.getY()) {
            // Handle PacMan eating the pellet: Update score or state
            System.out.println("PacMan ate a Pellet!");
            // Assuming PacMan has a method to eat a pellet
            pacMan.eatPellet(new Maze());  // Example assuming eatPellet method accepts Maze
        }
    }
}
