package dk.sdu.cookie.castle.item;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.*;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.item.IItemCreate;
import dk.sdu.cookie.castle.common.item.Item;
import dk.sdu.cookie.castle.common.item.ItemType;

public class CreateItem implements IItemCreate {

    /**
     * This method creates an Item
     *
     * @param x The X coordinate of the spawn position
     * @param y The Y coordinate of the spawn position
     * @param itemType The type of item it is
     * @param world The world the item is to be spawned into
     * @return The ID of the item
     */
    @Override
    public String createItem(float x, float y, ItemType itemType, World world) {
        float[] shapeX = new float[6];
        float[] shapeY = new float[6];

        Entity item = new Item();

        item.add(new ItemPart(new TimerPart(itemType.getTimer(), false), new BuffPart(itemType.getBuffType(), 1.8f)));
        item.add(new PositionPart(x, y, 0));
        item.setRadius(30);
        item.add(new CollisionPart());
        item.setShapeX(shapeX);
        item.setShapeY(shapeY);
        item.setEntityType(EntityType.ITEM);
        world.addEntity(item);
        return item.getID();
    }
}
