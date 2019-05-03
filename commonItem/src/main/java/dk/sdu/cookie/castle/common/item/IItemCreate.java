package dk.sdu.cookie.castle.common.item;

import dk.sdu.cookie.castle.common.data.World;

public interface IItemCreate {

    String createItem(float x, float y, ItemType itemType, World world);
}
