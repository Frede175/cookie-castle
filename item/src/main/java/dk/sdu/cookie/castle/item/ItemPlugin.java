package dk.sdu.cookie.castle.item;

import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IGamePluginService;

public class ItemPlugin implements IGamePluginService {

    private Item item;

    @Override
    public void start(GameData gameData, World world) {
        item = new Item();
        world.addEntity(item);
        System.out.println("Jeff");
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(item);
        System.out.println("No Jeff");
    }
}
