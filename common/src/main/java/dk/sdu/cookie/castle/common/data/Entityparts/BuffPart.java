package dk.sdu.cookie.castle.common.data.Entityparts;

import dk.sdu.cookie.castle.common.data.BuffType;
import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;

import java.util.HashMap;

public class BuffPart implements EntityPart {

    /**
     * Contains all buff types and their amount
     */
    private HashMap<BuffType, Float> buffs;

    public BuffPart() {
        buffs = new HashMap<>();
    }

    public BuffPart(BuffType buffType, float multiplier) {
        buffs = new HashMap<>();
        addBuff(buffType, multiplier);
    }

    /**
     * Adds a buffType to the buffpart
     * @param buffType The buffType type to add
     * @param multiplier The amount
     */
    public void addBuff(BuffType buffType, float multiplier)  {
        buffs.put(buffType, multiplier);
    }

    /**
     * Returns a map of all buffs and their amount
     * @return A map of all buffs and their amount
     */
    public HashMap<BuffType, Float> getBuffs() {
        return buffs;
    }

    /**
     * Returns specific buffType
     * @param buffType The buffType to search for
     * @return The amount, if not found returns 0
     */
    public float getBuff(BuffType buffType) {
        if (buffs.containsKey(buffType)) return buffs.get(buffType);
        return 0;
    }

    @Override
    public void process(GameData gameData, Entity entity) {

    }
}
