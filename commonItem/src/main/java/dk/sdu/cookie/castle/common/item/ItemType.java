package dk.sdu.cookie.castle.common.item;

import dk.sdu.cookie.castle.common.data.BuffType;
import dk.sdu.cookie.castle.common.data.Entityparts.BuffPart;
import dk.sdu.cookie.castle.common.data.Entityparts.ItemPart;
import dk.sdu.cookie.castle.common.data.Entityparts.TimerPart;
import dk.sdu.cookie.castle.common.data.Entityparts.WeaponPart;

public enum ItemType {
    COOKIE(
            new BuffPart(BuffType.MOVEMENT_SPEED, 1.8f),
            new TimerPart(5f)
    );

    ItemPart itemPart;

    ItemType(BuffPart buffPart, TimerPart timerPart, WeaponPart weaponPart) {

    }

    ItemType(BuffPart buffPart, TimerPart timerPart) {
        itemPart = new ItemPart(timerPart, buffPart);
    }

    public ItemPart getItemPart() {
        return itemPart;
    }
}
