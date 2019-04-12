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

public class MapPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        world.addEntity(createDoor(gameData));

        for (DoorPosition doorPosition : DoorPosition.values()) {
            doorPosition.setPosition(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        }

        // skal initiate singleton og kalde "generateNewMap" func


    }

    @Override
    public void stop(GameData gameData, World world) {

    }

    private Entity createDoor(GameData gameData) {
        float x = gameData.getDisplayWidth() / 2 + 200;
        float y = gameData.getDisplayHeight() / 2 + 150;

        float[] shapeX = new float[6];
        float[] shapeY = new float[6];


        Entity item = new Door(new Room(new ArrayList<Entity>()));
        item.add(new PositionPart(x, y, 0));
        item.setRadius(30);
        item.add(new CollisionPart());
        item.setShapeX(shapeX);
        item.setShapeY(shapeY);
        item.setEntityType(EntityType.DOOR);

        return item;
    }
}
