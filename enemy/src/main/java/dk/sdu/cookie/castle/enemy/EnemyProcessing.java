package dk.sdu.cookie.castle.enemy;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.*;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IEntityProcessingService;
import dk.sdu.cookie.castle.common.services.ILineOfSightService;
import dk.sdu.cookie.castle.common.util.Vector2f;

public class EnemyProcessing implements IEntityProcessingService {

    private static ILineOfSightService lineOfSightService;
    private PositionPart playerPositionPart;


    /**
     * Process method for the Enemy. This method checks for collisionpart, and then afterwards updates all the
     * other parts the Enemy has. Lastly it updates its shape, with its new position
     *
     * @param gameData The Gamedata
     * @param world    The World
     */
    @Override
    public void process(GameData gameData, World world) {

        PositionPart playerPositionPart = null;

        for (Entity player : world.getEntities()) {
            if (player.getEntityType() == EntityType.PLAYER) {
                playerPositionPart = player.getPart(PositionPart.class);
            }
        }

        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            AIMovingPart aiMovingPart = enemy.getPart(AIMovingPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);
            CollisionPart collisionPart = enemy.getPart(CollisionPart.class);
            ShootingPart shootingPart = enemy.getPart(ShootingPart.class);
            WeaponPart weaponPart = enemy.getPart(WeaponPart.class);

            if (collisionPart.getHit()) {
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

            if (lineOfSightService != null && playerPositionPart != null) {
                Vector2f playerPosition = new Vector2f(playerPositionPart.getX(), playerPositionPart.getY());
                Vector2f enemyPosition = new Vector2f(positionPart.getX(), positionPart.getY());
                if (lineOfSightService.isInlineOfSight(world, enemyPosition, playerPosition)) {
                    shootingPart.setShooting(true);

                    /* if (weaponPart.getRange() == enemyPosition.distance(playerPosition) + 10) {
                        aiMovingPart.setShouldMove(false);
                    } */
                }
            }



            aiMovingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);
            lifePart.process(gameData, enemy);
            if (lifePart.isDead()) {
                world.removeEntity(enemy);
            }

            shootingPart.process(gameData, enemy);

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
