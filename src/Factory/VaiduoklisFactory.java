// VaiduoklisFactory.java
package Factory;

import Strategy.AggressiveMovement;
import Strategy.CautiousMovement;
import Strategy.RandomMovement;

public class VaiduoklisFactory {
    public static Vaiduoklis createVaiduoklis(String type, int startX, int startY) {
        switch (type) {
            case "Aggressive":
                return new AggressiveVaiduoklis(startX, startY, new AggressiveMovement());
            case "Random":
                return new RandomVaiduoklis(startX, startY, new RandomMovement());
            case "Cautious":
                return new CautiousVaiduoklis(startX, startY, new CautiousMovement());
            default:
                throw new IllegalArgumentException("Unknown ghost type: " + type);
        }
    }
}
