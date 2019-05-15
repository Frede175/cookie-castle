package dk.sdu.cookie.castle.common.item;

import dk.sdu.cookie.castle.common.data.Entity;

public class Item extends Entity {

    private ItemType itemType;

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }
}
