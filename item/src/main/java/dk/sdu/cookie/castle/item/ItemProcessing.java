package dk.sdu.cookie.castle.item;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.Entityparts.CollisionPart;
import dk.sdu.cookie.castle.common.data.Entityparts.ItemPart;
import dk.sdu.cookie.castle.common.data.Entityparts.LifePart;
import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IEntityProcessingService;

public class ItemProcessing implements IEntityProcessingService {

    private float angle = 0;
    private float numPoints = 6;
    private float radians = 3.1415f / 2 + (float) Math.random();
    @Override
    public void process(GameData gameData, World world) {
        for (Entity item : world.getEntities(Item.class)) {
            PositionPart positionPart = item.getPart(PositionPart.class);
            LifePart lifePart = item.getPart(LifePart.class);
            CollisionPart collisionPart = item.getPart(CollisionPart.class);

            if (collisionPart.getHit()) {
                switch (collisionPart.getCollidingEntity().getEntityType()) {
                    case PLAYER:
                        // world.removeEntity(item);
                        break;
                    case ENEMY:
                        break;
                    case STATIC_OBSTACLE:
                        break;
                    case REMOVABLE_OBSTACLE:
                        break;
                    case PLAYER_BULLET:
                        break;
                    case ENEMY_BULLET:
                        break;
                    case WALL:
                        break;
                    case DOOR:
                        break;
                    case ITEM:
                }
            }

            positionPart.process(gameData, item);
            lifePart.process(gameData, item);

            updateShape(item);

        }
    }

            private void updateShape (Entity entity){
                float[] shapex = entity.getShapeX();
                float[] shapey = entity.getShapeY();
                PositionPart positionPart = entity.getPart(PositionPart.class);
                float x = positionPart.getX();
                float y = positionPart.getY();

                    for (int i = 0; i < numPoints; i++) {
                        shapex[i] = x + (float) Math.cos(angle + radians) * 25;
                        shapey[i] = y + (float) Math.sin(angle + radians) * 25;
                        angle += 2 * 3.1415f / numPoints;
                    }

                entity.setShapeX(shapex);
                entity.setShapeY(shapey);

                }

}

