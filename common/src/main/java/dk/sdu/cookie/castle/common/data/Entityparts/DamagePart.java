package dk.sdu.cookie.castle.common.data.Entityparts;

import dk.sdu.cookie.castle.common.data.BuffType;
import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;

public class DamagePart implements EntityPart {

    private float damage;

    public float getDamage() {
        return damage;
    }

    public DamagePart(float damage) {
        this.damage = damage;
    }

    @Override
    public void process(GameData gameData, Entity entity) {

    }
}
