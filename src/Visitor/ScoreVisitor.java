package Visitor;

import AbstractFactory.IPacMan;
import Factory.Vaiduoklis;
import Flyweight.Pellet;
import game.Maze;
import game.ScoreCounterSingleton;

public class ScoreVisitor implements Visitor {
    private IPacMan pacMan;  // PacMan instance to update the score

    public ScoreVisitor(IPacMan pacMan) {
        this.pacMan = pacMan;
    }

    @Override
    public void visit(IPacMan pacMan) {
        // No score logic needed for PacMan itself
    }

    @Override
    public void visit(Vaiduoklis ghost) {
        // No score update for collisions with ghosts
    }

    @Override
    public void visit(Pellet pellet) {
        // Check if PacMan eats a pellet
        if (pacMan.getX() == pellet.getX() && pacMan.getY() == pellet.getY()) {
            // Get the ScoreCounter instance
            ScoreCounterSingleton scoreCounter = ScoreCounterSingleton.getInstance();

            // Handle scoring when PacMan eats a pellet
            switch (pellet.getType()) {
                case "regular":
                    System.out.println("PacMan ate a regular Pellet!");
                    break;
                case "invincibility":
                    System.out.println("PacMan ate an Invincibility Pellet!");
                    scoreCounter.addScore(10);  // Power-up pellet, higher points
                    break;
                case "doublePoints":
                    System.out.println("PacMan ate a Double Points Pellet!");
                    scoreCounter.addScore(25);  // Double points pellet
                    break;
                case "teleporter":
                    System.out.println("PacMan ate a Teleporter Pellet!");
                    scoreCounter.addScore(50);  // Teleporter pellet
                    break;
                default:
                    System.out.println("Unknown Pellet type encountered.");
            }
        }
    }
}
