package dk.sdu.cookie.castle.collision;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;

public class AB {

    private Entity entity;
    private float[] minPoint;
    private float[] maxPoint;

    public AB(Entity entity) {
        this.entity = entity;
        calcMinMax();

    }

    public void calcMinMax() {
        PositionPart part = entity.getPart(PositionPart.class);
        if(part != null) {

            minPoint[0] = (part.getX() - entity.getRadius());
            minPoint[1] = (part.getY() - entity.getRadius());

            maxPoint[0] = (part.getX() + entity.getRadius());
            maxPoint[1] = (part.getY() + entity.getRadius());
        }

    }

    public float[] getMinPoint() {
        return minPoint;
    }

    public float[] getMaxPoint() {
        return maxPoint;
    }
}
