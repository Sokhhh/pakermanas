package AbstractFactory;
import Factory.Vaiduoklis;
import Factory.VaiduoklisFactory;

public class SPEntityFactory implements AbstractEntityFactory {
    private VaiduoklisFactory vaiduoklisFactory = new VaiduoklisFactory();

    @Override
    public IPacMan createPacMan(int startX, int startY) {
        return new SPPacMan(startX,startY);
    }

    @Override
    public Vaiduoklis createVaiduoklis(String type, int startX, int startY) {
        return vaiduoklisFactory.createVaiduoklis(type, startX, startY);
    }
}
