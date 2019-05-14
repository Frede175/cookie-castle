package dk.sdu.cookie.castle.enemy;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.*;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.enemy.Enemy;
import dk.sdu.cookie.castle.common.enemy.EnemyType;
import dk.sdu.cookie.castle.common.services.IEntityProcessingService;
import dk.sdu.cookie.castle.common.services.ILineOfSightService;
import dk.sdu.cookie.castle.common.util.Vector2f;

public class EnemyProcessing implements IEntityProcessingService {

    private static ILineOfSightService lineOfSightService;

    /**
     * Process method for the Enemy. This method checks for collisionpart, and then afterwards updates all the
     * other parts the Enemy has depending on its EnemyType. Lastly it updates its shape, with its new position
     *
     * @param gameData The Gamedata
     * @param world    The World
     */
    @Override
    public void process(GameData gameData, World world) {

        PositionPart playerPositionPart = null;

        for (Entity player : world.getEntities()) {
            if (!player.isActive()) continue;

            if (player.getEntityType() == EntityType.PLAYER) {
                playerPositionPart = player.getPart(PositionPart.class);
                break;
            }
        }

        for (Entity enemy : world.getEntities(Enemy.class)) {
            if (!enemy.isActive()) continue;

            PositionPart positionPart = enemy.getPart(PositionPart.class);
            AIMovingPart aiMovingPart = enemy.getPart(AIMovingPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);
            CollisionPart collisionPart = enemy.getPart(CollisionPart.class);
            ShootingPart shootingPart = enemy.getPart(ShootingPart.class);
            InventoryPart inventoryPart = enemy.getPart(InventoryPart.class);
            WeaponPart weaponPart = null;
            if (inventoryPart != null) {
                weaponPart = inventoryPart.getCurrentWeapon().getWeapon();
            }


            if (collisionPart.getIsHit()) {
                switch (collisionPart.getCollidingEntity().getEntityType()) {
                    case PLAYER:
                        break;
                    case PLAYER_BULLET:
                        DamagePart damagePart = collisionPart.getCollidingEntity().getPart(DamagePart.class);
                        lifePart.setHealth(lifePart.getHealth() - damagePart.getDamage());
                        break;
                }
                collisionPart.setIsHit(false);
            }

            if (lineOfSightService != null && playerPositionPart != null && weaponPart != null) {
                Vector2f playerPosition = new Vector2f(playerPositionPart.getX(), playerPositionPart.getY());
                Vector2f enemyPosition = new Vector2f(positionPart.getX(), positionPart.getY());
                if (lineOfSightService.isInlineOfSight(world, enemyPosition, playerPosition)) {
                    if (weaponPart.getRange() >= enemyPosition.distance(playerPosition) + 10) {
                        aiMovingPart.setShouldMove(false);
                        Vector2f delta = playerPosition.subtract(enemyPosition);
                        positionPart.setRadians((float) Math.atan2(delta.getY(), delta.getX()));
                        shootingPart.setShooting(true);
                    } else {
                        aiMovingPart.setShouldMove(true);
                    }
                } else {
                    aiMovingPart.setShouldMove(true);
                }
            }

            aiMovingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);
            lifePart.process(gameData, enemy);

            if (lifePart.isDead()) {
                world.removeEntity(enemy);
            }
            if (((Enemy) enemy).getEnemyType() == EnemyType.RANGED) {
                inventoryPart.process(gameData, enemy);
                shootingPart.updateShootingSpeed(weaponPart.getAttackSpeed());
                shootingPart.process(gameData, enemy);
            }

            updateShape(enemy);
        }
    }

    public void installLineOfSight(ILineOfSightService iLineOfSightService) {
        lineOfSightService = iLineOfSightService;
    }

    public void uninstallLineOfSight(ILineOfSightService lineOfSightService) {
        this.lineOfSightService = null;
    }

    /**
     * This method calculates the position and shape of the entity, based on the entitys x, y and radius.
     * Then updates the ShapeX and ShapeY
     *
     * @param entity The entity, which shape is about to be updated
     */
    private void updateShape(Entity entity) {
        float radius = 20;
        float angle = 0;
        float[] shapeX = entity.getShapeX();
        float[] shapeY = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        for (int i = 0; i < shapeX.length; i++) {
            shapeX[i] = (float) (x + Math.cos(radians - angle) * radius);
            shapeY[i] = (float) (y + Math.sin(radians - angle) * radius);
            angle += Math.PI / 4.5;
        }

        entity.setShapeX(shapeX);
        entity.setShapeY(shapeY);
        entity.updateMinMax();
    }
}
