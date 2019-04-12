package dk.sdu.cookie.castle.raycasting;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.ILineOfSightService;
import dk.sdu.cookie.castle.common.util.Vector2f;

import java.awt.geom.Line2D;


public class Raycasting implements ILineOfSightService {

    /**
     * {@inheritDoc}
     * 
     * Only checks the entities with the entitytypes: STATIC_OBSTACLE, REMOVABLE_OBSTACLE and WALL for line of sight.
     */
    @Override
    public boolean lineOfSight(World world, Vector2f start, Vector2f end) {
        Line2D rayline = new Line2D.Float(start.getX(), start.getY(), end.getX(), end.getY());

        for (Entity entity : world.getEntities()) {

            if (entity.getEntityType() != EntityType.STATIC_OBSTACLE && entity.getEntityType() != EntityType.REMOVABLE_OBSTACLE && entity.getEntityType() != EntityType.WALL) {
                continue;
            }

            for (int i = 0; i < entity.getShapeX().length; i++) {
                float x1 = entity.getShapeX()[i];
                float y1 = entity.getShapeY()[i];
                float x2 = entity.getShapeX()[(i + 1) % entity.getShapeX().length];
                float y2 = entity.getShapeY()[(i + 1) % entity.getShapeX().length];

                Line2D newRayline = new Line2D.Float(x1, y1, x2, y2);

                if (newRayline.intersectsLine(rayline)) {
                    return false;
                }
            }
        }
        return true;
    }
}
