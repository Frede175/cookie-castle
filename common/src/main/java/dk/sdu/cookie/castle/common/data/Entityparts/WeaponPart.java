package dk.sdu.cookie.castle.common.data.Entityparts;

import dk.sdu.cookie.castle.common.data.BuffType;
import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;

public class WeaponPart implements EntityPart {

    /**
     * The bullet range in pixels.
     */
    private float range;

    /**
     * The damage done by the bullet.
     */
    private float damage;
    private float buffedDamage;

    /**
     * The attack speed of the weapon in rounds per second.
     */
    private float attackSpeed;

    public float getRange() {
        return range;
    }

    public float getDamage() {
        return buffedDamage;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    /**
     * Weaponpart is used for items of the type "weapon"
     * This item will alter the dmg and range of an entity
     *
     * @param range       The range the bullet flies before dying. (pixels)
     * @param damage      The damage done to the entity
     * @param attackSpeed The speed at which the weapon can be shot. (rounds per second)
     */
    public WeaponPart(float range, float damage, float attackSpeed) {
        this.range = range;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        InventoryPart inventoryPart = entity.getPart(InventoryPart.class);

        buffedDamage = damage;
        if (inventoryPart != null) {
            for (ItemPart itemPart : inventoryPart.getItemParts()) {
                if (!itemPart.isWeapon()) {
                    BuffPart buffPart = itemPart.getBuff();
                    if (buffPart.getBuffType() == BuffType.DAMAGE) {
                        buffedDamage *= buffPart.getMultiplier();
                    }
                }
            }
        }
    }
}
