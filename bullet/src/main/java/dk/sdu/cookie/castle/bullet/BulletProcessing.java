package dk.sdu.cookie.castle.bullet;

import dk.sdu.cookie.castle.common.data.*;
import dk.sdu.cookie.castle.common.data.Entityparts.*;
import dk.sdu.cookie.castle.common.services.IEntityProcessingService;
import dk.sdu.cookie.castle.common.util.Vector2f;

public class BulletProcessing implements IEntityProcessingService {

    /**
     * This method checks if any entities in the world, which has a ShootingPart is trying to shoot and if it can, then
     * it then creates a Bullet if true. Furthermore it checks for collision on the CollisionPart, and then process'
     * the rest of the Parts.
     *
     * @param gameData The GameData
     * @param world    The World
     */

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            if (!entity.isActive()) continue;

            ShootingPart shootingPart;
            InventoryPart inventoryPart;

            if ((shootingPart = entity.getPart(ShootingPart.class)) != null && (inventoryPart = entity.getPart(InventoryPart.class)) != null) {
                if (inventoryPart.getCurrentWeapon() == null) continue;

                //Shoot if isShooting is true, ie. space is pressed and the weapon is not on cooldown, ie. can't shoot
                if (shootingPart.isShooting() && shootingPart.canShoot()) {
                    PositionPart positionPart = entity.getPart(PositionPart.class);
                    Vector2f positionVector = new Vector2f(positionPart.getRadians());
                    positionVector.mult(entity.getMin().distance(entity.getMax()));
                    //Add entity radius to initial position to avoid immediate collision.
                    Entity bullet = createBullet(positionPart.getX() + positionVector.getX(), positionPart.getY() + positionVector.getY(), positionPart.getRadians(), entity);
                    shootingPart.setShooting(false);
                    shootingPart.setCanShoot(false);
                    bullet.setIsActive(true);
                    world.addEntity(bullet);
                }
            }
        }

        // loop to keep track of all the bullets in the world
        // Keeping track of collision, dmg, and if it should be removed from the game
        for (Entity bullet : world.getEntities(Bullet.class)) {
            if (!bullet.isActive()) continue;

            PositionPart positionPart = bullet.getPart(PositionPart.class);
            TimerPart timerPart = bullet.getPart(TimerPart.class);
            BulletMovingPart bulletMovingPart = bullet.getPart(BulletMovingPart.class);
            CollisionPart collisionPart = bullet.getPart(CollisionPart.class);
            DamagePart damagePart = bullet.getPart(DamagePart.class);
            // If duration is exceeded, remove the bullet.
            if (timerPart.getDuration() < 0) {
                world.removeEntity(bullet);
            }

            // Checks what the bullet hits
            if (collisionPart.getIsHit()) {
                switch (collisionPart.getCollidingEntity().getEntityType()) {
                    case PLAYER:
                        if (bullet.getEntityType() == EntityType.ENEMY_BULLET) {
                            world.removeEntity(bullet);
                        }
                        break;
                    case ENEMY:
                        if (bullet.getEntityType() == EntityType.PLAYER_BULLET) {
                            world.removeEntity(bullet);
                        }
                        break;
                    case ITEM:
                        break;
                    default:
                        world.removeEntity(bullet);
                        break;
                }
                collisionPart.setIsHit(false);
            }

            // Keep refreshing all the data concerning bullets
            positionPart.process(gameData, bullet);
            timerPart.process(gameData, bullet);
            bulletMovingPart.process(gameData, bullet);
            collisionPart.process(gameData, bullet);
            damagePart.process(gameData, bullet);
            updateShape(bullet);
        }
    }


    /**
     * Creates a Bullet with all the needed parts and parameters, and also determines, which Entity shot it
     *
     * @param x       The starting x position of the Bullet
     * @param y       The starting y position of the Bullet
     * @param radians The starting radians of the Bullet
     * @param entity  The Entity that shoots the Bullet
     * @return
     */
    private Entity createBullet(float x, float y, float radians, Entity entity) {
        Entity b = new Bullet();

        b.add(new PositionPart(x, y, radians));
        b.add(new LifePart(1));
        b.add(new BulletMovingPart());
        b.add(new CollisionPart());
        InventoryPart inventoryPart = entity.getPart(InventoryPart.class);
        WeaponPart weaponPart = inventoryPart.getCurrentWeapon().getWeapon();
        b.add(new DamagePart(weaponPart.getDamage()));
        TimerPart timerPart = new TimerPart(weaponPart.getRange() / Constants.BULLET_SPEED);
        b.add(timerPart);
        timerPart.setHasStarted(true);

        if (entity.getEntityType() == EntityType.PLAYER) {
            b.setEntityType(EntityType.PLAYER_BULLET);
        } else if (entity.getEntityType() == EntityType.ENEMY) {
            b.setEntityType(EntityType.ENEMY_BULLET);
        }

        b.setCurrentTexture(BulletPlugin.getAssets().get("bullet"));

        return b;
    }

    /**
     * This method calculates the position and shape of the entity, based on the entitys x, y and radius.
     * Then updates the ShapeX and ShapeY
     *
     * @param entity The entity, which shape is about to be updated
     */
    private void updateShape(Entity entity) {

        float radius = 2;

        float[] shapeX = entity.getShapeX();
        float[] shapeY = entity.getShapeY();

        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapeX[0] = (float) (x + Math.cos(radians) * radius);
        shapeY[0] = (float) (y + Math.sin(radians) * radius);

        shapeX[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * radius);
        shapeY[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * radius);

        shapeX[2] = (float) (x + Math.cos(radians + 3.1415f) * radius * 0.5);
        shapeY[2] = (float) (y + Math.sin(radians + 3.1415f) * radius * 0.5);

        shapeX[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * radius);
        shapeY[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * radius);

        entity.setShapeX(shapeX);
        entity.setShapeY(shapeY);
        entity.updateMinMax();
    }
}
