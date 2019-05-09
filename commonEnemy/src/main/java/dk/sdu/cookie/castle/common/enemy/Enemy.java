package dk.sdu.cookie.castle.common.enemy;

import dk.sdu.cookie.castle.common.data.Entity;

public class Enemy extends Entity {
    EnemyType enemyType;

    public void setEnemyType(EnemyType enemyType) {
        this.enemyType = enemyType;
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }
}
