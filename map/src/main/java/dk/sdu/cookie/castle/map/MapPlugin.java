package dk.sdu.cookie.castle.map;

import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IGamePluginService;
import dk.sdu.cookie.castle.map.entities.door.DoorPosition;

public class MapPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        // Set door positions with display dimensions from GameData
        for (DoorPosition doorPosition : DoorPosition.values()) {
            doorPosition.setPosition(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        }

        // skal initiate singleton og kalde "generateNewMap" func
        Map.getInstance().generateMap(3, world);
        Map.getInstance().setCurrentRoom(Map.getInstance().getListOfRooms().get(0));
        for (String s : Map.getInstance().getCurrentRoom().getEntityList()) {
            world.getEntity(s).setIsActive(true);
        }
    }

    @Override
    public void stop(GameData gameData, World world) {

    }
}
