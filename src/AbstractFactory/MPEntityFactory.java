package AbstractFactory;
import Factory.Vaiduoklis;
import Factory.VaiduoklisFactory;

public class MPEntityFactory extends AbstractEntityFactory {
    MPPacMan PacManPrototype = new MPPacMan(11,21);
    @Override
    public IPacMan createPacMan() {
        return PacManPrototype.clone();
    }

    @Override
    public Vaiduoklis createVaiduoklis(String type, int startX, int startY) {
        return new MPGhost(startX,startY);
    }
}
