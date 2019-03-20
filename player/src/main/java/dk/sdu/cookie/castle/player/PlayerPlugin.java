package dk.sdu.cookie.castle.player;

import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {
    private Player player;

    @Override
    public void start(GameData gameData, World world) {
        player = new Player();
        world.addEntity(player);
        System.out.println("Started player");
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
        System.out.println("Removed player");
    }
}
