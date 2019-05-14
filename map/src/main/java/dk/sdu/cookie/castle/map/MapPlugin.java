package dk.sdu.cookie.castle.map;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IGamePluginService;
import dk.sdu.cookie.castle.map.entities.door.DoorPosition;

public class MapPlugin implements IGamePluginService {
    private Map map = Map.getInstance();

    @Override
    public void start(GameData gameData, World world) {
        // Set door positions with display dimensions from GameData
        for (DoorPosition doorPosition : DoorPosition.values()) {
            doorPosition.setPosition(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        }

        // skal initiate singleton og kalde "generateNewMap" func
        map.generateMap(3, world);
        map.setCurrentRoom(map.getListOfRooms().get(0));
        for (Entity e : map.getCurrentRoom().getDefaultState().keySet()) {
            world.getEntity(e.getID()).setIsActive(true);
        }
    }

    @Override
    public void stop(GameData gameData, World world) {

    }
}
