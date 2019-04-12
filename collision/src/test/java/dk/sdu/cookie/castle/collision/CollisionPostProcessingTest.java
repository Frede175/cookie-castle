package dk.sdu.cookie.castle.collision;

import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.CollisionPart;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IPostEntityProcessingService;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CollisionPostProcessingTest {

    private final IPostEntityProcessingService collision = new CollisionPostProcessing();

    //TODO Fix asserts!

    @Test
    public void shouldCollide() {
        World world = new World();

        TestEntity testEntity1 = new TestEntity(0, 0, 10, EntityType.PLAYER);
        TestEntity testEntity2 = new TestEntity(7, 7, 10, EntityType.STATIC_OBSTACLE);
        world.addEntity(testEntity1);
        world.addEntity(testEntity2);

        collision.process(null, world);

        CollisionPart collisionPart1 = testEntity1.getPart(CollisionPart.class);
        CollisionPart collisionPart2 = testEntity2.getPart(CollisionPart.class);

        assertTrue(collisionPart1.getHit());
        assertTrue(collisionPart2.getHit());
        assertEquals(testEntity1.getID(), collisionPart2.getCollidingEntity().getID());
        assertEquals(testEntity2.getID(), collisionPart1.getCollidingEntity().getID());

    }

    @Test
    public void shouldNotCollide() {
        World world = new World();

        TestEntity testEntity1 = new TestEntity(0, 0, 10, EntityType.PLAYER);
        TestEntity testEntity2 = new TestEntity(30, 30, 10, EntityType.STATIC_OBSTACLE);
        world.addEntity(testEntity1);
        world.addEntity(testEntity2);

        collision.process(null, world);

        CollisionPart collisionPart1 = testEntity1.getPart(CollisionPart.class);
        CollisionPart collisionPart2 = testEntity2.getPart(CollisionPart.class);

        assertFalse(collisionPart1.getHit());
        assertFalse(collisionPart2.getHit());

    }

    @Test
    public void shouldNotCollideSpecial() {
        World world = new World();

        TestEntity testEntity1 = new TestEntity(0, 0, 10, EntityType.PLAYER);
        TestEntity testEntity2 = new TestEntity(19, 19, 10, EntityType.STATIC_OBSTACLE);
        world.addEntity(testEntity1);
        world.addEntity(testEntity2);

        collision.process(null, world);

        CollisionPart collisionPart1 = testEntity1.getPart(CollisionPart.class);
        CollisionPart collisionPart2 = testEntity2.getPart(CollisionPart.class);

        assertFalse(collisionPart1.getHit());
        assertFalse(collisionPart2.getHit());

    }

}
