package AbstractFactory;
import Factory.Vaiduoklis;
import Factory.VaiduoklisFactory;

public class SPEntityFactory implements AbstractEntityFactory {
    private final VaiduoklisFactory vaiduoklisFactory = new VaiduoklisFactory();
    SPPacMan PacManPrototype = new SPPacMan(12,19);
    @Override
    public IPacMan createPacMan() {
        return PacManPrototype.clone();
    }

    @Override
    public Vaiduoklis createVaiduoklis(String type, int startX, int startY) {
        return VaiduoklisFactory.createVaiduoklis(type, startX, startY);
    }
}
