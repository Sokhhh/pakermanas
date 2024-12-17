package Decorator;

import AbstractFactory.IPacMan;
import Visitor.Visitor;
import game.Maze;
import java.awt.*;

public abstract class PacManDecorator implements IPacMan {
    public IPacMan decoratedPacMan;

    public PacManDecorator(IPacMan decoratedPacMan) {
        this.decoratedPacMan = decoratedPacMan;
    }

    @Override
    public void move(Maze maze) {
        decoratedPacMan.move(maze);
    }

    @Override
    public void eatPellet(Maze maze) {
        decoratedPacMan.eatPellet(maze);
    }

    @Override
    public int getX() {
        return decoratedPacMan.getX();
    }

    @Override
    public int getY() {
        return decoratedPacMan.getY();
    }

    @Override
    public void setDirection(int dx, int dy) {
        decoratedPacMan.setDirection(dx, dy);
    }

    @Override
    public void setPosition(int x, int y) {
        decoratedPacMan.setPosition(x, y);
    }

    @Override
    public int getDx() {
        return decoratedPacMan.getDx();
    }

    @Override
    public int getDy() {
        return decoratedPacMan.getDy();
    }

    @Override
    public void render(Graphics g) {
        decoratedPacMan.render(g);
    }

    // Method for accepting the visitor
    public void accept(Visitor visitor) {
        visitor.visit(this);  // Let the visitor operate on this DoublePointDecorator
    }
}
