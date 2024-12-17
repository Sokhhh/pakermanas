package Flyweight;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PelletFactory {
    private static final Map<String, Pellet> pelletCache = new HashMap<>();

    // Modify the getPellet method to accept x and y coordinates
    public static Pellet getPellet(String type, int x, int y) {
        Pellet pellet = pelletCache.get(type);

        // If the pellet of the requested type doesn't exist in the cache, create it
        if (pellet == null) {
            switch (type) {
                case "regular":
                    pellet = new Pellet(Color.WHITE, 6, x, y);  // Create a regular pellet at (x, y)
                    break;
                case "invincibility":
                    pellet = new Pellet(Color.CYAN, 12, x, y);  // Create an invincibility pellet at (x, y)
                    break;
                case "doublePoints":
                    pellet = new Pellet(Color.YELLOW, 12, x, y);  // Create a doublePoints pellet at (x, y)
                    break;
                case "teleporter":
                    pellet = new Pellet(Color.WHITE, 12, x, y);  // Create a teleporter pellet at (x, y)
                    break;
                default:
                    throw new IllegalArgumentException("Unknown pellet type: " + type);
            }
            // After creating the pellet, add it to the cache
            pelletCache.put(type, pellet);
        }

        // Return the cached pellet with the updated position
        return pellet;
    }
}
