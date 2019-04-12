package dk.sdu.cookie.castle.raycasting;

import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.util.Vector2f;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class RaycastingTest {

    /**
     * Tests if the two entities testEntity1 and testEntity2 intersects with the line from p(50,50) to p(250,250)
     * Should return false.
     */
    @Test
    public void shouldIntersect() {
        World world = new World();

        TestEntity testEntity1 = new TestEntity(200, 200, 10, EntityType.STATIC_OBSTACLE);
        TestEntity testEntity2 = new TestEntity(100, 100, 10, EntityType.STATIC_OBSTACLE);
        world.addEntity(testEntity1);
        world.addEntity(testEntity2);

        Raycasting raycasting = new Raycasting();
        boolean result = raycasting.lineOfSight(world, new Vector2f(50, 50), new Vector2f(250, 250));
        assertFalse(result);
    }

    /**
     * Tests if the two entities testEntity1 and testEntity2 intersects with the line from p(350,350) to p(400,400)
     * Should return true.
     */
    @Test
    public void shouldNotIntersect() {
        World world = new World();

        TestEntity testEntity1 = new TestEntity(200, 200, 10, EntityType.STATIC_OBSTACLE);
        TestEntity testEntity2 = new TestEntity(100, 100, 10, EntityType.STATIC_OBSTACLE);
        world.addEntity(testEntity1);
        world.addEntity(testEntity2);

        Raycasting raycasting = new Raycasting();
        boolean result = raycasting.lineOfSight(world, new Vector2f(350, 350), new Vector2f(400, 400));
        assertTrue(result);
    }

    /**
     * Tests if the two entities testEntity1 and testEntity2 intersects with the line from p(100,200) to p(250,100)
     * Should return false.
     */
    @Test
    public void shouldNotIntersectLineBetween() {
        World world = new World();

        TestEntity testEntity1 = new TestEntity(200, 200, 10, EntityType.STATIC_OBSTACLE);
        TestEntity testEntity2 = new TestEntity(100, 100, 10, EntityType.STATIC_OBSTACLE);
        world.addEntity(testEntity1);
        world.addEntity(testEntity2);

        Raycasting raycasting = new Raycasting();
        boolean result = raycasting.lineOfSight(world, new Vector2f(100, 250), new Vector2f(250, 100));
        assertTrue(result);
    }
}
