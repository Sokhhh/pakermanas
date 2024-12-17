package Visitor;

import AbstractFactory.IPacMan;
import Factory.Vaiduoklis;
import Flyweight.Pellet;

public interface Visitor {
    void visit(IPacMan pacMan);
    void visit(Vaiduoklis ghost);
    void visit(Pellet pellet);
}
