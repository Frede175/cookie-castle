package dk.sdu.cookie.castle.world;

import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IGamePluginService;

public class WorldPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        System.out.println("I exist");
    }

    @Override
    public void stop(GameData gameData, World world) {

    }
}
