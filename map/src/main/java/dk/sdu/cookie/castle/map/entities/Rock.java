package dk.sdu.cookie.castle.map.entities;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.CollisionPart;
import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;

//Rock class, static obstacle
public class Rock extends Entity {
    public Rock(float x, float y) {
        float radians = 3.1415f / 2;

        add(new PositionPart(x, y, radians));
        add(new CollisionPart());
        setEntityType(EntityType.STATIC_OBSTACLE);

        setShapeX(new float[6]);
        setShapeY(new float[6]);
    }
}
