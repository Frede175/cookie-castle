package dk.sdu.cookie.castle.enemy;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.*;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;

    @Override
    public void start(GameData gameData, World world) {
        enemy = createEnemy(gameData);
        world.addEntity(enemy);
    }

    private Entity createEnemy(GameData gameData) {
        float[] shapeX = new float[3];
        float[] shapeY = new float[3];

        float maxSpeed = 150;
        float x = gameData.getDisplayWidth() / 4;
        float y = gameData.getDisplayHeight() / 4;
        float radians = 3.1415f / 2;

        Entity enemyShip = new Enemy();
        enemyShip.setRadius(8);
        enemyShip.add(new AIMovingPart(maxSpeed));
        enemyShip.add(new PositionPart(x, y, radians));
        enemyShip.add(new LifePart(1,1,1,1));
        enemyShip.add(new CollisionPart());
        enemyShip.setEntityType(EntityType.ENEMY);

        WeaponPart weaponPart = new WeaponPart(400f,10f,5f);
        enemyShip.add(weaponPart);

        enemyShip.setShapeY(shapeY);
        enemyShip.setShapeX(shapeX);
        enemyShip.add(new ShootingPart(weaponPart.getAttackSpeed()));

        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }
}
