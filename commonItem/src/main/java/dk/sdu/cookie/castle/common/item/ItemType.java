package dk.sdu.cookie.castle.common.item;

import dk.sdu.cookie.castle.common.data.BuffType;

public enum ItemType {

    COOKIE(BuffType.MOVEMENT_SPEED,5
    );

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
