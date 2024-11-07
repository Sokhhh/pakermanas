package AbstractFactory;

import Factory.Vaiduoklis;

public abstract class AbstractEntityFactory {
    public abstract IPacMan createPacMan();
    public abstract Vaiduoklis createVaiduoklis(String type, int startX, int startY);
}
