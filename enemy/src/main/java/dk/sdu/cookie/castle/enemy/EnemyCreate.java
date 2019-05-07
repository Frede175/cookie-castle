package dk.sdu.cookie.castle.enemy;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.*;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.enemy.Enemy;
import dk.sdu.cookie.castle.common.enemy.EnemyType;
import dk.sdu.cookie.castle.common.enemy.IEnemyCreate;

public class EnemyCreate implements IEnemyCreate {

    @Override
    public String createEnemy(float x, float y, EnemyType enemyType, World world) {
        float[] shapeX = new float[3];
        float[] shapeY = new float[3];

        float maxSpeed = 150;
        float radians = 3.1415f / 2;

        Entity enemy = new Enemy();
        enemy.setRadius(8);
        enemy.add(new AIMovingPart(maxSpeed));
        enemy.add(new PositionPart(x, y, radians));
        enemy.add(new LifePart(1, 1, 1, 1));
        enemy.add(new CollisionPart());
        enemy.setEntityType(EntityType.ENEMY);

        if (enemyType == EnemyType.RANGED) {
            WeaponPart weaponPart = new WeaponPart(500f, 2f, 3f);
            enemy.add(weaponPart);
            enemy.add(new ShootingPart(weaponPart.getAttackSpeed()));
        }

        enemy.setCurrentTexture(EnemyPlugin.getAssetId("cookie"));

        enemy.setShapeY(shapeY);
        enemy.setShapeX(shapeX);

        world.addEntity(enemy);

        return enemy.getID();
    }
}
