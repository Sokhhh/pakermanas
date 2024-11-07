package AbstractFactory;

import Factory.Vaiduoklis;

public interface AbstractEntityFactory {
    IPacMan createPacMan();
    Vaiduoklis createVaiduoklis(String type, int startX, int startY);
}
