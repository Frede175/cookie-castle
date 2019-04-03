package dk.sdu.cookie.castle.bullet;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.*;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IEntityProcessingService;
import dk.sdu.cookie.castle.common.util.Vector2f;

public class BulletProcessing implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            if (entity.getPart(ShootingPart.class) != null && entity.getPart(WeaponPart.class) != null) {

                ShootingPart shootingPart = entity.getPart(ShootingPart.class);

                //Shoot if isShooting is true, ie. space is pressed.

                if (shootingPart.isShooting() && shootingPart.isCanShoot()) {
                    PositionPart positionPart = entity.getPart(PositionPart.class);
                    Vector2f positionsVector = new Vector2f(positionPart.getRadians());
                    positionsVector.mult(entity.getRadius());
                    //Add entity radius to initial position to avoid immideate collision.
                    Entity bullet = createBullet(positionPart.getX() + positionsVector.getX(), positionPart.getY() + positionsVector.getY(), positionPart.getRadians(), entity);
                    shootingPart.setShooting(false);
                    shootingPart.setCanShoot(false);
                    world.addEntity(bullet);
                }
            }
        }
        for (Entity bullet : world.getEntities(Bullet.class)) {
            PositionPart positionPart = bullet.getPart(PositionPart.class);
            TimerPart timerPart = bullet.getPart(TimerPart.class);
            LifePart lifePart = bullet.getPart(LifePart.class);
            BulletMovingPart bulletMovingPart = bullet.getPart(BulletMovingPart.class);
            CollisionPart collisionPart = bullet.getPart(CollisionPart.class);
            DamagePart damagePart = bullet.getPart(DamagePart.class);
            //If duration is exceeded, remove the bullet.
            if (timerPart.getExpiration() < 0) {
                world.removeEntity(bullet);
            }

            if (collisionPart.getHit()) {
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
                    case PLAYER_BULLET:
                        System.out.println("Hitting another player bullet");
                        break;
                    default:
                        break;
                }
                collisionPart.setHit(false);
            }

            positionPart.process(gameData, bullet);
            timerPart.process(gameData, bullet);
            lifePart.process(gameData, bullet);
            bulletMovingPart.process(gameData, bullet);
            collisionPart.process(gameData, bullet);
            damagePart.process(gameData, bullet);

            updateShape(bullet);

        }
    }

    private Entity createBullet(float x, float y, float radians, Entity entity) {
        Entity b = new Bullet();

        b.add(new PositionPart(x, y, radians));

        b.add(new LifePart(1));
        b.add(new BulletMovingPart());
        b.add(new CollisionPart());
        if (entity.getEntityType() == EntityType.PLAYER) {
            b.setEntityType(EntityType.PLAYER_BULLET);
        } else if (entity.getEntityType() == EntityType.ENEMY) {
            b.setEntityType(EntityType.ENEMY_BULLET);
        }
        b.setRadius(2);
        WeaponPart EntityWeaponPart = entity.getPart(WeaponPart.class);
        b.add(new DamagePart(EntityWeaponPart.getDamage()));
        b.add(new TimerPart(EntityWeaponPart.getRange()));

        return b;
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();

        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * entity.getRadius());
        shapey[0] = (float) (y + Math.sin(radians) * entity.getRadius());

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * entity.getRadius());
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * entity.getRadius());

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * entity.getRadius() * 0.5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * entity.getRadius() * 0.5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * entity.getRadius());
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * entity.getRadius());

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}
