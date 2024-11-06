package AbstractFactory;
import Factory.Vaiduoklis;
import Factory.VaiduoklisFactory;

public class MPEntityFactory implements AbstractEntityFactory {
    private VaiduoklisFactory vaiduoklisFactory = new VaiduoklisFactory();

    @Override
    public IPacMan createPacMan(int startX, int startY) {
        return new MPPacMan(startX,startY);
    }

    @Override
    public Vaiduoklis createVaiduoklis(String type, int startX, int startY) {
        return new MPGhost(startX,startY);
    }
}
