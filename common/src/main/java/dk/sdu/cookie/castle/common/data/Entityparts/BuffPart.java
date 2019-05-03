package dk.sdu.cookie.castle.common.data.Entityparts;

import dk.sdu.cookie.castle.common.data.BuffType;
import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;

import java.util.HashMap;

public class BuffPart implements EntityPart {

    /**
     * Contains all buff types and their amount
     */
    private BuffType buffType;
    private float multiplier;

    public BuffType getBuffType() {
        return buffType;
    }

    public float getMultiplier() {
        return multiplier;
    }

    public BuffPart(BuffType buffType, float multiplier) {
        this.buffType = buffType;
        this.multiplier = multiplier;
    }

    @Override
    public void process(GameData gameData, Entity entity) {

    }
}
