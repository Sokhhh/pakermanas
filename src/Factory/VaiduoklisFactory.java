// VaiduoklisFactory.java
package Factory;

public class VaiduoklisFactory {
    public static Vaiduoklis createVaiduoklis(String type, int startX, int startY) {
        switch (type) {
            case "Aggressive":
                return new AggressiveVaiduoklis(startX, startY);
            case "Random":
                return new RandomVaiduoklis(startX, startY);
            case "Cautious":
                return new CautiousVaiduoklis(startX, startY);
            default:
                throw new IllegalArgumentException("Unknown ghost type: " + type);
        }
    }
}
