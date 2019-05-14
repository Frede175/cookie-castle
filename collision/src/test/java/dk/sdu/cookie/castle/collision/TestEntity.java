package dk.sdu.cookie.castle.collision;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.CollisionPart;
import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;

public class TestEntity extends Entity {

    public TestEntity(float x, float y, float radius, EntityType tag) {
        add(new CollisionPart());
        add(new PositionPart(x, y, 0.0f));

        float[] shapeX = {x, x + radius, x, x - radius};
        float[] shapeY = {y + radius, y, y - radius, y};
        setShapeX(shapeX);
        setShapeY(shapeY);
        updateMinMax();

        setIsActive(true);
        setEntityType(tag);
    }
}
