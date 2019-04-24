package dk.sdu.cookie.castle.map;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.*;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IGamePluginService;
import dk.sdu.cookie.castle.map.entities.door.Door;
import dk.sdu.cookie.castle.map.entities.door.DoorPosition;

import java.util.ArrayList;
import java.util.List;

public class MapPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        // Set door positions with display dimensions from GameData
        for (DoorPosition doorPosition : DoorPosition.values()) {
            doorPosition.setPosition(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        }

        // skal initiate singleton og kalde "generateNewMap" func
        Map.getInstance().generateMap(3);
        Map.getInstance().setCurrentRoom(Map.getInstance().getListOfRooms().get(0));
        for (Entity e : Map.getInstance().getCurrentRoom().getEntityList()) {
            world.addEntity(e);
        }
    }

    @Override
    public void stop(GameData gameData, World world) {

    }

    private Entity createDoor(GameData gameData) {
        float x = gameData.getDisplayWidth() / 2 + 300;
        float y = gameData.getDisplayHeight() / 2 + 150;

        float[] shapeX = new float[6];
        float[] shapeY = new float[6];

        Entity item = new Door(new Room(new ArrayList<>()));
        item.add(new PositionPart(x, y, 0));
        item.setRadius(30);
        item.add(new CollisionPart());
        item.setShapeX(shapeX);
        item.setShapeY(shapeY);
        item.setEntityType(EntityType.DOOR);

        return item;
    }
}
