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
            // Create a new Pellet with its specific type
            switch (type) {
                case "regular":
                    pellet = new Pellet(Color.WHITE, 6, x, y, "regular");  // Create a regular pellet at (x, y)
                    break;
                case "invincibility":
                    pellet = new Pellet(Color.CYAN, 12, x, y, "invincibility");  // Create an invincibility pellet at (x, y)
                    break;
                case "doublePoints":
                    pellet = new Pellet(Color.YELLOW, 12, x, y, "doublePoints");  // Create a doublePoints pellet at (x, y)
                    break;
                case "teleporter":
                    pellet = new Pellet(Color.WHITE, 12, x, y, "teleporter");  // Create a teleporter pellet at (x, y)
                    break;
                default:
                    throw new IllegalArgumentException("Unknown pellet type: " + type);
            }
            // After creating the pellet, add it to the cache with its type and position
            pelletCache.put(type + "-" + x + "-" + y, pellet);  // Cache key is now based on type and position
        } else {
            // If the pellet exists, update its position in the factory
            // New Pellet objects will be created at specific x, y positions.
            // This is necessary to avoid caching errors because Pellet objects are position-specific.
            pellet = new Pellet(pellet.getColor(), pellet.getSize(), x, y, pellet.getType());
        }

        // Return the pellet with the updated position
        return pellet;
    }
}
