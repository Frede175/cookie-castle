package dk.sdu.cookie.castle.common.data.Entityparts;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;

import java.util.ArrayList;

public class InventoryPart implements EntityPart {

    /**
     * Currently equipped weapon
     */
    private ItemPart currentWeapon;

    /**
     * All items in the inventory
     */
    private ArrayList<ItemPart> itemParts;

    /**
     * Constructs an Inventory only with starting items
     *
     * @param itemParts List of items to construct with
     */
    public InventoryPart(ArrayList<ItemPart> itemParts) {
        this.itemParts = itemParts;
    }

    public InventoryPart() {
        itemParts = new ArrayList<>();
    }

    /**
     * Constructs an Inventory with starting items and current weapon
     *
     * @param currentWeapon The current weapon at the start
     * @param itemParts     List of items to construct with
     */
    public InventoryPart(ItemPart currentWeapon, ArrayList<ItemPart> itemParts) {
        this.currentWeapon = currentWeapon;
        this.itemParts = itemParts;
    }

    /**
     * Returns all items in the inventory
     *
     * @return All items in the inventory
     */
    public ArrayList<ItemPart> getItemParts() {
        return itemParts;
    }

    /**
     * Uses a permanent buff, and removes it from the inventory
     *
     * @param itemPart The permanent item to use
     */
    public void usePermanentBuff(ItemPart itemPart) {
        if (itemPart.isPermanentBuff()) itemParts.remove(itemPart);
    }

    public void addItem(ItemPart itemPart) {
        itemParts.add(itemPart);
        // Checks if the item picked up is a weapon, then replaces it with the old one
        if (itemPart.isWeapon()) {
            itemParts.remove(currentWeapon);
            currentWeapon = itemPart;
        }
        if (itemPart.hasTimer()) {
            itemPart.getTimer().setHasStarted(true);
        }
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        // Removes ItemPart if it is expired
        itemParts.removeIf(itemPart -> itemPart.hasTimer() && itemPart.getTimer().isExpired());
        for (ItemPart itemParts : itemParts) {
            itemParts.process(gameData, entity);
        }
    }
}
