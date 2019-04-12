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

        List<Entity> entities = new ArrayList<>();
        entities.add(createDoor(gameData));
        Room room = new Room(entities);
        Map.getInstance().setCurrentRoom(room);
        for (Entity e : entities) {
            world.addEntity(e);
        }
        // skal initiate singleton og kalde "generateNewMap" func


    }

    @Override
    public void stop(GameData gameData, World world) {

    }

    private Entity createDoor(GameData gameData) {
        float x = gameData.getDisplayWidth() / 2 + 300;
        float y = gameData.getDisplayHeight() / 2 + 150;

        float[] shapex = new float[6];
        float[] shapey = new float[6];

        Entity door = new Door(new Room(new ArrayList<>()));
        door.add(new PositionPart(x * 2, y * 2, 0));
        door.setRadius(30);
        door.add(new CollisionPart());
        door.setShapeX(shapex);
        door.setShapeY(shapey);
        door.setEntityType(EntityType.DOOR);
        List<Entity> entities = new ArrayList();
        entities.add(door);
        Entity item = new Door(new Room(entities));
        item.add(new PositionPart(x, y, 0));
        item.setRadius(30);
        item.add(new CollisionPart());
        item.setShapeX(shapex);
        item.setShapeY(shapey);
        item.setEntityType(EntityType.DOOR);

        return item;
    }
}
