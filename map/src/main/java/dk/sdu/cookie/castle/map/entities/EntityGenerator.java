package dk.sdu.cookie.castle.map.entities;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.*;
import dk.sdu.cookie.castle.common.enemy.Enemy;
import dk.sdu.cookie.castle.common.enemy.EnemyType;
import dk.sdu.cookie.castle.common.item.Item;
import dk.sdu.cookie.castle.common.item.ItemType;

public class EntityGenerator {
    public static Entity generateEnemy(float x, float y, EnemyType enemyType) {
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

        enemy.add(enemyType.getWeaponPart());

        enemy.setShapeY(shapeY);
        enemy.setShapeX(shapeX);
        enemy.add(new ShootingPart(enemyType.getWeaponPart().getAttackSpeed()));

        return enemy;
    }

    public static Entity generateItem(float x, float y, ItemType itemType) {
        float[] shapeX = new float[6];
        float[] shapeY = new float[6];

        Entity item = new Item();
        item.add(new PositionPart(x, y, 0));
        item.setRadius(30);
        item.add(new CollisionPart());
        item.add(itemType.getItemPart());
        item.setShapeX(shapeX);
        item.setShapeY(shapeY);
        item.setEntityType(EntityType.ITEM);

        return item;
    }
}
