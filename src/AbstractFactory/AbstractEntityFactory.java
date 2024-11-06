package AbstractFactory;

import Factory.Vaiduoklis;

public interface AbstractEntityFactory {
    IPacMan createPacMan(int startX, int startY);
    Vaiduoklis createVaiduoklis(String type, int startX, int startY);
}
