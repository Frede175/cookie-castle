package dk.sdu.cookie.castle.item;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.*;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IGamePluginService;

public class ItemPlugin implements IGamePluginService {

    private Entity item;

    @Override
    public void start(GameData gameData, World world) {
        item = createItem(gameData);
        world.addEntity(item);
        System.out.println("Jeff");
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(item);
        System.out.println("No Jeff");
    }

    private Entity createItem(GameData gameData) {
        float x = gameData.getDisplayWidth() / 2 + 200;
        float y = gameData.getDisplayHeight() / 2 + 150;

        float[] shapex = new float[6];
        float[] shapey = new float[6];


        Entity item = new Item();
        item.add(new PositionPart(x, y));
        item.add(new LifePart(1, 1,1, 30));
        item.setRadius(40);
        item.add(new CollisionPart());
        item.add(new ItemPart(new WeaponPart(10,15,20)));
        item.setShapeX(shapex);
        item.setShapeY(shapey);
        item.setEntityType(EntityType.STATIC_OBSTACLE);

        return item;
    }

}
