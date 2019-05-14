package dk.sdu.cookie.castle.common.enemy;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.World;

public interface IEnemyCreate {
    Entity createEnemy(float x, float y, EnemyType enemyType, World world);
}
