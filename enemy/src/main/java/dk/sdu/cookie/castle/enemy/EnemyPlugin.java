package dk.sdu.cookie.castle.enemy;

import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {

    private Enemy enemy;

    @Override
    public void start(GameData gameData, World world) {
        enemy = new Enemy();
        world.addEntity(enemy);
        System.out.println("Enemy started");
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
        System.out.println("Enemy stopped");
    }
}
