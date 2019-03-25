package dk.sdu.cookie.castle.collision;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.Entityparts.CollisionPart;
import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;

public class TestEntity extends Entity {

    public TestEntity(float x, float y, float radius) {
        add(new CollisionPart());
        add(new PositionPart(x,y));
        setRadius(radius);
    }
}
