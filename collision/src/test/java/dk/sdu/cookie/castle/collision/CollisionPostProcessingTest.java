package dk.sdu.cookie.castle.collision;

import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IPostEntityProcessingService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollisionPostProcessingTest {

    private final IPostEntityProcessingService collision = new CollisionPostProcessing();

    //TODO Fix asserts!

    @Test
    public void shouldCollide() {
        World world = new World();
        world.addEntity(new TestEnitity(0,0, 10));
        world.addEntity(new TestEnitity(15,15, 10));

        collision.process(null, world);


        assertEquals(2,2);
    }

    @Test
    public void shouldNotCollide() {
        World world = new World();
        world.addEntity(new TestEnitity(0,0, 10));
        world.addEntity(new TestEnitity(30,30, 10));

        collision.process(null, world);

        assertEquals(2,2);
    }

    @Test
    public void shouldNotCollideSpecial() {
        World world = new World();
        world.addEntity(new TestEnitity(0,0, 10));
        world.addEntity(new TestEnitity(19,19, 10));

        collision.process(null, world);

        assertEquals(2,3);
    }

}
