package tmp_test;

import Flyweight.Pellet;
import Flyweight.PelletFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FlyweightTest {

    @Test
    public void testPelletReuse() {
        // Retrieve pellets of the same type
        Pellet regularPellet1 = PelletFactory.getPellet("regular");
        Pellet regularPellet2 = PelletFactory.getPellet("regular");

        Pellet invincibilityPellet1 = PelletFactory.getPellet("invincibility");
        Pellet invincibilityPellet2 = PelletFactory.getPellet("invincibility");

        // Assert that the same object is reused for the same type
        assertSame(regularPellet1, regularPellet2, "Regular pellets are not reused!");
        assertSame(invincibilityPellet1, invincibilityPellet2, "Invincibility pellets are not reused!");

        // Assert that different types are distinct objects
        assertNotSame(regularPellet1, invincibilityPellet1, "Different pellet types should not share the same instance!");
    }
}
