package dk.sdu.cookie.castle.collision;

import dk.sdu.cookie.castle.common.data.Entityparts.CollisionPart;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IPostEntityProcessingService;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollisionPostProcessingTest {

    private final IPostEntityProcessingService collision = new CollisionPostProcessing();

    //TODO Fix asserts!

    @Test
    public void shouldCollide() {
        World world = new World();

        TestEntity testEntity1 = new  TestEntity(0,0, 10);
        TestEntity testEntity2 = new  TestEntity(15,15, 10);
        world.addEntity(testEntity1);
        world.addEntity(testEntity2);

        collision.process(null, world);

        CollisionPart collisionPart1 = testEntity1.getPart(CollisionPart.class);
        CollisionPart collisionPart2 = testEntity2.getPart(CollisionPart.class);

        assertEquals(true, collisionPart1.getHit());
        assertEquals(true, collisionPart2.getHit());
        assertEquals(testEntity1.getID(), collisionPart2.getCollidingEntity().getID());
        assertEquals(testEntity2.getID(), collisionPart1.getCollidingEntity().getID());

    }

    @Test
    public void shouldNotCollide() {
        World world = new World();

        TestEntity testEntity1 = new  TestEntity(0,0, 10);
        TestEntity testEntity2 = new  TestEntity(30,30, 10);
        world.addEntity(testEntity1);
        world.addEntity(testEntity2);

        collision.process(null, world);

        CollisionPart collisionPart1 = testEntity1.getPart(CollisionPart.class);
        CollisionPart collisionPart2 = testEntity2.getPart(CollisionPart.class);

        assertEquals(false, collisionPart1.getHit());
        assertEquals(false, collisionPart2.getHit());

    }

    @Test
    public void shouldNotCollideSpecial() {
        World world = new World();

        TestEntity testEntity1 = new  TestEntity(0,0, 10);
        TestEntity testEntity2 = new  TestEntity(19,19, 10);
        world.addEntity(testEntity1);
        world.addEntity(testEntity2);

        collision.process(null, world);

        CollisionPart collisionPart1 = testEntity1.getPart(CollisionPart.class);
        CollisionPart collisionPart2 = testEntity2.getPart(CollisionPart.class);

        assertEquals(false, collisionPart1.getHit());
        assertEquals(false, collisionPart2.getHit());

    }

}
