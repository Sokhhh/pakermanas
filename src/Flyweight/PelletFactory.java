package Flyweight;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PelletFactory {
    private static final Map<String, Pellet> pelletCache = new HashMap<>();

    public static Pellet getPellet(String type) {
        if (!pelletCache.containsKey(type)) {
            switch (type) {
                case "regular":
                    pelletCache.put(type, new Pellet(Color.WHITE, 6));
                    break;
                case "invincibility":
                    pelletCache.put(type, new Pellet(Color.CYAN, 12));
                    break;
                case "doublePoints":
                    pelletCache.put(type, new Pellet(Color.YELLOW, 12));
                    break;
                case "teleporter":
                    pelletCache.put(type, new Pellet(Color.WHITE, 12));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown pellet type: " + type);
            }
        }
        return pelletCache.get(type);
    }
}
