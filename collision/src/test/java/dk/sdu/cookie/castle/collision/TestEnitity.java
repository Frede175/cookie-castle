package dk.sdu.cookie.castle.collision;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;
import dk.sdu.cookie.castle.common.data.ICollidable;

public class TestEnitity extends Entity {

    public TestEnitity(float x, float y, float radius) {
        add(new PositionPart(x,y));
        setRadius(radius);
    }
}
