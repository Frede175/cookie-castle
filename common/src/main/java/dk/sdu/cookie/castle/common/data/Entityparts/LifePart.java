package dk.sdu.cookie.castle.common.data.Entityparts;



import dk.sdu.cookie.castle.common.data.Buff;
import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;

import java.util.Map;

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

    public boolean isHit() {
        return isHit;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
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

        if(inventoryPart != null) {
            for (ItemPart itemPart : inventoryPart.getItemParts()) {

                // Checks if the buff is permanent, if it is add the health and remove the item from the inventory
                if(itemPart.isPermanentBuff()) {

                    // Checks if the healing over heals the maximum health limit
                    float healing = itemPart.getBuff().getSpecificBuff(Buff.HEALTH);
                    if(health + healing <= maxHealth) {
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
                if(!itemPart.isWeapon()) {

                    // Gets all buffs
                    Map<Buff, Float> buffs = itemPart.getBuff().getBuffs();

                    // Checks if the buff contains health regen and damage reduction, and adds that to the base
                    if(buffs.containsKey(Buff.HEALTH_REGEN)) {
                        buffedHealthRegen += buffs.get(Buff.HEALTH_REGEN);
                    }
                    if(buffs.containsKey(Buff.DAMAGE_REDUCTION)) {
                        buffedDamageReduction += buffs.get(Buff.DAMAGE_REDUCTION);
                    }
                }
            }
        }

        // Regens health
        health += buffedHealthRegen;
        if (health > maxHealth) health = maxHealth;

        if (health <= 0) {
            dead = true;
        }

    }
}
