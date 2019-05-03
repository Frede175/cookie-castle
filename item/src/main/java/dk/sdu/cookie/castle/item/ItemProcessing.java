package dk.sdu.cookie.castle.item;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.CollisionPart;
import dk.sdu.cookie.castle.common.data.Entityparts.LifePart;
import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.item.Item;
import dk.sdu.cookie.castle.common.services.IEntityProcessingService;

public class ItemProcessing implements IEntityProcessingService {

    private float angle = 0;
    private float numPoints = 6;
    private float radians = 3.1415f / 2 + (float) Math.random();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity item : world.getEntities(Item.class)) {
            PositionPart positionPart = item.getPart(PositionPart.class);
//            LifePart lifePart = item.getPart(LifePart.class);
            CollisionPart collisionPart = item.getPart(CollisionPart.class);

            if (collisionPart.getHit() && collisionPart.getCollidingEntity().getEntityType() == EntityType.PLAYER) {
                world.removeEntity(item);
            }

            positionPart.process(gameData, item);

//            if (lifePart != null) {
//                lifePart.process(gameData, item);
//            }

            updateShape(item);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapeX = entity.getShapeX();
        float[] shapeY = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

        for (int i = 0; i < numPoints; i++) {
            shapeX[i] = x + (float) Math.cos(angle + radians) * 26;
            shapeY[i] = y + (float) Math.sin(angle + radians) * 26;
            angle += 2 * 3.1415f / numPoints;
        }

        entity.setShapeX(shapeX);
        entity.setShapeY(shapeY);
    }
}

