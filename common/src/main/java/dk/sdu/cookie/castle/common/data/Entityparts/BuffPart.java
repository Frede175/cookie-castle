package dk.sdu.cookie.castle.common.data.Entityparts;

import dk.sdu.cookie.castle.common.data.Buff;
import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;

import java.util.HashMap;

public class BuffPart implements EntityPart {

    /**
     * Contains all buff types and their amount
     */
    private HashMap<Buff, Float> buffs;

    public BuffPart() {
        buffs = new HashMap<>();
    }

    /**
     * Adds a buff to the buffpart
     * @param buff The buff type to add
     * @param amount The amount
     */
    public void addBuff(Buff buff, float amount)  {
        buffs.put(buff, amount);
    }

    /**
     * Returns a map of all buffs and their amount
     * @return A map of all buffs and their amount
     */
    public HashMap<Buff, Float> getBuffs() {
        return buffs;
    }

    /**
     * Returns specific buff
     * @param buff The buff to search for
     * @return The amount, if not found returns 0
     */
    public float getSpecificBuff(Buff buff) {
        if (buffs.containsKey(buff)) return buffs.get(buff);
        return 0;
    }

    @Override
    public void process(GameData gameData, Entity entity) {

    }
}
