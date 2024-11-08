package AbstractFactory;
import Factory.Vaiduoklis;
import Factory.VaiduoklisFactory;

public class SPEntityFactory implements AbstractEntityFactory {
    private VaiduoklisFactory vaiduoklisFactory = new VaiduoklisFactory();
    SPPacMan PacManPrototype = new SPPacMan(17,19);
    @Override
    public IPacMan createPacMan() {
        return PacManPrototype.clone();
    }

    @Override
    public Vaiduoklis createVaiduoklis(String type, int startX, int startY) {
        return vaiduoklisFactory.createVaiduoklis(type, startX, startY);
    }
}
