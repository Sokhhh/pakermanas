package AbstractFactory;

import game.Maze;

import java.awt.*;

public interface IPacMan {
    void move(Maze maze);
    int getX();
    int getY();
    void setDirection(int dx, int dy);
    void setPosition(int x, int y);
    void render(Graphics g);
}
