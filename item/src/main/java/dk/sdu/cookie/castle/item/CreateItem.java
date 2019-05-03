package dk.sdu.cookie.castle.item;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.*;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.item.IItemCreate;
import dk.sdu.cookie.castle.common.item.Item;
import dk.sdu.cookie.castle.common.item.ItemType;

public class CreateItem implements IItemCreate {

    @Override
    public String createItem(float x, float y, ItemType itemType, World world) {
        float[] shapeX = new float[6];
        float[] shapeY = new float[6];

        Entity item = new Item();

        item.add(new ItemPart(new TimerPart(itemType.getTimer()), new BuffPart(itemType.getBuffType(), 1.8f)));
        item.add(new PositionPart(x, y, 0));
        item.add(new LifePart(1, 1, 1, 30));
        item.setRadius(30);
        item.add(new CollisionPart());
        item.add(new ItemPart(new WeaponPart(10, 15, 20)));
        item.setShapeX(shapeX);
        item.setShapeY(shapeY);
        item.setEntityType(EntityType.ITEM);

        return item.getID();
    }
}
