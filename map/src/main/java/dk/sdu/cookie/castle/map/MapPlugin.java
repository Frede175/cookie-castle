package dk.sdu.cookie.castle.map;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.*;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IGamePluginService;
import dk.sdu.cookie.castle.map.entities.Door;

import java.util.ArrayList;
import java.util.List;

public class MapPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        world.addEntity(createDoor(gameData));


        // skal initiate singleton og kalde "generateNewMap" func


    }

    @Override
    public void stop(GameData gameData, World world) {

    }

    private Entity createDoor(GameData gameData) {
        float x = gameData.getDisplayWidth() / 2 + 200;
        float y = gameData.getDisplayHeight() / 2 + 150;

        float[] shapex = new float[6];
        float[] shapey = new float[6];


        Entity item = new Door(new Room(new ArrayList<Entity>()));
        item.add(new PositionPart(x, y, 0));
        item.setRadius(30);
        item.add(new CollisionPart());
        item.setShapeX(shapex);
        item.setShapeY(shapey);
        item.setEntityType(EntityType.DOOR);

        return item;
    }
}
