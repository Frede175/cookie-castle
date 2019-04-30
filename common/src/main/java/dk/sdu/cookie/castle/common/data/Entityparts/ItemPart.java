package dk.sdu.cookie.castle.common.data.Entityparts;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;

/*
    You can only have items that is permanent or not. So you can't have a buff that increases
    static health, and then for 30 seconds increases health regen

    It's a permanent buff it is a buff and doesn't have a timer
*/
public class ItemPart implements EntityPart {

    private TimerPart timer;
    private BuffPart buff;
    private WeaponPart weapon;

    /**
     * True if is a weapon, false if the item is a buff
     */
    private boolean isWeapon;

    /**
     * True if the item has a timer, false if its permanent in the inventory
     */
    private boolean hasTimer;

    /**
     * Constructs a timed buff
     * @param timer How long the buff should run
     * @param buff The buff amount
     */
    public ItemPart(TimerPart timer, BuffPart buff) {
        this.timer = timer;
        this.buff = buff;
        isWeapon = false;
        hasTimer = true;
    }

    /**
     * Constructs a permanent buff
     * @param buff The buff amount
     */
    public ItemPart(BuffPart buff) {
        this.buff = buff;
        isWeapon = false;
        hasTimer = false;
    }

    /**
     * Constructs a weapon
     * @param weapon The weapon part to use
     */
    public ItemPart(WeaponPart weapon) {
        this.weapon = weapon;
        isWeapon = true;
        hasTimer = false;
    }

    public boolean isWeapon() {
        return isWeapon;
    }

    public boolean hasTimer() {
        return hasTimer;
    }

    public boolean isPermanentBuff() {
        return !isWeapon && !hasTimer;
    }

    public TimerPart getTimer() {
        return timer;
    }

    public BuffPart getBuff() {
        return buff;
    }

    public WeaponPart getWeapon() {
        return weapon;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        
    }
}
