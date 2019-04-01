package dk.sdu.cookie.castle.bullet;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.*;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IEntityProcessingService;

public class BulletProcessing implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            if (entity.getPart(ShootingPart.class) != null) {

                ShootingPart shootingPart = entity.getPart(ShootingPart.class);
                //Shoot if isShooting is true, ie. space is pressed.
                if (shootingPart.isShooting()) {
                    PositionPart positionPart = entity.getPart(PositionPart.class);
                    //Add entity radius to initial position to avoid immideate collision.
                    Entity bullet = createBullet(positionPart.getX() + entity.getRadius(), positionPart.getY() + entity.getRadius(), positionPart.getRadians(), shootingPart.getID());
                    shootingPart.setShooting(false);
                    world.addEntity(bullet);
                }
            }
        }
        for (Entity b : world.getEntities(Bullet.class)) {
            PositionPart positionPart = b.getPart(PositionPart.class);
            MovingPart movingPart = b.getPart(MovingPart.class);
            TimerPart timerPart = b.getPart(TimerPart.class);
            movingPart.setUp(true);
            LifePart lifePart = b.getPart(LifePart.class);
            //If duration is exceeded, remove the bullet.
            if (timerPart.getExpiration() < 0) {
                world.removeEntity(b);
            }

            positionPart.process(gameData, b);
            movingPart.process(gameData, b);
            timerPart.process(gameData, b);
            lifePart.process(gameData, b);

            updateShape(b);
        }
    }

    private Entity createBullet(float x, float y, float radians, String uuid) {
        Entity b = new Bullet();

        b.add(new PositionPart(x, y, radians));
        b.add(new MovingPart(10));
        b.add(new TimerPart(3));
        b.add(new LifePart(1));
        b.setEntityType(EntityType.PLAYER_BULLET);
        b.setRadius(2);

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
