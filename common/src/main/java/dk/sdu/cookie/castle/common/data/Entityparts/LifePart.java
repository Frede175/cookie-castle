package dk.sdu.cookie.castle.common.data.Entityparts;


import dk.sdu.cookie.castle.common.data.BuffType;
import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;

import java.util.Map;

import static dk.sdu.cookie.castle.common.data.BuffType.HEALTH_REGEN;

public class LifePart implements EntityPart {

    private boolean dead = false;
    private float health;
    private float healthRegen;
    private float maxHealth;
    private float damageReduction;
    private boolean isHit = false;

    public LifePart(float health, float healthRegen, float maxHealth, float damageReduction) {
        this.health = health;
        this.healthRegen = healthRegen;
        this.maxHealth = maxHealth;
        this.damageReduction = damageReduction;
    }

    public LifePart(float health) {
        this.health = health;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public boolean isDead() {
        return dead;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        InventoryPart inventoryPart = entity.getPart(InventoryPart.class);

        // Temp variables for buffed stats
        float buffedHealthRegen = healthRegen;
        float buffedDamageReduction = damageReduction;

        if (inventoryPart != null) {
            for (ItemPart itemPart : inventoryPart.getItemParts()) {

                // Checks if the buff is permanent, if it is add the health and remove the item from the inventory
                if (itemPart.isPermanentBuff()) {

                    // Checks if the healing over heals the maximum health limit
                    float healing = itemPart.getBuff().getMultiplier();
                    if (health + healing <= maxHealth) {
                        health += healing;
                    } else {
                        health = maxHealth;
                    }

                    // Uses the item in the inventory (Removes the item from the inventory)
                    inventoryPart.usePermanentBuff(itemPart);

                    // Continues since a permanent can't have timed buffs
                    continue;
                }

                // Checks if the item is a buff
                if (!itemPart.isWeapon()) {
                    switch (itemPart.getBuff().getBuffType()) {
                        case DAMAGE_REDUCTION:
                            buffedDamageReduction += itemPart.getBuff().getMultiplier();
                            break;
                        case HEALTH_REGEN:
                            buffedHealthRegen += itemPart.getBuff().getMultiplier();
                            break;
                        default:
                            break;
                    }

                }
            }
        }

        // Regens health
        health += buffedHealthRegen * gameData.getDelta();
        damageReduction += buffedDamageReduction;
        if (health > maxHealth) health = maxHealth;

        if (health <= 0) {
            dead = true;
        }
    }
}
