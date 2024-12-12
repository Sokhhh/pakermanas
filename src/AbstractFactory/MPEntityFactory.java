package AbstractFactory;
import Factory.Vaiduoklis;
import Factory.VaiduoklisFactory;

public class MPEntityFactory implements AbstractEntityFactory {
    private final VaiduoklisFactory vaiduoklisFactory = new VaiduoklisFactory();
    MPPacMan PacManPrototype = new MPPacMan(11,21);
    @Override
    public IPacMan createPacMan() {
        return PacManPrototype.clone();
    }

    @Override
    public Vaiduoklis createVaiduoklis(String type, int startX, int startY) {
        if(type == "Zaidejas")
        {
            return new MPGhost(startX,startY);
        }
        else
        {
            return VaiduoklisFactory.createVaiduoklis(type, startX, startY);
        }
    }
}
