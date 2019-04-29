package dk.sdu.cookie.castle.bullet;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IGamePluginService;

public class BulletPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {
    }

    /**
     * Removes the Entity from the world, if it is of the Bullet Class
     *
     * @param gameData The GameData
     * @param world The World
     */
    @Override
    public void stop(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {
            if (e.getClass() == Bullet.class) {
                world.removeEntity(e);
            }
        }
    }
}
