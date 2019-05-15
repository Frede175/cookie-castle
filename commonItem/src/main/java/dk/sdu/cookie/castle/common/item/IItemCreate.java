package dk.sdu.cookie.castle.common.item;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.World;

public interface IItemCreate {
    Entity createItem(float x, float y, ItemType itemType, World world);
}
