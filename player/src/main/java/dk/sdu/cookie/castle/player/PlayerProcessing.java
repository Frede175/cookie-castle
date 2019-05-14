package dk.sdu.cookie.castle.player;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.Entityparts.*;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.GameKeys;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IEntityProcessingService;

import java.nio.file.WatchEvent;

public class PlayerProcessing implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {

        for (Entity player : world.getEntities(Player.class)) {
            if (!player.isActive()) continue;

            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            LifePart lifePart = player.getPart(LifePart.class);
            CollisionPart collisionPart = player.getPart(CollisionPart.class);
            InventoryPart inventoryPart = player.getPart(InventoryPart.class);
            WeaponPart weaponPart = inventoryPart.getCurrentWeapon().getWeapon();
            ShootingPart shootingPart = player.getPart(ShootingPart.class);

            movingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            movingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(GameKeys.UP));
            movingPart.setDown(gameData.getKeys().isDown(GameKeys.DOWN));
            shootingPart.setShooting(gameData.getKeys().isDown(GameKeys.SPACE));

            //Collision check
            //Take damage if collision is with bullet
            //Pick up item if the collision is with an entity of type "item"
            if (collisionPart.getIsHit()) {
                switch (collisionPart.getCollidingEntity().getEntityType()) {
                    case ENEMY:
                        if (collisionPart.getCollidingEntity().getPart(DamagePart.class) != null) {
                            DamagePart enemyDamagePart = collisionPart.getCollidingEntity().getPart(DamagePart.class);
                            lifePart.setHealth(lifePart.getHealth() - enemyDamagePart.getDamage());
                        }
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

            //Processing all the player parts (keeping them updated as the game runs)
            lifePart.process(gameData, player);
            if (lifePart.isDead()) {
                world.removeEntity(player);
            }

            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
            inventoryPart.process(gameData, player);
            shootingPart.updateShootingSpeed(weaponPart.getAttackSpeed());
            shootingPart.process(gameData, player);

            updateShape(player);
        }
    }

    /**
     * Updates the graphical aspect of the players position
     * @param entity
     */
    private void updateShape(Entity entity) {
        float radius = 25;
        float angle = 0f;
        float[] shapeX = entity.getShapeX();
        float[] shapeY = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        for (int i = 0; i < shapeX.length; i++) {
            if ( i == 0) {
                shapeX[i] = (float) (x + Math.cos(radians - angle) * (radius + 10));
                shapeY[i] = (float) (y + Math.sin(radians - angle) * (radius + 10));
            } else {
                shapeX[i] = (float) (x + Math.cos(radians - angle) * radius);
                shapeY[i] = (float) (y + Math.sin(radians - angle) * radius);
            }
            angle += Math.PI / 4.5;
        }

        entity.setShapeX(shapeX);
        entity.setShapeY(shapeY);
        entity.updateMinMax();
    }
}
