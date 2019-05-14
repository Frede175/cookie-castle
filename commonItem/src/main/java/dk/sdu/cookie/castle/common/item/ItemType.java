package dk.sdu.cookie.castle.common.item;

import dk.sdu.cookie.castle.common.data.BuffType;

public enum ItemType {

    ENERGYDRINK(BuffType.ATTACK_SPEED, 20),
    PROTEINSHAKE(BuffType.DAMAGE, 10),
    SUGAR(BuffType.MOVEMENT_SPEED, 10);


    float timer;
    BuffType buffType;

    ItemType(BuffType buffType, float timer) {
        this.timer = timer;
        this.buffType = buffType;
    }

    public float getTimer() {
        return timer;
    }

    public BuffType getBuffType() {
        return buffType;
    }
}
