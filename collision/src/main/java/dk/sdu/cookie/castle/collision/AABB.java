package dk.sdu.cookie.castle.collision;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;

class AABB {

    private Entity entity;
    private float[] minPoint;
    private float[] maxPoint;

    AABB(Entity entity) {
        this.entity = entity;
        minPoint = new float[2];
        maxPoint = new float[2];
        calcMinMax();
    }

    private void calcMinMax() {
        PositionPart part = entity.getPart(PositionPart.class);
        if (part != null) {

            minPoint[0] = entity.getMin().getX();
            minPoint[1] = entity.getMin().getY();

            maxPoint[0] = entity.getMax().getX();
            maxPoint[1] = entity.getMax().getY();
        }

    }

    Entity getEntity() {
        return entity;
    }

    float[] getMinPoint() {
        return minPoint;
    }

    float[] getMaxPoint() {
        return maxPoint;
    }
}
