package dk.sdu.cookie.castle.player;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.Entityparts.*;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.GameKeys;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IEntityProcessingService;

public class PlayerProcessing implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {

        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            LifePart lifePart = player.getPart(LifePart.class);
            CollisionPart collisionPart = player.getPart(CollisionPart.class);
            InventoryPart inventoryPart = player.getPart(InventoryPart.class);
            ShootingPart shootingPart = player.getPart(ShootingPart.class);

            movingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            movingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(GameKeys.UP));
            movingPart.setDown(gameData.getKeys().isDown(GameKeys.DOWN));
            shootingPart.setShooting(gameData.getKeys().isDown(GameKeys.SPACE));

            if (collisionPart.getHit()) {
                switch (collisionPart.getCollidingEntity().getEntityType()) {
                    case ENEMY:
                        break;
                    case ENEMY_BULLET:
                        DamagePart damagePart = collisionPart.getCollidingEntity().getPart(DamagePart.class);
                        lifePart.setHealth(lifePart.getHealth() - damagePart.getDamage());
                        break;
                    case DOOR:
                        break;
                    case ITEM:
                        ItemPart itemPart = collisionPart.getCollidingEntity().getPart(ItemPart.class);
                        inventoryPart.addItem(itemPart);
                }
                collisionPart.setIsHit(false);
            }

            lifePart.process(gameData, player);
            if (lifePart.isDead()) {
                world.removeEntity(player);
            }

            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
            inventoryPart.process(gameData, player);
            shootingPart.process(gameData, player);

            updateShape(player);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapeX = entity.getShapeX();
        float[] shapeY = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapeX[0] = (float) (x + Math.cos(radians) * entity.getRadius());
        shapeY[0] = (float) (y + Math.sin(radians) * entity.getRadius());

        shapeX[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * entity.getRadius());
        shapeY[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * entity.getRadius());

        shapeX[2] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * entity.getRadius());
        shapeY[2] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * entity.getRadius());

        entity.setShapeX(shapeX);
        entity.setShapeY(shapeY);
    }
}
