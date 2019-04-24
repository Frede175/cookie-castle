package dk.sdu.cookie.castle.common.data.Entityparts;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;

public class WeaponPart implements EntityPart {

    private float range;
    private float damage;
    private float attackSpeed;

    public float getRange() {
        return range;
    }

    public float getDamage() {
        return damage;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    /**
     * Weaponpart is used for items of the type "weapon"
     * This item will alter the dmg and range of an entity
     * @param range
     * @param damage
     * @param attackSpeed
     */
    public WeaponPart(float range, float damage, float attackSpeed) {
        this.range = range;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
    }

    @Override
    public void process(GameData gameData, Entity entity) {

    }
}
