package dk.sdu.cookie.castle.enemy;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.*;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.enemy.Enemy;
import dk.sdu.cookie.castle.common.enemy.EnemyType;
import dk.sdu.cookie.castle.common.enemy.IEnemyCreate;

public class EnemyCreate implements IEnemyCreate {


    /**
     * This method creates an enemy unit.
     *
     * @param x The starting x position of the Enemy
     * @param y The starting y position of the Enemy
     * @param enemyType A type to determined wether if the enemy is Melee or Ranged
     * @param world The World the enemy is to be added to
     * @return The ID of the enemy
     */
    @Override
    public String createEnemy(float x, float y, EnemyType enemyType, World world) {
        float[] shapeX = new float[3];
        float[] shapeY = new float[3];

        float maxSpeed = 100;
        float radians = 3.1415f / 2;

        Enemy enemy = new Enemy();

        enemy.add(new PositionPart(x, y, radians));
        enemy.add(new CollisionPart());
        enemy.setEntityType(EntityType.ENEMY);

        if (enemyType == EnemyType.RANGED) {
            WeaponPart weaponPart = new WeaponPart(500f, 2f, 3f);
            enemy.add(weaponPart);
            enemy.add(new ShootingPart(weaponPart.getAttackSpeed()));
            enemy.setEnemyType(EnemyType.RANGED);
            enemy.add(new LifePart(6, 1, 6, 1));
            enemy.setRadius(15);
            maxSpeed = 150;
        }

        if (enemyType == EnemyType.MELEE) {
            enemy.add(new DamagePart(3f));
            enemy.setEnemyType(EnemyType.MELEE);
            enemy.add(new LifePart(3, 1, 3, 1));
            enemy.setRadius(25);
            maxSpeed = 185;
        }

        enemy.add(new AIMovingPart(maxSpeed));

        enemy.setShapeY(shapeY);
        enemy.setShapeX(shapeX);

        world.addEntity(enemy);

        return enemy.getID();
    }
}
