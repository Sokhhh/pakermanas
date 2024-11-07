package AbstractFactory;

import game.Maze;

import java.awt.*;

public interface IPacMan {
    void move(Maze maze);
    void eatPellet(Maze maze);

    int getX();
    int getY();

    void setDirection(int dx, int dy);
    void setPosition(int x, int y);

    int getDx();
    int getDy();

    void render(Graphics g);
}
